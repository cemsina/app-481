package com.mycompany.app;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import spark.ModelAndView;
import spark.Request;
import spark.template.mustache.MustacheTemplateEngine;

/**
 * Hello world!
 *
 */
public class App {
	public static boolean search(ArrayList<Integer> array, int e) {
		System.out.println("inside search");

		if (array == null)
			return false;
		for (int elt : array) {
			if (elt == e)
				return true;
		}
		return false;
	}
	public static Map<String, String> lotteryRequestParser(Request req) {
		String users = req.queryParams("users");
		String[] rows = users.split("\r\n");
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Integer> usersIndexes = new ArrayList<Integer>();
		ArrayList<Integer> weights = new ArrayList<Integer>();
		int i=0;
		for(String s : rows) {
			s = s.trim();
			if(s.length() == 0)
				continue;
			String[] spaces = s.split(" ");
			String last = spaces[spaces.length-1];
			int weight = 1;
			try {
				weight = Integer.parseInt(last);
				s = s.replace(last, "").trim();
			}catch(NumberFormatException e) {
				
			}
			usersIndexes.add(i);
			weights.add(weight);
			names.add(s);
			i++;
		}
		int totalMoney = 10000;
		int winnerCount = (int)Math.ceil((double)usersIndexes.size()/10);
		try {
			totalMoney = Integer.parseInt(req.queryParams("totalMoney"));
			winnerCount = Integer.parseInt(req.queryParams("winnerCount"));
		}catch(NumberFormatException e) {
			
		}
		Map<Integer,Integer> map = lottery(usersIndexes,weights,winnerCount,totalMoney);
		String winners = "";
		Set<Integer> keys = map.keySet();
		for(Integer k : keys) {
			winners += names.get(k)+" : "+map.get(k) + "\n";
		}
		Map<String,String> m = new HashMap<String,String>();
		m.put("winners", winners);
		return m;
	}
	public static Map<Integer, Integer> lottery(ArrayList<Integer> users, ArrayList<Integer> ticketWeights, int winnerCount, int totalMoney) {
		if (users == null || ticketWeights == null || winnerCount > users.size()
				|| ticketWeights.size() != users.size())
			return null;
		int usersLength = users.size();
		int[] winners = new int[winnerCount];
		int[] indexes = new int[usersLength];
		for (int i = 0; i < indexes.length; i++)
			indexes[i] = i;
		for (int i = 0; i < indexes.length; i++) {
			int a = (int) (indexes.length * Math.random());
			int b = (int) (indexes.length * Math.random());
			int temp = indexes[a];
			indexes[a] = indexes[b];
			indexes[b] = temp;
		}
		int totalWeight = 0;
		for (int i = 0; i < winnerCount; i++) {
			winners[i] = indexes[i];
			totalWeight += ticketWeights.get(indexes[i]);
		}
		double unit = (double) totalMoney / totalWeight;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < winnerCount; i++) {
			map.put(winners[i], (int) (ticketWeights.get(winners[i]) * unit));
		}
		return map;
	}

	public static void main(String[] args) {
		port(getHerokuAssignedPort());
		get("/", (req, res) -> "Hello, World");
		post("/compute", (req, res) -> {
			return new ModelAndView(App.lotteryRequestParser(req), "compute.mustache");
		}, new MustacheTemplateEngine());
		get("/compute", (rq, rs) -> {
			Map map = new HashMap();
			map.put("result", "not computed yet!");
			return new ModelAndView(map, "compute.mustache");
		}, new MustacheTemplateEngine());
	}

	static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; // return default port if heroku-port isn't set (i.e. on localhost)
	}
}