package Queue.MemorialWriting;

import java.util.Arrays;

public class AQ_0901 {
    private Object[] elements;
    private int size;
    private int capacity = 10;
    private int head, tail;
    private int max = Integer.MAX_VALUE - 8;

    public AQ_0901() {
        elements = new Object[capacity];
        initPointer(0, 0);
    }

    public AQ_0901(int capacity) {
        elements = new Object[capacity];
        initPointer(0, 0);
    }

    private void initPointer(int i, int i1) {
        this.head = head;
        this.tail = tail;
    }

    public boolean enqueue(Object element) {
        ensureCapactyHelper();
        elements[this.tail++] = element;
        ++this.size;
        return true;
    }

    private void ensureCapactyHelper() {
        if (this.tail == elements.length) {
            if (this.head == 0) {
                grow(elements.length);
            } else {
                if (this.tail > this.head) {
                    System.arraycopy(elements, head, elements, 0,tail - head);
                }
                initPointer(0, this.tail - this.head);
            }
        }
    }

    private void grow(int oldCapacity) {
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity < oldCapacity) {
            newCapacity = capacity;
        }
        if (newCapacity > this.max) {
            newCapacity = hugeCapacity(newCapacity);
        }
        elements = Arrays.copyOf(elements, newCapacity);
    }

    private int hugeCapacity(int newCapacity) {
        return newCapacity > this.max ? Integer.MAX_VALUE : newCapacity;
    }

    public Object dequeue() {
        if (this.head == this.tail) {
            return null;
        }
        Object obj = elements[this.head++];
        --this.size;
        return obj;
    }

    public int getSize() {
        return this.size;
    }

}
