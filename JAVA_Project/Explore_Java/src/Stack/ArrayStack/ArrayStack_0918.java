package Stack.ArrayStack;

import java.util.Arrays;

public class ArrayStack_0918 {
    private int size = 0, capacity = 10;
    private Object[] elements;
    private int top = -1, max = Integer.MAX_VALUE - 8;

    public ArrayStack_0918() {
        elements = new Object[capacity];
    }

    public ArrayStack_0918(int capacity) {
        elements = new Object[capacity];
    }

    public boolean push(Object obj) {
        if (size == max) {
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
        return top == elements.length - 1;
    }

    public Object pop() {
        if (isEmpty()) {
            return null;
        }
        Object delete_num = elements[top--];
        --size;
        return delete_num;
    }

    private boolean isEmpty() {
        return top == -1;
    }

    public Object peek() {
        if (isEmpty()) {
            return null;
        }
        Object element = elements[top];
        return element;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return elements.length;
    }

    public static void main(String[] args) {
        ArrayStack_0918 arrayStack_0918 = new ArrayStack_0918(5);
        arrayStack_0918.push(3);
        arrayStack_0918.push(93);
        arrayStack_0918.push(1);
        arrayStack_0918.push(2);
        arrayStack_0918.push(6);
        System.out.println("Peek: " + arrayStack_0918.peek());
        System.out.println("Pop: " + arrayStack_0918.pop());
        System.out.println("Peek: " + arrayStack_0918.peek());
        System.out.println("CurSize: " + arrayStack_0918.getSize());
        System.out.println("CurCapacity: " + arrayStack_0918.getCapacity());
        arrayStack_0918.push(12);
        arrayStack_0918.push(61);
        System.out.println("CurSize: " + arrayStack_0918.getSize());
        System.out.println("CurCapacity: " + arrayStack_0918.getCapacity());
    }
    //预期结果：6 6 2 4 5 6 8
}
