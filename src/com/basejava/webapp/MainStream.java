package com.basejava.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

public class MainStream {

    public static void main(String[] args) {
        int[] mas = {1, 9, 9, 0, 0, 5};
        System.out.println(minValue(mas));

        List<Integer> list = Arrays.asList(10, 14, 423, 84, 2, 6, 88, 5, 1);
        System.out.println(oddOrEven(list));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0,(x, y) -> x * 10 + y);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream().collect(partitioningBy(x -> x % 2 == 0, toList()));
        return map.get(map.get(false).size() % 2 != 0);
    }
}
