package com.code.testcode;

import java.util.HashSet;

public class ContainsDuplicate {
    public static boolean containsDuplicate(int[] nums) {

        HashSet<Integer> found = new HashSet<> ();

        for (int num : nums) {
            if (found.contains (num)) {
                return true;
            }
            found.add (num);
        }

        return false;
    }

    public static void main (String[] args) {
        int[] nums  = {1,2,3,1};
        System.out.println (containsDuplicate(nums));

        int[] nums2 = {1, 2, 3, 4};
        System.out.println(containsDuplicate(nums2));

        int[] nums3 = {1, 1, 1, 3, 3, 4, 3, 2, 4, 2};
        System.out.println(containsDuplicate(nums3));
    }
}
