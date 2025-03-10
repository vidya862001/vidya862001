package com.code.testcode.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AnagramII {
    public static List<List<String>> groupAnagrams(String[] strs) {

//        Input: strs = ["eat","tea","tan","ate","nat","bat"]
//
//        Output: [["bat"],["nat","tan"],["ate","eat","tea"]]

        //1. Create a hashmap
        HashMap<String, List<String>> hm = new HashMap<> ();

        //2. fOR EACH

        for (String str : strs) {
           char[] sortedArray = str.toCharArray ();
           Arrays.sort (sortedArray);

           // Convert it to string to add to hashamp
            String keyElement = new String(sortedArray);

           // Adding to hashmap
            if (!hm.containsKey (keyElement)) {
                hm.put (keyElement, new ArrayList<> ());
            }
            hm.get (keyElement).add (str);

        }
        return new ArrayList<> (hm.values ());
    }

    public static void main (String[] args) {
        String[] strs1 = {"eat","tea","tan","ate","nat","bat"};
        System.out.println(groupAnagrams(strs1));
    }
}
