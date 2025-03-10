package com.code.testcode;

public class Palindrome {
    public static boolean isPalindrome(String s) {

        //Remove additional characters like space and convert to lowercase
        StringBuilder st = new StringBuilder ();
        for (char c : s.toCharArray())
        {
            if (Character.isLetterOrDigit (c)) {
                st.append (Character.toLowerCase (c));
            }
        }

        int left = 0;
        int right = st.length ()-1;
        // Have a two pointer to check if the letter or number is same
       while (left < right ) {
           if (st.charAt (left) != st.charAt (right)) {
               return false;
           }
           left++;
           right--;
       }
return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println(isPalindrome("race a car"));
        System.out.println(isPalindrome(" "));
    }
}
