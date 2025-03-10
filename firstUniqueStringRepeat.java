package com.code.testcode.strings;

import java.util.HashMap;

public class firstUniqueStringRepeat {
//    Input: s = "leetcode"
//
//    Output: 0
//
//    Explanation:
//
//    The character 'l' at index 0 is the first character that does not occur at any other index.
    public int firstUniqChar(String s) {

        HashMap<Character, Integer> hm = new HashMap<> ();

        for (char c : s.toCharArray ()) {
                hm.put(c,hm.getOrDefault (c,0)+1);
        }

        for (int i =0; i < s.length (); i++) {
            if (hm.get (s.charAt (0)) == 1) {
                return i;
            }
        }
        return -1;
    }
}
