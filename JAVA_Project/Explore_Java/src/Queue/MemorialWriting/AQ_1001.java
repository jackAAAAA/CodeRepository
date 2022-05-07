package Queue.MemorialWriting;

import java.util.Arrays;

public class AQ_1001 {
    private Object[] elements;
    private int size, capacity = 10;
    private int max = Integer.MAX_VALUE - 8;
    private int head, tail;

    public AQ_1001() {
        elements = new Object[capacity];
        initPointer(0, 0);
    }

    public AQ_1001(int capacity) {
        elements = new Object[capacity];
        initPointer(0, 0);
    }

    private void initPointer(int head, int tail) {
        this.head = head;
        this.tail = tail;
    }

    public boolean enqueue(Object obj) {
        if (isFull()) {
            return false;
        }
        ensureCapacity();
        ++size;
        elements[tail++] = obj;
        return true;
    }

    private void ensureCapacity() {
        if (tail == elements.length) {
            if (head == 0) {
                grow(elements.length);
            } else {
                System.arraycopy(elements, head, elements, 0, tail - head);
                initPointer(0, tail - head);
            }
        }
    }

    private void grow(int length) {
        int newLength = length + (length >> 1) + 1;
        newLength = Math.min(newLength, max);
        elements = Arrays.copyOf(elements, newLength);
    }

    private boolean isFull() {
        return this.size == max;
    }

    private boolean isEmpty() {
        return head == tail;
    }

    public Object deque() {
        if (isEmpty()) {
            return null;
        }
        Object obj = elements[head++];
        --size;
        return obj;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return elements.length;
    }

    public void printHead_Tail() {
        System.out.println("Head: " + this.head + "\n" + "Tail: " + this.tail);
    }


}
