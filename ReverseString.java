package com.code.testcode;

public class ReverseString {

    public static void main(String[] args) {
        char[] str = {'h','e','l','l','o'};
        char[] reversed = reverseString (str);
        System.out.println (reversed);
    }

    public static char[] reverseString(char[] str) {
        int left = 0;
        int right = str.length - 1;
        while (left < right) {
            char temp = str[left];
            str[left] = str[right];
            str[right] = temp;
            left++;
            right--;
        }
        return str;

    }
}

