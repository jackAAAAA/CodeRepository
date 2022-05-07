package Interview;

import Tree.BinaryTree;

import java.util.Scanner;

class addTN {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyhead = new ListNode(-1);
        ListNode head = dummyhead;
        int sum = 0, carry = 0;
        while (l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.value;
            int y = l2 == null ? 0 : l2.value;
            sum = carry + x + y;
            carry = sum / 10;
            sum %= 10;
            head.next = new ListNode(sum);
            head = head.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carry == 1) {
            head.next = new ListNode(1);
        }
        return dummyhead.next;
    }

    public static void main(String[] args) {
//        Original Template
        System.out.println("-----请输入两个长度为3的链表-----");
        Scanner scanner = new Scanner(System.in);
        int len1 = 3, len2 = 3;
        int[] arr1 = new int[len1], arr2 = new int[len2];
        for (int i = 0; i < len1; ++i) {
            arr1[i] = scanner.nextInt();
        }
        for (int i = 0; i < len2; ++i) {
            arr2[i] = scanner.nextInt();
        }
//        主动关闭输入扫描器
        scanner.close();
//        在静态main()方法中新建内部类对象，此内部类必须为静态内部类
        ListNode listNode = new ListNode();
        ListNode node1 = listNode.createList(arr1);
        ListNode node2 = listNode.createList(arr2);
        addTN ad = new addTN();
        ListNode node3 = ad.addTwoNumbers(node1, node2);
        node3.printList();

//        2021.06.21
//        ListNode listNode = new ListNode();
//        ListNode node1 = listNode.createList(arr1);
//        ListNode node2 = listNode.createList(arr2);
//        addTN ad = new addTN();
//        ListNode node3 = ad.addTwoNumbers(node1, node2);
//        node3.printList();

//        2021.06.12
//        ListNode node = new ListNode();
//        ListNode l1 = node.createList(arr1);
//        ListNode l2 = node.createList(arr2);
//        addTN ad = new addTN();
//        node = ad.addTwoNumbers(l1, l2);
//        node.printList();

//        2021.06.09
//        System.out.println("-----请输入两个长度为3的整数-----");
//        Scanner scanner = new Scanner(System.in);
//        int len1 = 3, len2 = 3;
//        int[] arr1 = new int[len1], arr2 = new int[len2];
//        for (int i = 0; i < len1; ++i) {
//            arr1[i] = scanner.nextInt();
//        }
//        for (int i = 0; i < len2; ++i) {
//            arr2[i] = scanner.nextInt();
//        }
//        scanner.close();
//        ListNode node = new ListNode();
//        ListNode node1 = node.createList(arr1);
//        ListNode node2 = node.createList(arr2);
//        addTN add = new addTN();
//        node = add.addTwoNumbers(node1, node2);
//        node.printList();
    }
}
//    Original Template
class ListNode {
    int value;
    ListNode next;

    ListNode() {
    }

    ListNode(int value) {
        this.value = value;
    }

    ListNode(int value, ListNode next) {
        this.value = value;
        this.next = next;
    }

    public ListNode createList(int[] a) {
        ListNode node = null;
        for (int i = a.length - 1; i >= 0; --i) {
            node = new ListNode(a[i], node);
        }
        return node;
    }

    public void printList() {
        ListNode node = this;
        while (node != null) {
            if (node.next != null) {
                System.out.print(node.value + "→");
            } else {
                System.out.println(node.value);
            }
            node = node.next;
        }
    }
}

//  2021.07.05
//class ListNode {
//    int value;
//    ListNode next;
//
//    ListNode() {
//    }
//
//    ListNode(int value) {
//        this.value = value;
//    }
//
//    ListNode(int value, ListNode next) {
//        this.value = value;
//        this.next = next;
//    }
//
//    ListNode createList(int[] arr) {
//        ListNode node = null;
//        for (int i = arr.length - 1; i >= 0; --i) {
//            node = new ListNode(arr[i], node);
//        }
//        return node;
//    }
//
//    void printList() {
//        ListNode node = this;
//        while (node != null) {
//            if (node.next != null) {
//                System.out.print(node.value + "->");
//            } else {
//                System.out.println(node.value);
//            }
//            node = node.next;
//        }
//    }
//}

//class ListNode {
//    int value;
//    ListNode next;
//    public ListNode() {}
//
//    public ListNode(int value) {
//        this.value = value;
//    }
//
//    public ListNode(int value, ListNode next) {
//        this.value = value;
//        this.next = next;
//    }
//    ListNode createList(int[] a) {
//        ListNode node = null;
//        for (int i = a.length - 1; i >= 0; --i) {
//            node = new ListNode(a[i], node);
//        }
//        return node;
//    }
//
//    void printList() {
//        ListNode node = this;
//        while (node != null) {
//            if (node.next != null) {
//                System.out.print(node.value + "->");
//            } else {
//                System.out.println(node.value);
//            }
//            node = node.next;
//        }
//    }
//}


//  2021.06.12
//class ListNode {
//    int value;
//    ListNode next;
//    ListNode() {}
//
//    ListNode(int value) {
//        this.value = value;
//    }
//
//    ListNode(int value, ListNode next) {
//        this.value = value;
//        this.next = next;
//    }
//
//    public ListNode createList(int[] nums) {
//        ListNode node = null;
//        for (int i = nums.length - 1; i >= 0; --i) {
//            node = new ListNode(nums[i], node);
//        }
//        return node;
//    }
//
//    public void printList() {
//        ListNode node = this;
//        while (node != null) {
//            if (node.next != null) {
//                System.out.print(node.value + "->");
//            } else {
//                System.out.println(node.value);
//            }
//            node = node.next;
//        }
//    }
//}


//  2021.06.09
//class ListNode {
//    int val;
//    ListNode next;
//    public ListNode() {}
//
//    public ListNode(int val) {
//        this.val = val;
//    }
//
//    public ListNode(int val, ListNode next) {
//        this.val = val;
//        this.next = next;
//    }
//
//    public ListNode createList(int[] arr) {
//        ListNode node = null;
//        for (int i = arr.length - 1; i >= 0; --i) {
//            node = new ListNode(arr[i], node);
//        }
//        return node;
//    }
//
//    public void printList() {
//        ListNode node = this;
//        while (node != null) {
//            if (node.next != null) {
//                System.out.print(node.val + "→");
//            } else {
//                System.out.println(node.val);
//            }
//            node = node.next;
//        }
//    }
//
//}

//    2021.06.07
//    static class ListNode {
//        private int val;
//        private ListNode next;
//
//        public ListNode() {
//        }
//
//        public ListNode(int val) {
//            this.val = val;
//        }
//
//        public ListNode(int val, ListNode next) {
//            this.val = val;
//            this.next = next;
//        }
//
//        public ListNode creatList(int[] nums) {
//            ListNode node = null;
//            for (int i = nums.length - 1; i >= 0; --i) {
//                node = new ListNode(nums[i], node);
//            }
//            return node;
//        }
//
//        public void printList() {
//            ListNode node = this;
//            while (node != null) {
//                if (node.next != null) {
//                    System.out.print(node.val + "→");
//                } else {
//                    System.out.println(node.val);
//                }
//                node = node.next;
//            }
//        }
//    }

//    2021.06.06
//    static class ListNode {
//        private int value;
//        private ListNode next;
//
//        public ListNode() {
//        }
//
//        public ListNode(int value) {
//            this.value = value;
//        }
//
//        public ListNode(int value, ListNode next) {
//            this.value = value;
//            this.next = next;
//        }
//
//        public ListNode createList(int[] a) {
//            ListNode node = null;
//            for (int i = a.length - 1; i >= 0; --i) {
//                node = new ListNode(a[i], node);
//            }
//            return node;
//        }
//
//        public void printList() {
//            ListNode node = this;
//            while (node != null) {
//                if (node.next != null) {
//                    System.out.print(node.value + "→");
//                } else {
//                    System.out.println(node.value);
//                }
//                node = node.next;
//            }
//        }
//    }

//    2021.06.05
//    class ListNode {
//        private int val;
//        private ListNode next;
//        ListNode(int val) {
//            this.val = val;
//        }
//        ListNode(int val, ListNode next) {
//            this.val = val;
//            this.next = next;
//        }
//        public ListNode createList(int[] a) {
//            ListNode node = null;
//            for (int i = a.length - 1; i >= 0; --i) {
//                node = new ListNode(a[i], node);
//            }
//            return node;
//        }
//        public void printList() {
//            ListNode node = this;
//            while (node != null) {
//                if (node.next != null) {
//                    System.out.print(node.val + "→");
//                } else {
//                    System.out.println(node.val);
//                }
//                node = node.next;
//            }
//        }
//    }

