package com.code.testcode;

import java.util.Arrays;

public class RotateArray {

    public static void rotate(int[] nums, int k) {

        //Edge case scenario if no elements in nums or lenght is 0 or if k is 0

        if (nums == null || nums.length == 0 || k == 0 ) {
            return;
        }

        //  Reduce k to be within the array length (in case k is larger than the length of the array)

        k = k % nums.length;

        // do entire reversal of the nums
        reversal (nums, 0, nums.length-1);

        // all the elements are reversed order, so revese the k element
        reversal(nums, 0, k -1 );
        // reverse the next elements which are not part of k to appear in correct order
        reversal (nums, k, nums.length - 1);
    }

    public static void reversal(int[] nums, int startIndex, int endIndex) {
        while (startIndex < endIndex) {
         // create a temp variable to store left most value so that it can be flipped at the end
            int temp = nums[startIndex];
            nums[startIndex] = nums[endIndex];
            nums[endIndex] = temp;
            startIndex++;
            endIndex--;
        }
    }

    public static void main (String[] args) {
        int[] nums = {1,2,3,4,5,6,7};
        int k = 3;
        rotate (nums,k);
        System.out.println (Arrays.toString (nums));


        int[] nums1 = {-1, -100, 3, 99};
        int k1 = 2;
        rotate (nums1,k1);
        System.out.println (Arrays.toString (nums1));

    }
}
