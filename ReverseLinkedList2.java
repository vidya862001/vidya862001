package com.code.testcode.LinkedList;

import java.util.Stack;

class ListNode4 {
    int val;
    ListNode4 next;
    ListNode4() {}
    ListNode4(int val) { this.val = val; }
    ListNode4(int val, ListNode4 next) { this.val = val; this.next = next; }
}
public class ReverseLinkedList2 {

    public static ListNode4 reverseList(ListNode4 head) {

        // Handle edge case

        if (head == null || head.next == null)
        {
            return head;
        }
        // Create a new stack

        Stack<ListNode4> stk = new Stack<> ();

        ListNode4 currentPtr = head;
        // Push it to stack
        while (currentPtr != null ){
            stk.push (currentPtr);
            currentPtr = currentPtr.next;
        }

        // Pop will be bring LIFO
        ListNode4 newHead = stk.pop ();
        //Pointing current to this new Head
        currentPtr = newHead;

        // Loop to pop rest of the elements
        while (!stk.isEmpty ()) {
            // Set the next element as the popped element
            currentPtr.next = stk.pop ();
            // Move the pointer to the currently popped element
            currentPtr = currentPtr.next;
        }

        // set the last pointer to null to end the list
        currentPtr.next = null;
        return newHead;
    }

    public static void printList(ListNode4 head) {
        ListNode4 current = head;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
       

        // Example 1: head = [1, 2, 3, 4, 5]
        ListNode4 head1 = new ListNode4 (1);
        head1.next = new ListNode4 (2);
        head1.next.next = new ListNode4 (3);
        head1.next.next.next = new ListNode4 (4);
        head1.next.next.next.next = new ListNode4 (5);

        System.out.print("Original list: ");
       printList(head1);

        ListNode4 reversedHead1 = reverseList(head1);
        System.out.print("Reversed list: ");
        printList(reversedHead1);

        // Example 2: head = [1, 2]
        ListNode4 head2 = new ListNode4 (1);
        head2.next = new ListNode4 (2);

        System.out.print("Original list: ");
        printList(head2);

        ListNode4 reversedHead2 = reverseList(head2);
        System.out.print("Reversed list: ");
        printList(reversedHead2);

        // Example 3: head = []
        ListNode4 head3 = null;

        System.out.print("Original list: ");
        printList(head3);

        ListNode4 reversedHead3 = reverseList(head3);
        System.out.print("Reversed list: ");
        printList(reversedHead3);
    }
}
