package com.code.testcode.strings;

public class reverseIntegerRepeat {
    //Input: x = 123
    //Output: 321
    public int reverse(int x) {

        // return element
        int reversed = 0;
        while ( x!= 0) {

            int digit = x % 10; // to get last digit
             x /= 10; // to get last before digit

            // Calculate for overflow
            if (x >= Integer.MAX_VALUE || (x == Integer.MAX_VALUE && digit > 7 )){
                return 0;
            }
            if (x <= Integer.MIN_VALUE || (x == Integer.MIN_VALUE && digit < -8 )){
                return 0;
            }

            reversed = reversed * 10 + digit;
        }
        return reversed;
    }
}
