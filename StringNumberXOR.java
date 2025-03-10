package com.code.testcode;

public class StringNumberXOR {


    public static int singleNumber(int[] nums) {

        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    public static void main (String[] args) {
        int[] nums = {2,2,1};
        System.out.println (singleNumber(nums));
        int[] nums1 = {4,1,2,1,2};
        System.out.println (singleNumber(nums1));
        int[] nums2 = {1};
        System.out.println (singleNumber(nums2));
    }
}
