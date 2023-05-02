package com.mark.code.epamlab.calculations;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FibonachiCalculation {
    public int calculate(int number) {
        int first = 1;
        int second = 1;
        int third = 0;

        for (int i = 2; i <= number; i++) {
            third = first + second;
            first = second;
            second = third;
        }

        return third;
    }

    public int findSum(List<Integer> fibonachList) {
        int sum = 0;
        if (!fibonachList.isEmpty()) {
            sum = fibonachList.stream().mapToInt(Integer::intValue).sum();
        }
        return sum;
    }

    public int findMin(List<Integer> fibonachiList) {

        int min = 0;
        if (!fibonachiList.isEmpty()) {
            min = fibonachiList.stream().min(Integer::compareTo).get();
        }
        return min;
    }

    public int findMax(List<Integer> fibonachiList) {
        int max = 0;
        if (!fibonachiList.isEmpty()) {
            max = fibonachiList.stream().max(Integer::compareTo).get();
        }
        return max;
    }

}
