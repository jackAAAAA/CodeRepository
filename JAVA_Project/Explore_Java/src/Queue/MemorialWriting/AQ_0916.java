package Queue.MemorialWriting;

import java.util.Arrays;

public class AQ_0916 {
    private int size, capacity = 10;
    private Object[] elements;
    private int head, tail;
    private int max = Integer.MAX_VALUE - 8;

    public AQ_0916() {
        elements = new Object[this.capacity];
        initPointer(0, 0);
    }

    public AQ_0916(int capacity) {
        elements = new Object[capacity];
        initPointer(0, 0);
    }

    private void initPointer(int head, int tail) {
        this.head = head;
        this.tail = tail;
    }

    public boolean enqueue(Object obj) {
        ensureCapacity();
        elements[tail++] = obj;
        ++this.size;
        return true;
    }

    public void ensureCapacity() {
        if (isFull()) {
            if (this.head == 0) {
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
        if (isEmpty()) {
            return null;
        }
        Object obj = elements[head++];
        --this.size;
        return obj;
    }

    public boolean isEmpty() {
        return this.head == this.tail;
    }

    public boolean isFull() {
        return this.tail == elements.length;
    }

    public int getSize() {
        return this.size;
    }

    public int getCapacity() {
        return elements.length;
    }


}
