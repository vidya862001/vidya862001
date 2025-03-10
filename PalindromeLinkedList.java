package com.code.testcode.LinkedList;

   class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
public class PalindromeLinkedList {

    public static boolean isPalindrome(ListNode head) {

        // 1. Find the middle so assign two pointers one is slow moves 1 step, other is fast moves 2 step until fast reaches null
        // fast.next is null
        ListNode slow = head;
        ListNode fast = head;

        //example {2,3,7,2,3,7}

        while (fast != null && fast.next != null) {
            // Move slow one step and fast two step
            slow = slow.next;
            fast = fast.next.next;
        }

        // slow is at middle, head of the second half and fast is at null, do reversal for slow

        ListNode secondHalf  = reverse (slow);
        // Another node which points to the head of the begniing
        ListNode firstHead = head;

        // Checking for secondHalf because of Odd number of data scenario, in that case secondHalf reaches null first
        // we don't need to check last element in first half
        while (secondHalf != null) {
            // compare value of first hald and second half if any point not same return false
            if (firstHead.val != secondHalf.val) {
                return false;
            }
            // move both the pointers to next value
            secondHalf = secondHalf.next;
            firstHead = firstHead.next;

        }
 // if it comes till here then it is palindrom
        return true;
    }

    public static ListNode reverse(ListNode slow) {

        // Creating prev and current node

        ListNode prevNode = null; // assume null because at the end after flipping last node needs to point to null
        ListNode curNode = slow;

        while (curNode.next != null) {
            // Create next node
            ListNode next = curNode.next;

            // Assign current to the previous

            curNode.next = prevNode;
            prevNode = curNode;
            curNode = next;
        }
        return prevNode;
    }

    // Helper method to print the linked list (for testing purposes)
    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {

        // Test case 1: list = [1,2,2,1]
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(2);
        head1.next.next.next = new ListNode(1);
        System.out.println("Is palindrome (should be true): " + isPalindrome(head1));

        // Test case 2: list = [1,2]
        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);
        System.out.println("Is palindrome (should be false): " + isPalindrome(head2));
    }
}
