package me.abdiskiosk.guis.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Row {

    public static final List<Integer> FIRST = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
    public static final List<Integer> SECOND = Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16, 17);
    public static final List<Integer> THIRD = Arrays.asList(18, 19, 20, 21, 22, 23, 24, 25, 26);
    public static final List<Integer> FOURTH = Arrays.asList(27, 28, 29, 30, 31, 32, 33, 34, 35);
    public static final List<Integer> FIFTH = Arrays.asList(36, 37, 38, 39, 40, 41, 42, 43, 44);
    public static final List<Integer> SIXTH = Arrays.asList(45, 46, 47, 48, 49, 50, 51, 52, 53);


    public static List<Integer> combined(List<Integer>... rows) {
        List<Integer> combined = new ArrayList<>();
        for (List<Integer> row : rows) {
            combined.addAll(row);
        }
        return combined;
    }
}