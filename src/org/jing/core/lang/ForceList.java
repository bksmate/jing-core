package org.jing.core.lang;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-09-23 <br>
 */
@Deprecated
@SuppressWarnings({ "unchecked", "Duplicates" }) public class ForceList<E> implements List<E>, RandomAccess, Cloneable {
    protected static final int MAX_SIZE = Integer.MAX_VALUE - 8;

    private transient Object[] elementData = null;

    private transient int size = 0;

    private transient int modCount = 0;

    public ForceList() {
    }

    @Override public int size() {
        return size;
    }

    @Override public boolean isEmpty() {
        return size == 0;
    }

    @Override public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override public Iterator<E> iterator() {
        return new Itr(0);
    }

    @Override public E[] toArray() {
        return (E[]) Arrays.copyOf(elementData, size);
    }

    @Override @SuppressWarnings({ "unchecked", "SuspiciousSystemArraycopy" }) public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override public boolean add(E e) {
        rangeCheck(size + 1);
        elementData[size] = e;
        size++;
        modCount++;
        return true;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);

        modCount++;
        E oldValue = (E) elementData[index];

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                numMoved);
        elementData[--size] = null; // Let gc do its work

        return oldValue;
    }

    @Override public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        }
        else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    protected void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = null; // Let gc do its work
    }

    @Override public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override public void clear() {
        modCount++;

        // Let gc do its work
        for (int i = 0; i < size; i++)
            elementData[i] = null;

        size = 0;
    }

    @Override public E get(int index) {
        return null;
    }

    @Override public E set(int index, E element) {
        return null;
    }

    @Override public void add(int index, E element) {

    }

    @Override public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        }
        else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        }
        else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    @Override public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    protected void rangeCheck(int index) {
        if (index > MAX_SIZE) {
            throw new JingRuntimeException("size has already been max");
        }
        else if (index < 0) {
            throw new JingRuntimeException("invalid index: {}", index);
        }
        else if (size == 0) {
            elementData = new Object[index];
        }
        else if (index > size) {
            elementData = Arrays.copyOf(elementData, index);
        }
    }

    protected String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + this.size;
    }

    protected class Itr implements Iterator<E> {
        int cursor;       // index of next element to return

        int lastRet = -1; // index of last element returned; -1 if no such

        int expectedModCount = modCount;

        Itr() {
            cursor = 0;
        }

        Itr(int index) {
            cursor = index;
        }

        public boolean isNextNull() {
            return null == ForceList.this.elementData[cursor];
        }

        @Override public boolean hasNext() {
            return cursor != size;
        }

        @Override public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            checkModCount();
            try {
                ForceList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            }
            catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override public E next() {
            checkModCount();
            int i = cursor;
            if (i >= size) {
                throw new NoSuchElementException();
            }
            Object[] elementData = ForceList.this.elementData;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        final void checkModCount() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    protected class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        public E previous() {
            checkModCount();
            int i = cursor - 1;
            if (i < 0) {
                throw new NoSuchElementException();
            }
            Object[] elementData = ForceList.this.elementData;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        public void set(E e) {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            checkModCount();
            try {
                ForceList.this.set(lastRet, e);
            }
            catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkModCount();
            try {
                int i = cursor;
                ForceList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            }
            catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
