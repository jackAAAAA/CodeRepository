package Stack.ArrayStack;

import java.util.Arrays;

public class ArrayStack_0919 {
    private int size = 0, capacity = 10;
    private Object[] elements;
    private int max = Integer.MAX_VALUE - 8;
    private int top = -1;

    ArrayStack_0919() {
        this.elements = new Object[capacity];
    }

    ArrayStack_0919(int capacity) {
        this.elements = new Object[capacity];
    }

    public boolean push(Object obj) {
        if (this.size == this.max) {
            return false;
        }
        if (isFull()) {
            grow(elements.length);
        }
        elements[++top] = obj;
        ++size;
        return true;
    }

    private void grow(int length) {
        int newLength = length + (length >> 1) + 1;
        newLength = Math.min(newLength, max);
        elements = Arrays.copyOf(elements, newLength);
    }

    private boolean isFull() {
        return this.top == elements.length - 1;
    }

    public Object pop() {
        if (isEmpty()) {
            return null;
        }
        Object obj = elements[top--];
        --size;
        return obj;
    }

    private boolean isEmpty() {
        return this.top == -1;
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
        return this.elements.length;
    }

    public static void main(String[] args) {
        ArrayStack_0919 arrayStack_0919 = new ArrayStack_0919(5);
        arrayStack_0919.push(3);
        arrayStack_0919.push(13);
        arrayStack_0919.push(23);
        arrayStack_0919.push(33);
        arrayStack_0919.push(43);
        System.out.println("Size: " + arrayStack_0919.getSize());
        System.out.println("Capacity: " + arrayStack_0919.getCapacity());
        System.out.println("DeleteNum: " + arrayStack_0919.pop());
        System.out.println("PeekNum: " + arrayStack_0919.peek());
        arrayStack_0919.push(53);
        arrayStack_0919.push(63);
        System.out.println("Size: " + arrayStack_0919.getSize());
        System.out.println("Capacity: " + arrayStack_0919.getCapacity());
        System.out.println("DeleteNum: " + arrayStack_0919.pop());
        System.out.println("PeekNum: " + arrayStack_0919.peek());
    }

//    Expected results: 5 5 43 33 6 8 63 53
}
