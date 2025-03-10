package com.code.testcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TwoSums {

    public static int[] twoSum(int[] nums, int target) {

        // Hashmap

        HashMap<Integer, List<Integer>>  hm = new HashMap<> ();

        // Check for target iterate and return indices
        for (int i = 0; i < nums.length; i++) {

            int complementNumber = target - nums[i];

            if (hm.containsKey (complementNumber)) {
                for (int index : hm.get (complementNumber)) {
                    return new int[]{i, index};
                }
            }
                // if Map doesn't contain add it to map
                hm.putIfAbsent (nums[i], new ArrayList<> ());
                hm.get (nums[i]).add (i);


        }

        return null;
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        System.out.println (Arrays.toString (twoSum (nums, target)));

        int[] nums1 = {3,2,4};
        int target1 = 6;

        System.out.println (Arrays.toString (twoSum (nums1, target1)));

        int[] nums2 = {3,3};
        int target2 = 6;

        System.out.println (Arrays.toString (twoSum (nums2, target2)));

        int[] nums3 = {3, 5, 3, 4};
        int target3 = 9;

        System.out.println (Arrays.toString (twoSum (nums3, target3)));

    }
}
