package Queue.MemorialWriting;

import java.util.Arrays;

public class AQ_0902 {
    private Object[] elements;
    private int size, capacity = 10;
    private int max = Integer.MAX_VALUE - 8;
    private int head, tail;

    public AQ_0902() {
        elements = new Object[this.capacity];
        initPointer(0, 0);
    }

    public AQ_0902(int capacity) {
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

    private void grow(int oldCapacity) {
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity < oldCapacity) {
            newCapacity = capacity;
        }
        newCapacity = newCapacity > max ? Integer.MAX_VALUE : newCapacity;
        elements = Arrays.copyOf(elements, newCapacity);
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
        return size;
    }

    public int getCapacity() {
        return elements.length;
    }

}
