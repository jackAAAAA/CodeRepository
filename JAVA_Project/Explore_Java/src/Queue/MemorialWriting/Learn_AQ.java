package Queue.MemorialWriting;

import java.util.Arrays;


/**
 *TO DO
 * 反复敲代码内化
 */

public class Learn_AQ {
    private Object[] elements;
    private int size;
    private int capacity = 10;
    private int head, tail;
    private int max = Integer.MAX_VALUE - 8;

    public Learn_AQ() {
        elements = new Object[capacity];
        initPointer(0, 0);
    }

    public Learn_AQ(int capacity) {
        elements = new Object[capacity];
        initPointer(0, 0);
    }

    private void initPointer(int head, int tail) {
        this.head = head;
        this.tail = tail;
    }

    public boolean enqueue(Object element) {
        ensureCapacityHelper();
        elements[this.tail++] = element;
        ++this.size;
        return true;
    }

    private void ensureCapacityHelper() {
        if (this.tail == elements.length) {
//            队列满
            if (this.head == 0) {
                grow(elements.length);
            } else {
//                队列未满，只是队首指针不是指向第0位
                if (this.tail - this.head > 0) {
                    System.arraycopy(elements, head, elements, 0, tail - head);
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
