package com.code.testcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class IntersectionOfTwoArrays {

    public static int[] intersect(int[] nums1, int[] nums2) {

        //1. Handle edge case
        if (nums1 == null || nums2 == null || nums1.length ==0 || nums2.length == 0)
        {
            return new int[0];
        }

        // Check which ever array has lesser elements add hashmap to that
        if (nums1.length > nums2.length) {
            //Calling function recurrsively
            intersect (nums2, nums1);
        }
        HashMap<Integer, Integer> hm = new HashMap<> ();

        for ( int num : nums1) {
            hm.put(num, hm.getOrDefault (num,0) +1);
        }

        List<Integer> result = new ArrayList<> ();
        for (int num : nums2) {
            // Element is already found in other array, reduce the frequency
            if (hm.containsKey (num) && hm.get (num) >0) {
                result.add (num);
                // Reducing the frequency value
               hm.put (num, hm.get (num) -1);

            }
        }

        //convert result to an array
        int[] intersection = new int[result.size ()];

        for (int i=0 ; i <result.size (); i++) {
            intersection[i] = result.get (i);
        }

        return intersection;
    }

    public static void main (String[] args) {
        int [] nums = {1,2,2,1};
        int[] nums2 = {2,2};

        System.out.println (Arrays.toString (intersect(nums, nums2)));

        int [] nums3 = {4,9,5};
        int[] nums4 = {9,4,9,8,4};

        System.out.println (Arrays.toString (intersect(nums3, nums4)));

    }
}
