package com.code.testcode;

import java.util.Arrays;

public class PlusOne {

    public static int[] plusOne(int[] digits) {

        // 1. Iterate through last element and decrement

        int arrayLength = digits.length;

        for (int i = arrayLength -1; i >= 0; i--) {
            // if the current digit is less than 9 just increment the value and return
            if (digits[i] < 9) {
                // just increment add one to the last digit
                digits[i]++;
                // It will go back to the calling function and further loop doesn't get processed
                return digits;
            }

            // if not then digit is 9, we need to carry forward the one after setting this to 0,
            digits[i] = 0;
        }
        //create new array, increment length by 1 since there is a carry forward
        int[] result = new int[arrayLength +1];
        // Adding one to the first element since there was a carry forward
        result[0] = 1;
        return result;
    }

    public static void main (String[] args) {

    int[] nums = {1,2,3};
    System.out.println (Arrays.toString (plusOne (nums)));

        int[] nums1 = {9,9};
        System.out.println (Arrays.toString (plusOne (nums1)));

        int[] nums2 = {1,2,9};
        System.out.println (Arrays.toString (plusOne (nums2)));


    }
}
