package LinkedList;

public class add_remove {
    public static class Node<E> {
        private E elem;
        private Node<E> next;

        public Node(E elem, Node<E> next) {
            this.elem = elem;
            this.next = next;
        }

        public Node() {
            this(null, null);
        }

        public E getElem() {
            return this.elem;
        }

        public void setElem(E elem) {
            this.elem = elem;
        }

        public Node<E> getNext() {
            return this.next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

    }

    public static class SingleLinkedList {
        Node head;

        public SingleLinkedList() {
            this.head = new Node();
        }

        public int size() {
            int cnt = 0;
            for (Node node = head; node.getNext() != null; node = node.getNext()) {
                ++cnt;
            }
            return cnt;
        }

        public void add(Node node) {
            Node tail = head;
            while (tail.getNext() != null) {
                tail = tail.getNext();
            }
            tail.setNext(node);
        }

        public Object remove(int index) {
            if (index < 0 || index >= size()) {
                throw new IndexOutOfBoundsException();
            }
            Node pre = head;
            while (index > 0) {
                pre = pre.getNext();
                --index;
            }
            Node cur = pre.getNext();
            Node next = cur.getNext();
            pre.setNext(next);
            return cur.getElem();
        }

        public void iterate() {
            for (Node it = head.getNext(); it != null; it = it.getNext()) {
                System.out.println("当前元素是：" + it.getElem());
            }
        }
    }

    public static void main(String[] args) {
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        Node node3 = new Node(3, null);
        Node node2 = new Node(2, null);
        Node node1 = new Node(1, null);

        singleLinkedList.add(node1);
        singleLinkedList.add(node2);
        singleLinkedList.add(node3);
        System.out.println("Initial size: " + singleLinkedList.size());
        singleLinkedList.iterate();

        System.out.println("--------------执行元素删除后-----------------");
        System.out.println("删除的元素是：" + singleLinkedList.remove(1));
        System.out.println("Changed size: " + singleLinkedList.size());
        singleLinkedList.iterate();
    }
}
