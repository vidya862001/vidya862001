package com.code.testcode;

public class LongestCommonPrefix {

    public static String longestCommonPrefix(String[] strs) {

        //1. If String of array has no elements or if the length is empty return empty string

        if (strs == null || strs.length == 0) {
            return "";
        }

        // 2. If not, consider first element in array is the prefix for all remaining elements and start searching

        String prefix =  strs[0];
        //Starting loop from second element
       // for (int i = 1; i < strs.length ; i++)
            for (int i = 1; i < strs.length; i++)
        {
            //Keep shortening the prefix until search through all the words in first string
            while (!strs[i].startsWith (prefix)) {
                // reducing one element at the last as prefix
                prefix = prefix.substring (0, prefix.length ()-1);

                // If at any point prefix becomes empty return empty string that means no match
                if (prefix.isEmpty ()) {
                    return "";
                }
            }
        }
        //if not something is matching so return that

        return prefix;
    }


    public static void main (String[] args) {
        String [] strs = {"flower","flow","flight"};
        System.out.println (longestCommonPrefix (strs));

        String [] strs1 = {"dog", "racecar", "car"};
        System.out.println (longestCommonPrefix (strs1));

    }
}
