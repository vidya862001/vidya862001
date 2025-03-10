package com.code.testcode;

import java.util.Arrays;

public class MoveZerosToEnd {

    public static void moveZeroes(int[] nums) {

        // Have to move all zeros to the trailing end keeping others intact
        // 2 pointer , 1 to track index of the non zero placement so that next non zero can be placed remaining place it to zero
        // {0,3,0,12,1}
        int lastNonZeroIndex = 0;

        for (int i =0; i < nums.length; i++) {
            // If element is not zero move it to the front
            if (nums[i] != 0) {
                nums[lastNonZeroIndex] = nums[i];
             // This happens at the end when all the non zero is placed in fron i still loops
                // but it doesn't match lastNonZerio so replace with 0
                if (i != lastNonZeroIndex) {
                    nums[i] = 0;
                }
                // Incrementing last non zero pointer only if we encouter a non zero
                lastNonZeroIndex ++;
            }
        }
    }

    public static void main (String[] args) {

        int[] nums = {0,1,0,3,12};
        moveZeroes (nums);
        System.out.println (Arrays.toString (nums));

    }
}
