package com.code.testcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RemoveDuplicatesFromSortedArray {

    public static void main(String[] args) {
        int[] nums = {1,1,2};
        testRun(nums);
        SpringApplication.run (RemoveDuplicatesFromSortedArray.class, args);
    }

//    public static int[] testRun(int[] nums) {
//        //below will have non duplicate numbers
//        Set<Integer> removedNum = new HashSet<> ();
//        for (int num : nums) {
//            removedNum.add (num);
//        }
//        // Get the size of the removedNum
//        int countOfNums = removedNum.size ();
//        System.out.println("value is :" +countOfNums);
//        System.out.println("removed duplicate value is :" +removedNum);
  //  return countOfNums;
//        return removedNum.stream ().mapToInt(Integer::intValue).toArray ();
//    }

    // int[] nums = {1,1,2};
    // int[] nums = {1,1,2,3,4};
    public static int testRun(int[] nums) {
       if (nums.length == 0) return 0;
       int i =0;
       for (int j = 1 ; j < nums.length; j++)
       {
           if (nums[j] != nums[i])
           {
               i++;
               nums[i] = nums[j];
           }
       }
      // System.out.println ( i+1);
        return i+1;
    }

}
