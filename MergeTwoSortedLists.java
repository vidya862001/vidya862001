package com.code.testcode.LinkedList;

   class ListNode5 {
      int val;
      ListNode5 next;
      ListNode5() {}
      ListNode5(int val) { this.val = val; }
      ListNode5(int val, ListNode5 next) { this.val = val; this.next = next; }
  }

public class MergeTwoSortedLists {

    public static ListNode5 mergeTwoLists(ListNode5 list1, ListNode5 list2) {

        //1. Create dummy
        ListNode5 dummy = new ListNode5 (0);
        ListNode5 current = dummy;

        // loop to until both are not null
        while ( list1 != null && list2 != null ) {
            // check which one is smaller to add to the current
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            }
            else {
                current.next = list2;
                list2 = list2.next;
            }
            // Moving pointer to the added element and setting it as current
            current = current.next;
        }

        // Once loop is breaked add whichever has not null elements to the current
        if (list1 != null) {
            //The remaining elements are added
            current.next = list1;
        }
        else {
            //The remaining elements are added
            current.next = list2;
        }

        return dummy.next;
    }

    public static void printList(ListNode5 head) {
        ListNode5 current = head;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {

        // Example 1: list1 = [1,2,4], list2 = [1,3,4]
        ListNode5 list1 = new ListNode5 (1);
        list1.next = new ListNode5 (2);
        list1.next.next = new ListNode5 (4);

        ListNode5 list2 = new ListNode5 (1);
        list2.next = new ListNode5 (3);
        list2.next.next = new ListNode5 (4);

        System.out.print("Merged list: ");
        ListNode5 mergedHead = mergeTwoLists(list1, list2);
        printList(mergedHead);

        // Example 2: list1 = [], list2 = []
        list1 = null;
        list2 = null;

        System.out.print("Merged list: ");
        mergedHead = mergeTwoLists(list1, list2);
        printList(mergedHead);

        // Example 3: list1 = [], list2 = [0]
        list1 = null;
        list2 = new ListNode5 (0);

        System.out.print("Merged list: ");
        mergedHead = mergeTwoLists(list1, list2);
        printList(mergedHead);
    }
}
