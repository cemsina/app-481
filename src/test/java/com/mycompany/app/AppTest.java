package com.mycompany.app;
import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
public class AppTest extends TestCase{
  public void testLotteryWinnersCount(){
    ArrayList<Integer> users = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
    ArrayList<Integer> weights = new ArrayList<Integer>(Arrays.asList(1,1,2,4,1));
    Map<Integer,Integer> map = App.lottery(users, weights, 2, 4000);
    assertTrue(map.keySet().size() == 2);
  }	
  public void testLotteryWinnersMoneyAmount(){
    ArrayList<Integer> users = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
    ArrayList<Integer> weights = new ArrayList<Integer>(Arrays.asList(1,1,2,4,1));
    Map<Integer,Integer> map = App.lottery(users, weights, 2, 4000);
    int sum = 0;
    int winnersWeight = 0;
    int sumWithWeights = 0;
    for(int winner : map.keySet()){
      winnersWeight += (weights.get(winner));
    }
    for(int winner : map.keySet()){
      sumWithWeights += (int)(((double)weights.get(winner)/winnersWeight)*4000);
    }
    for(int money : map.values()){
      sum += money;
    }
    System.out.println(sumWithWeights);
    System.out.println(sum);
    assertTrue(sumWithWeights == sum);
  }	
  public void testLotteryWinnersCountBiggerThanUsersSize(){
    ArrayList<Integer> users = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
    ArrayList<Integer> weights = new ArrayList<Integer>(Arrays.asList(1,1,2,4,1));
    Map<Integer,Integer> map = App.lottery(users, weights, 6, 4000);
    assertTrue(map == null);
  }	
  public void testLotteryNullUsers(){
    ArrayList<Integer> users = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
    ArrayList<Integer> weights = new ArrayList<Integer>(Arrays.asList(1,1,2,4,1));
    Map<Integer,Integer> map = App.lottery(null, weights, 6, 4000);
    assertTrue(map == null);
  }	
  public void testLotteryNullWeights(){
    ArrayList<Integer> users = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
    ArrayList<Integer> weights = new ArrayList<Integer>(Arrays.asList(1,1,2,4,1));
    Map<Integer,Integer> map = App.lottery(users, null, 6, 4000);
    assertTrue(map == null);
  }	
  public void testLotteryWinnersCountNegative(){
    ArrayList<Integer> users = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
    ArrayList<Integer> weights = new ArrayList<Integer>(Arrays.asList(1,1,2,4,1));
    Map<Integer,Integer> map = App.lottery(users, weights, -1, 4000);
    assertTrue(map == null);
  }
}
