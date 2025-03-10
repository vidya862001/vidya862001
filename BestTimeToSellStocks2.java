package com.code.testcode;

public class BestTimeToSellStocks2 {

    public static int maxProfit(int[] prices) {


        // 1. Intialize a variable for totalProfit

        int totalProfit = 0;

        // Edge case check
        if (prices == null || prices.length == 0) {
            return 0;
        }
        //2 . Loop through and find if any day is greater than previous day sum it to the total profit

        for (int i= 1; i < prices.length; i++) {
            if ( prices[i] > prices[i-1]) {
                totalProfit += prices[i] - prices[i-1];
            }
        }
        return totalProfit;

    }

    public static void main(String[] args) {
        int[] nums = {7,1,5,3,6,4};
        System.out.println(maxProfit(nums));

        int[] nums1 = {1,2,3,4,5};
        System.out.println(maxProfit(nums1));

        int[] nums2 = {7,6,4,3,1};
        System.out.println(maxProfit(nums2));
    }
}
