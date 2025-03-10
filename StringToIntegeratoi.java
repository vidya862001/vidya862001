package com.code.testcode;

public class StringToIntegeratoi {

    public static int myAtoi(String s) {

        //1. Trim white spaces
        s = s.trim();

        //2. After trimming if the string is empty return 0
        if(s.isEmpty ()) {
            return 0;
        }

        //3. Calculate first letter is positive or negative sign
        int sign = 1; //Assuming positive sign as default
        int i = 0; //Index starting
        if (s.charAt(i) == '-') { // check if the first character is -
            sign = -1; // if its - change sign to -1
            i++; // in order to ignore that - increment index
        }
        else if (s.charAt(i) == '+') { // if first character is positive just leave the default sign and increment index to move to next
            i++;
        }

        // 4. Used for converting to digits
        int result = 0;

        //5. Calculation part loop until last element, and if its a digit
        while(i < s.length () && Character.isDigit (s.charAt (i)))
        {
           int intDigit = s.charAt(i) - '0'; // Example '4' - '0' gives 4 (the integer value). Ascii of 0 is 48 sequential to get the digit

            //Check for overflow underflow
            if (result > Integer.MAX_VALUE/10 || (result == Integer.MAX_VALUE/10 && intDigit > Integer.MAX_VALUE % 10 )) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            result = result * 10 + intDigit; // Remember
            i++; // increment to next character
        }
        // Apply the sign and return the result anything multiplied by 1 will be same by sign + or - needs to be determined

        return sign * result;
    }

    public static void main(String[] args) {
        System.out.println(myAtoi("42"));
        System.out.println(myAtoi(" -042"));
        System.out.println(myAtoi("1337c0d3"));
        System.out.println(myAtoi("0-1"));
        System.out.println(myAtoi("words and 987"));
        System.out.println(myAtoi("2147483648"));
    }

}
