package com.code.testcode;

public class ReverseString2 {

    public static void main(String[] args) {
        char[] givenInput = {'h','e','l','l','o'};
        char[] reversedString = getReverseString(givenInput);
        System.out.println (reversedString);
    }

        public static char[] getReverseString (char[] input) {

            int left = 0;
            int right = input.length -1;
            while (left < right ){
                //Keeping left element in temp variable to swap to right element
                char temp = input[left];
                input[left] = input[right];
                // Set right eleement with the temp left element already stored
                input[right] = temp;
                left++;
                right--;
            }

            return input;
        }
}
