package com.test;

import org.junit.Test;

import java.util.*;

public class CommonTest {

    @Test
    public void testHashMapSort() {
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('a',325);
        map.put('b',32);
        map.put('c',100);
        ArrayList<Map.Entry<Character, Integer>> list = new ArrayList<>(map.entrySet());
        System.out.println("before sort");
        System.out.println(list);
        Collections.sort(list, (a, b) -> {
            return b.getValue() - a.getValue();
        });
        System.out.println("after sort");
        System.out.println(list);
    }

    @Test
    public void testDateSort() {
        Map<Integer, Date> map = new HashMap<>();
        map.put(1, new Date(10000000));
        map.put(2, new Date(20000000));
        map.put(3, new Date(5000000));
        List<Map.Entry<Integer, Date>> list = new ArrayList<>(map.entrySet());
        System.out.println("before sort");
        System.out.println(list);
        Collections.sort(list, (a, b) -> {
            return a.getValue().compareTo(b.getValue());
        });
        System.out.println("after sort");
        System.out.println(list);
    }

}
