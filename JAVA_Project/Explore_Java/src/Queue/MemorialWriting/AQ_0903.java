package Queue.MemorialWriting;

import java.util.Arrays;

public class AQ_0903 {
    private Object[] elements;
    private int size, capacity = 10;
    private int head, tail;
    private int max = Integer.MAX_VALUE - 8;

    public AQ_0903() {
        elements = new Object[capacity];
        initPointer(0, 0);
    }

    public AQ_0903(int capacity) {
        elements = new Object[capacity];
        initPointer(0, 0);
    }

    private void initPointer(int head, int tail) {
        this.head = head;
        this.tail = tail;
    }

    public boolean enqueue(Object element) {
        ensureCapacity();
        elements[tail++] = element;
        ++size;
        return true;
    }

    private void ensureCapacity() {
        if (tail == elements.length) {
            if (head == 0) {
                grow(elements.length);
            } else {
                if (tail > head) {
                    System.arraycopy(elements, head, elements, 0, tail - head);
                }
                initPointer(0, tail - head);
            }
        }
    }

    private void grow(int oldLength) {
        int newLength = oldLength + (oldLength >> 1);
//        if (newLength < oldLength) {
//            newLength = capacity;
//        }
        newLength = Math.min(newLength, max);
        elements = Arrays.copyOf(elements, newLength);
    }

    public Object deque() {
        if (head == tail) {
            return null;
        }
        Object element = elements[head++];
        --size;
        return element;
    }

    public int getSize() {
        return this.size;
    }

    public int getCapacity() {
        return elements.length;
    }
}
