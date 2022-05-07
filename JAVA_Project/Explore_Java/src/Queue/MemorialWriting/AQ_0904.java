package Queue.MemorialWriting;

import java.util.Arrays;

public class AQ_0904 {
    private int size, capacity = 10;
    private Object[] elements;
    private int head, tail;
    private int max = Integer.MAX_VALUE - 8;

    public AQ_0904() {
        elements = new Object[capacity];
        initPointer(0, 0);
    }

    public AQ_0904(int capacity) {
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

    private void grow(int length) {
        int newLength = length + (length >> 1) + 1;
        newLength = Math.min(newLength, max);
        elements = Arrays.copyOf(elements, newLength);
    }

    public Object deque() {
        if (head == tail) {
            return null;
        }
        Object obj = elements[head++];
        --size;
        return obj;
    }

    public int getSize() {
        return this.size;
    }

    public int getCapacity() {
        return elements.length;
    }

}
