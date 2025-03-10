package com.code.testcode.medium;

public class containerWithMostWater {

    public static int maxArea(int[] height) {

        // To calculate area of  a rectangle, it is length * breadth (width) - > value of that postion smallest so that water doesn't overflow
        // and difference between right and left, calculate that with water is the max

        // create a two pointers

        // return element

        int maxArea1 = 0;
        int left = 0;
        int right = height.length -1;
//        for (int left = 0; left < right; left++ ) {
        while (left < right ) {
            // calculate area between left and right pointer
            // Lowest betwwen two can be considered as the length because if we chose max it will overflow
            int area = Math.min (height[left], height[right]) * (right - left);
            // After finding the current area, find if that is the max area than previously stored
            maxArea1 = Math.max (area, maxArea1);

            // Have to move pointer, try to find which pointer is less that can be moved so that container can store more
            if (height[left] < height[right]) {
                // move left because it is smaller find bigger area to store max
                left++;
            } else {
                right--;
            }
//        }
        }

        return maxArea1;
    }

    public static void main (String[] args) {

        int[] height = {1,8,6,2,5,4,8,3,7};
        System.out.println (maxArea(height));

        int[] height1 = {1,1};
        System.out.println (maxArea(height1));

    }
}
