package com.code.testcode.Arrays;

public class MergeSortedArrays {

    public static void merge(int[] nums1, int m, int[] nums2, int n) {

        // create a three index pointers
        int i = m -1; // size of nums1 m is the length m-1 is the last index element since it starts with 0
        int j = n - 1; // size of nums2 n is the length n-1 is the last index element since it starts with 0
        int k = m + n -1; // size of nums1 and nums2 n is the length m + n -1 is the last index element since it starts with 0 when both are combined

        // Having 2 pointers  moving from right
        while ( i>= 0 && j >=0) {
            // starting from the end of the element of both arrays to see which is greater and it to kth position in nums1
            if (nums1[i] > nums2[j]) {
                nums1[k] = nums1[i];
                // since largest is nums1, move to the next right most element in nums1, no changes to nums2
                i--;
            }
            else {
                nums1[k] = nums2[j];
                // since largest is nums2, move to the next right most element in nums2, no changes to nums2
                 j--;
            }
            // After filling kth position move to the next right most postion
            k--;
        }

        // If it comes here either i or j is iterated, if check
       while (j >= 0 ) {
            nums1[k] = nums2[j];
            j--;
            k--;
        }

       // nums1 even if thre is any left over it will get added

    }

    public static void main (String[] args) {

    }
}
