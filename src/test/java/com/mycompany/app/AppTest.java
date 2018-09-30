package com.mycompany.app;
import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.Arrays;
public class AppTest extends TestCase{
	public void testFound() {
      ArrayList<Integer> array = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4));
      assertTrue(App.search(array, 4));
    }

    public void testNotFound() {
      ArrayList<Integer> array = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4));
      assertFalse(App.search(array, 5));
    }

    public void testEmptyArray() {
      ArrayList<Integer> array = new ArrayList<Integer>();
      assertFalse(App.search(array, 1));
    }

    public void testNull() {
      assertFalse(App.search(null, 1));
    }

}
