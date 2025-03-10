package com.code.testcode;

public class ReverseInteger {

    public int reverse(int x) {

        int reversed = 0;

         while (x != 0) {
             int digit = x % 10;
             x /= 10;

             //Check for overflow before reversing
             if (reversed > Integer.MAX_VALUE /10 || (reversed == Integer.MAX_VALUE && digit > 7 ))
             {
                 return 0;
             }

             //Check for underflow before reversing
             if (reversed < Integer.MIN_VALUE/10 || (reversed == Integer.MIN_VALUE && digit < -8))
             {
                 return 0;
             }

             reversed =  reversed * 10 + digit;

         }
        return reversed;

    }

    public static void main(String[] args)
    {
        ReverseInteger ri = new ReverseInteger ();

        System.out.print (ri.reverse (123));
        System.out.print (ri.reverse (-123));
        System.out.print (ri.reverse (120));
        System.out.print (ri.reverse (0));
        System.out.print (ri.reverse (1534236469));
    }

}
