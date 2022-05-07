package Stack.ArrayStack;

import java.util.Arrays;

    /**
     * Expected results:
     * true
     * true
     * true
     * true
     * true
     * 5
     * 5
     * 5
     * true
     * true
     * 6
     * 8
     */

    /**
     * Real results:
     * true
     * true
     * true
     * true
     * true
     * 5
     * 5
     * 5
     * true
     * true
     * 6
     * 8
     */

    /**
     * Error points:
     * 1. Function_Constructor: elements = new Object[capacity];
     * 2. Function_grow: elements = Arrays.copyOf(elements, newLength);
     */

public class ArrayStack_0921 {
    private int size = 0, capacity = 10;
    private Object[] elements;
    private int top = -1, max = Integer.MAX_VALUE - 8;

    public ArrayStack_0921() {
        elements = new Object[capacity];
    }

    public ArrayStack_0921(int capacity) {
        elements = new Object[capacity];
    }

    public boolean push(Object obj) {
        if (this.size == capacity) {
            return false;
        }
        if (isFull()) {
            grow(elements.length);
        }
        elements[++top] = obj;
        ++this.size;
        return true;
    }

    private void grow(int length) {
        int newLength = length + (length >> 1) + 1;
        newLength = Math.min(newLength, max);
        elements = Arrays.copyOf(elements, newLength);
    }

    private boolean isFull() {
        return top == elements.length - 1;
    }

    public Object pop() {
        if (isEmpty()) {
            return null;
        }
        Object obj = elements[top--];
        --this.size;
        return obj;
    }

    private boolean isEmpty() {
        return top == -1;
    }

    public Object peek() {
        if (isEmpty()) {
            return null;
        }
        Object obj = elements[top];
        return obj;
    }

    public int getSize() {
        return this.size;
    }

    public int getCapacity() {
        return elements.length;
    }

    public static void main(String[] args) {
        ArrayStack_0921 arrayStack_0921 = new ArrayStack_0921(5);
        System.out.println(arrayStack_0921.push(3));
        System.out.println(arrayStack_0921.push(6));
        System.out.println(arrayStack_0921.push(1));
        System.out.println(arrayStack_0921.push(2));
        System.out.println(arrayStack_0921.push(5));

        System.out.println(arrayStack_0921.getSize());
        System.out.println(arrayStack_0921.getCapacity());

        System.out.println(arrayStack_0921.pop());

        System.out.println(arrayStack_0921.push(23));
        System.out.println(arrayStack_0921.push(666666));

        System.out.println(arrayStack_0921.getSize());
        System.out.println(arrayStack_0921.getCapacity());
    }

}
