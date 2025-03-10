package com.code.testcode.LinkedList;



   class ListNode1 {
    int val;
     ListNode1 next;
     ListNode1(int x) { val = x; }
 }


public class DeleteNodeInLinkedList {

    public static void deleteNode(ListNode1 node) {

        // Replace 5 with the next val
        node.val = node.next.val;

        // Link the current node to the next of the next node, effectively removing the next node
        node.next = node.next.next;
    }

    // Helper method to print the linked list
    public static void printList(ListNode1 head) {
        ListNode1 current = head;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println();
    }

       public static void main (String[] args) {
        //4->5->1->9
           ListNode1 head = new ListNode1 (4);
           head.next = new ListNode1 (5);
           head.next.next = new ListNode1 (1);
           head.next.next.next = new ListNode1 (9);

           // Calling to print
           printList(head);

           deleteNode (head.next);
           printList(head);
       }

}
