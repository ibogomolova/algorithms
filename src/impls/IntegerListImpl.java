package impls;

import exception.ArrayIndexException;
import interfaces.IntegerList;
import interfaces.StringList;

import java.util.Arrays;

public class IntegerListImpl implements IntegerList {
    private Integer[] array;
    private int size;

    public IntegerListImpl(int length) {
        this.array = new Integer[length];
    }

    @Override
    public Integer add(Integer item) {
        checkLength();
        array[size++] = item;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        checkLength();
        checkIndex(index);
        System.arraycopy(array, index, array, index + 1,size - index);
        array[index] = item;
        size++;
        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        checkLength();
        checkIndex(index);
        array[index] = item;
        return item;
    }

    @Override
    public Integer remove(Integer item) {
        return remove(indexOf(item));
    }

    @Override
    public Integer remove(int index) {
        checkSearchIndex(index);
        Integer item = array[index];
        System.arraycopy(array, index + 1, array, index,size - index);
        array[size - 1] = null;
        size--;
        return item;
    }

    @Override
    public boolean contains(Integer item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(Integer item) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        checkIndex(index);
        return array[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        if (otherList == this) return true;
        if (otherList == null) return false;

        return Arrays.equals(otherList.toArray(), this.toArray());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        array = new Integer[array.length];
    }

    @Override
    public Integer[] toArray() {
        return Arrays.copyOf(array, size);
    }

    private void checkLength() {
        if (size >= array.length) {
            throw new ArrayIndexException("Не осталось свободных индексов для добавления элемента ");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexException("Отрицательный, либо выходящий за размер массива индекс ");
        }
    }

    private static void checkSearchIndex(int index) {
        if (index == -1) {
            throw new ArrayIndexException("Индекс не найден ");
        }
    }
}
