package com.code.testcode.strings;

import java.util.HashMap;

//Given two strings s and t, return true if t is an anagram of s, and false otherwise.
//    Example 1:
//
//        Input: s = "anagram", t = "nagaram"
//
//        Output: true
//
//        Example 2:
//
//        Input: s = "rat", t = "car"
//
//        Output: false
//       Constraints:
//
//        1 <= s.length, t.length <= 5 * 104
//        s and t consist of lowercase English letters.
//
//        Follow up: What if the inputs contain Unicode characters? How would you adapt your solution to such a case?
public class AnangramEasRpeat {

    public boolean isAnagram(String s, String t) {

        // Length check of not same not anagram
        if (s.length () != t.length ()) {
            return false;
        }

        HashMap <Character,Integer> hm = new HashMap<> ();

        for (char c : s.toCharArray ()) {
            hm.put (c, hm.getOrDefault (c,0)+1);
        }

        for (char c : t.toCharArray ()) {
           if (!hm.containsKey (c) || hm.get (c) == 0) {
               return false;
           }

           // decrement the occurence
            hm.put (c, hm.get (c)-1);
        }

        return true;
    }
}
