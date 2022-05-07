package Stack;

import java.util.Arrays;

/**
 * 2021.09.16：继续，可仿造用数组实现队列的思路来用数组实现栈。
 * 2021.09.17：已实现并暂时把BUG修复
 */

public class StackArray {
    private int size, capacity = 10;
    private Object[] elements;
    private int top = -1;
    private int max = Integer.MAX_VALUE - 8;

    public StackArray() {
        this.elements = new Object[capacity];
    }

    public StackArray(int capacity) {
        this.elements = new Object[capacity];
    }

    public boolean push(Object element) {
        if (size == max) {
            return false;
        }
        if (isFull()) {
            grow(elements.length);
        }
        this.elements[++top] = element;
        ++this.size;
        return true;
    }

    private void grow(int length) {
        int newLength = length + (length >> 1) + 1;
        newLength = Math.min(newLength, max);
        elements = Arrays.copyOf(elements, newLength);
    }

    public Object pop() {
        if (isFull()) {
            return null;
        }
        Object delete_num = this.elements[top--];
        --this.size;
        return delete_num;
    }

    public Object peek() {
        if (isEmpty()) {
            return null;
        }
        Object value = this.elements[top];
        return value;
    }

    public boolean isFull() {
        return top == elements.length - 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int getSize() {
        return this.size;
    }

    public int getCapacity() {
        return elements.length;
    }

    public static void main(String[] args) {
        StackArray stackArray = new StackArray(5);
        stackArray.push(9);
        stackArray.push(59);
        stackArray.push(39);
        stackArray.push(29);
        stackArray.push(19);
        System.out.println("Top: " + stackArray.top);
        System.out.println("Capacity: " + stackArray.getCapacity() + "\nSize: " + stackArray.getSize() + "\nPeek: " + stackArray.peek());
        System.out.println("DeleteNum: " + stackArray.pop());
        stackArray.push(59);
        System.out.println("Top: " + stackArray.top);
        stackArray.push(33);
        stackArray.push(73);
        stackArray.push(43);
        stackArray.push(23);
        System.out.println("Capacity: " + stackArray.getCapacity() + "\nSize: " + stackArray.getSize() + "\nPeek: " + stackArray.peek());
        System.out.println("DeleteNum: " + stackArray.pop());
    }

}
