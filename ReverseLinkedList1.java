package com.code.testcode.LinkedList;

class ListNode3 {
      int val;
      ListNode3 next;
      ListNode3() {}
      ListNode3(int val) { this.val = val; }
      ListNode3(int val, ListNode3 next) { this.val = val; this.next = next; }
  }

  // 1->2->3->null
//
public class ReverseLinkedList1 {
    public ListNode3 reverseList(ListNode3 head) {

    ListNode3 prevNode = null;
    ListNode3 curNode = head;

    while (curNode != null) {
        // Store Next value in temp
        ListNode3 nextNode = curNode.next;
        // Point the next to the previous null
        curNode.next = prevNode;
        // Move previous to current and current to next
        prevNode = curNode;
        curNode = nextNode;
    }

        return prevNode;
    }
}
