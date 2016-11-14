package com.paris.sud.crawler.queuemanagement;

/**
 * Created by Hadhami on 13/11/2016.
 */

import java.util.*;

public class QueueWithPriority<E> {
    private final Comparator<? super E> comparator;
    private final E[] objects;

    private int head = 0;
    private int tail = 0;

    public E[] getObjects() {
        return objects;
    }

    public int getHead() {
        return head;
    }

    public int getTail() {
        return tail;
    }

    @SuppressWarnings("unchecked")
    public QueueWithPriority(int capacity, Comparator<? super E> comparator) {
        this.objects = (E[]) new Object[capacity];
        this.comparator = comparator;
    }


    public void add(E elem) {
        if (tail == objects.length) {
            // shift down |-----AAAAAAA|
            tail -= head;
            System.arraycopy(objects, head, objects, 0, tail);
            head = 0;
        }

        if (tail == head || comparator.compare(objects[tail - 1], elem) <= 0) {
            // Append
            objects[tail++] = elem;
        } else if (head > 0 && comparator.compare(objects[head], elem) > 0) {
            // Prepend
            objects[--head] = elem;
        } else {
            // Insert in the middle
            int index = binarySearch(head, tail - 1, elem);
            System.arraycopy(objects, index, objects, index + 1, tail - index);
            objects[index] = elem;
            tail++;
        }
    }

    public E peek() {
        return (head != tail) ? objects[head] : null;
    }

    public E poll() {
        E elem = objects[head];
        head = (head + 1) % objects.length;
        if (head == 0) tail = 0;
        return elem;
    }

    public int size() {
        return tail - head;
    }

    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    public boolean contains(Object o) {
        for (int i = head; i < tail; ++i) {
            if (objects[i] == o) {
                return true;
            }
        }
        return false;
    }

    public int remainingCapacity() {
        return this.objects.length - (tail - head);
    }

    private int binarySearch(int start, int end, E key) {
        while (start < end) {
            int mid = (start + end) / 2;
            E mitem = objects[mid];
            int cmp = comparator.compare(mitem, key);
            if (cmp > 0) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return (start);
    }

    public void dump() {
        int mask = objects.length - 1;
        int head = this.head & mask;
        int tail = this.tail & mask;
        System.out.printf("head=%2d tail=%2d ", head, tail);
        for (int i = 0; i < objects.length; ++i) {
            if (objects[i] == null) {
                System.out.print("__");
            } else {
                System.out.print(objects[i]);
            }
            System.out.print(' ');
        }
        System.out.println();
    }

}
