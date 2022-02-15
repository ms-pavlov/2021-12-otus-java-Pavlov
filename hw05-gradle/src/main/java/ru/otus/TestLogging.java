package ru.otus;


import ru.otus.annotations.Log;

import java.util.Arrays;

public class TestLogging {
    public static int calculation(int param) {
        return param;
    }

    public static int calculation(int param1, int param2) {
        int[] params = {param1, param2};
        return calculation(params);
    }


    @Log
    public static int calculation(int param1, int param2,  int param3) {
        int[] params = {param1, param2, param3};
        return calculation(params);
    }
    @Log
    public static int calculation(int[] params) {
        return Arrays.stream(params).sum();
    }

}
