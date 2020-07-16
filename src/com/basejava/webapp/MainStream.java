package com.basejava.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStream {

    public static void main(String[] args) {
        int[] mas = {1, 9, 9, 0, 0};
        System.out.println(minValue(mas));

        List<Integer> list = Arrays.asList(10, 14, 423, 84, 2, 6, 88, 5);
        System.out.println(oddOrEven(list));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().map(x -> {
            IntStream distinct = Arrays.stream(values).distinct();
            int i = 1;
            for (int t : Arrays.stream(values).distinct().sorted().toArray()) {
                if (x == t) { break; }
                i++;
            }
            return (int) (x * Math.pow(10, (double) distinct.count() - i));
        }).reduce(Integer::sum).orElse(0);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().reduce(0, Integer::sum) % 2 == 0 ?
                filter(integers, x -> x % 2 != 0) :
                filter(integers, x -> x % 2 == 0);
    }

    private static List<Integer> filter(List<Integer> integers, Predicate<Integer> predicate) {
        return integers.stream().filter(predicate).collect(Collectors.toList());
    }
}
