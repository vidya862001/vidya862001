package com.code.testcode;

import java.util.HashMap;

public class FirstUniqueCharString {


    public static void main(String[] args) {
        String s = "leetcode";
        System.out.println(getUniqueCharacter(s));
    }

    public static int getUniqueCharacter(String s) {

        // Create a hashmap with character and occurence
        HashMap<Character, Integer>frequencyMap = new HashMap<> ();

        for (char c : s.toCharArray ()) {
            // Add key as character, value as get key if its present increment value by 1 if no default to 0
           frequencyMap.put (c,frequencyMap.getOrDefault (c,0) +1);
        }

        for (int i=0; i < s.length (); i++) {
            if (frequencyMap.get (s.charAt (i)) == 1)
            {
                return i;
            }
        }
        return -1;
    }
}
