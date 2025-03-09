/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package adt;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author aaron
 * @param <T>
 */
public class List<T> implements ListInterface<T> {

    private T[] elements;
    private int size;
    private int iteratorIndex;

    //constructor
    public List() {
        elements = (T[]) new Object[10]; // Initial Capacity
        size = 0;
        iteratorIndex = 0;
    }

    public void checkDoubleArray() {
        if (size == elements.length) {
            T[] newElements = (T[]) new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, elements.length);
            elements = newElements;
        }
    }

    @Override
    public void add(T newEntry) {
        checkDoubleArray();
        elements[size++] = newEntry;
    }

    @Override
    public boolean addAt(int index, T newEntry) {
        if (index < 0 || index > size) {
            return false;
        }
        checkDoubleArray();
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = newEntry;
        size++;
        return true;
    }

    @Override
    public boolean remove(T entry) {
        for (int i = 0; i < size; i++) {
            if (entry.equals(elements[i])) {
                removeAt(i); //removeAt method reuse to remove by index
                return true;
            }
        }
        return false; //if entry not found , return false
    }

    @Override
    public T removeAt(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        T removedElement = (T) elements[index];

        int indexMoved = size - index - 1;

        if (indexMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, indexMoved);
        }
        elements[--size] = null;
        return removedElement;
    }

    @Override
    public boolean update(int index, T newEntry) {
        if (index >= 0 || index < size) {
            elements[index] = newEntry;
            return true;
        }
        return false;
    }

    @Override
    public T get(int index) {
        if (index >= 0 || index < size) {
            return (T) elements[index];
        }
        return null;
    }

    @Override
    public int indexOf(T entry) {
        for (int i = 0; i < size; i++) {
            if (entry == null && elements[i] == null) {
                return i;
            } else if (entry != null && entry.equals(elements[i])) {
                return i;
            }
        }
        return -1; //entry not found 
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0; // reset the size
    }

    @Override
    public boolean contains(T entry) {
        return indexOf(entry) >= 0; // reuse the check indexOf 
    }

    @Override
    public void displayList() {
        for (int i = 0; i < size; i++) {
            System.out.println(elements[i]);
        }
    }

    @Override
    public void sort(Comparator<T> comparator) {

        mergeSort(elements, comparator, size);
    }

    private void mergeSort(T[] insertedElements, Comparator<T> comparator, int sizeWithoutNull) {

        int oriLength = insertedElements.length;
        int length = oriLength - (insertedElements.length - sizeWithoutNull);
        if (length <= 1) {
            return; //base case
        }
        int middle = length / 2;
        T[] leftArray = (T[]) new Object[middle];
        T[] rightArray = (T[]) new Object[length - middle];

        int i = 0; //left array
        int j = 0; //right array

        for (; i < length; i++) {
            T element = insertedElements[i];
            if (element != null) {
                if (i < middle) {
                    leftArray[i] = insertedElements[i];
                } else {
                    rightArray[j] = insertedElements[i];
                    j++;
                }

            }
        }
        mergeSort(leftArray, comparator, leftArray.length);
        mergeSort(rightArray, comparator, rightArray.length);

        merge(leftArray, rightArray, insertedElements, comparator);

    }

    private void merge(T[] leftArray, T[] rightArray, T[] array, Comparator<T> comparator) {
        int leftSize = (leftArray.length + rightArray.length) / 2;
        int rightSize = (leftArray.length + rightArray.length) - leftSize;
        int i = 0, l = 0, r = 0; //indices

        //check the conditions for merging
        while (l < leftSize && r < rightSize) {

            if (comparator.compare(leftArray[l], rightArray[r]) <= 0) {
                array[i] = leftArray[l];
                i++;
                l++;
            } else {
                array[i] = rightArray[r];

                i++;
                r++;
            }

        }

        while (l < leftSize) {
            array[i] = leftArray[l];
            i++;
            l++;
        }
        while (r < rightSize) {
            array[i] = rightArray[r];
            i++;
            r++;
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size; // Assuming 'size' is the number of elements in the list
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) elements[currentIndex++]; // Assuming 'elements' is the array storing list items
            }
        };
    }

    // Method to convert List to an array
    @Override
    public T[] toArray(T[] a) {
        if (a.length < size) {
            // If the given array is too small, allocate a new one with the same runtime type
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null; // Set element beyond the size to null, standard behavior of Collection.toArray()
        }
        return a;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;

    }
}
