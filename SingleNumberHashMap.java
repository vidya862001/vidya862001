package com.code.testcode;

import java.util.HashMap;
import java.util.HashSet;

public class SingleNumberHashMap {

    public static int singleNumber(int[] nums) {

        HashMap<Integer, Integer> hm = new HashMap<> ();

        for (int num : nums) {
            hm.put (num, hm.getOrDefault (num, 0) +1);
        }

        for (int num : hm.keySet ()) {
            if (hm.get (num) == 1) {
                return num;
            }
        }
        return -1;
    }

    public static void main (String[] args) {
        int[] nums = {2,2,1};
        System.out.println (singleNumber (nums));

        int[] nums1 = {4,1,2,1,2};
        System.out.println (singleNumber (nums1));

        int[] nums2 = {1};
        System.out.println (singleNumber (nums2));
    }
}
