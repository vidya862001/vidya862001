package com.code.testcode;

import java.util.HashMap;

public class EasyAnagram {

    public static void main(String[] args) {
        System.out.println(isAnagram("rat", "cat"));
        System.out.println(isAnagram("anagram", "nagaram"));
    }

    public static boolean isAnagram(String s, String t) {

        // If length are not same means not anagram
        if (s.length () != t.length ()) {
            return false;
        }

        // Continue with hashmap

        HashMap<Character,Integer> charCountMap = new HashMap<>();

        for (char c : s.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault (c,0)+1);
        }

        for (char c : t.toCharArray ()) {
            // If character is not in first string or if it is present less times in first string example aab and abb scenario
            if (!charCountMap.containsKey (c) || charCountMap.get (c) == 0 )
            {
                return false;
            }

            // Decrement the character in HashMap, it will override the key with by decrementing the value count
            charCountMap.put (c, charCountMap.get (c)-1);
        }
      return true;
    }
}
