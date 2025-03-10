package com.code.testcode.strings;

public class reverseStringRepeat {
    //Write a function that reverses a string. The input string is given as an array of characters s.
    //
    //You must do this by modifying the input array in-place with O(1) extra memory.
    //
    //
    //
    //Example 1:
    //
    //Input: s = ["h","e","l","l","o"]
    //Output: ["o","l","l","e","h"]
    //Example 2:
    //
    //Input: s = ["H","a","n","n","a","h"]
    //Output: ["h","a","n","n","a","H"]
    //
    //
    //Constraints:
    //
    //1 <= s.length <= 105
    //s[i] is a printable ascii character.


    public void reverseString(char[] s) {
        //Input: s = ["h","e","l","l","o"]
        //Output: ["o","l","l","e","h"]
        int left = 0;
        int right = s.length -1 ;

        while (left < right) {

            // temp variable to store
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left ++;
            right --;
        }
    }
}
