/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

import java.util.Comparator;

/**
 *
 * @author aaron
 */
public interface ListInterface<T> extends Iterable<T> {

    public void add(T newEntry);

    public boolean addAt(int index, T newEntry);

    public boolean remove(T entry);

    public T removeAt(int index);

    public boolean update(int index, T newEntry);

    public T get(int index);

    public int indexOf(T entry);

    public int size();

    public void clear();

    public boolean contains(T entry);

    public void displayList();

    public void sort(Comparator<T> comparator);

    public boolean isEmpty();

    public T[] toArray(T[] a);
}
