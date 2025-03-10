package com.code.testcode;

public class ImplementStrStr {
    public static int strStr(String haystack, String needle) {
        // 1. Check for null values return 0, if needle is empty
        if (needle.isEmpty()) {
            return 0;
        }

        //2. If Haystack is empty needle is not empty, needle is not found in haystack
        if(haystack.isEmpty()) {
            return -1;
        }

        // 3. Iterate to find if anythingmatch and get that index, iterate only till the length of needle
        for (int i= 0;  (i <= haystack.length () - needle.length ()); i++) {

            if (haystack.substring (i, i+needle.length ()).equals (needle))
            {
                return i;
            }

        }
        //Nothing matches return -1

        return -1;

    }

    public static void main(String[] args) {
        System.out.println(strStr("sadot", "sad"));
        System.out.println(strStr("esadot", "sad"));
        System.out.println(strStr("esedot", "sad"));
    }

}
