package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStreams {

    public static void main(String[] args) {
        int[] values = {9, 9, 0};
        System.out.println(minValue(values));
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            integers.add(i);
        }
        System.out.println(oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (a, b) -> 10 * a + b);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream().collect(Collectors.partitioningBy(x -> x % 2 == 0));
        return map.get(map.get(false).size() % 2 != 0);
    }
}
