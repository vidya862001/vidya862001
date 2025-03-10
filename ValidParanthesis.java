package com.code.testcode.strings;

import java.util.Stack;

public class ValidParanthesis {

    public boolean isValid(String s) {
        // Create a stack
        Stack<Character> stk = new Stack<> ();

        for (char c : s.toCharArray ()) {
            // ppush to stack
            if ( c == '{' || c == '(' || c == '[') {
                stk.push (c);
            }
            // its a closing bracket do a pop to see if it exists
            if (stk.isEmpty ())
            {
                return false;
            }

            char top = stk.pop ();
            if (c == '}' && top != '{') return false;
            if (c == ']' && top != '[') return false;
            if (c == ')' && top != '(') return false;
        }

        return stk.isEmpty ();
    }

}
