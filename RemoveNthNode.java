package com.code.testcode.LinkedList;

 class ListNode2 {
     int val;
     ListNode2 next;
      ListNode2() {}
      ListNode2(int val) { this.val = val; }
      ListNode2(int val, ListNode2 next) { this.val = val; this.next = next; }
  }
public class RemoveNthNode {

    public ListNode2 removeNthFromEnd(ListNode2 head, int n) {

        //1. Create dummy node to avoid index out of bound

        ListNode2 dummy = new ListNode2 (0);
        // Dummy points to the head
        dummy.next = head;

        // Create slow and fastpointer

        ListNode2 firstPtr = dummy;
        ListNode2 secondPtr =  dummy;

        // secondPtr moves n+1 steps than first, firstPtr starts from 1
        for (int i=1; i <= n+1; i++) {
            secondPtr = secondPtr.next;
        }

        while (secondPtr!=null) {
            firstPtr = firstPtr.next;
            secondPtr = secondPtr.next;
        }

        // Next element is what that needs to be removed so referencing it to point to next next element for the current element to delete
        firstPtr.next = firstPtr.next.next;

        return dummy.next;

    }

}
