package com.code.testcode;

import java.util.HashSet;

public class SingleNumberHashSet {

    public static int singleNumber(int[] nums) {

        HashSet<Integer> st = new HashSet<> ();

        for (int num : nums) {
            if ( st.contains (num)) {
                st.remove (num);
            }
            else {
                st.add (num);
            }
        }

        return st.iterator ().next ();
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
