package com.test;

import com.database.command.SonarCommand;
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

    @Test
    public void testLongSort() {
        Map<Integer, Long> map = new HashMap<>();
        map.put(1, 10000000L);
        map.put(2, 20000000L);
        map.put(3, 5000000L);
        List<Map.Entry<Integer, Long>> list = new ArrayList<>(map.entrySet());
        System.out.println("before sort");
        System.out.println(list);
        Collections.sort(list, (a, b) -> {
            long diff = a.getValue() - b.getValue();
            if (diff > 0) return 1;
            else if (diff == 0) return 0;
            else return -1;
        });
        System.out.println("after sort");
        System.out.println(list);
    }

    @Test
    public void testMapEntry() {
        Map<Integer, Long> map = new HashMap<>();
        map.put(1, 10000000L);
        map.put(2, 20000000L);
        map.put(3, 5000000L);
        List<Map.Entry<Integer, Long>> list = new ArrayList<>(map.entrySet());
        list.forEach(integerLongEntry -> {
            System.out.println(integerLongEntry.getKey());
            System.out.println(integerLongEntry.getValue());
        });
    }

    @Test
    public void testString2Integer() {
        String s = "sdfg";
        System.out.println(Integer.valueOf(s));
    }

    @Test
    public void testSonarCommand() {
        SonarCommand command = null;
        String line = "time 2022-12-01 2022-12-30";
        String[] strings = line.split(" ");
        try {
            command = SonarCommand.getSonarCommand(strings[0], strings);
            System.out.println(command);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
