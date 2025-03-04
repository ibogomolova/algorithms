package interfaces;

import exception.ArrayIndexException;

import java.util.Arrays;

public class StringListImpl implements StringList {

    private String[] array;
    private int size;

    public StringListImpl(int length) {
        this.array = new String[length];
    }

    @Override
    public String add(String item) {
        checkLength();
        array[size++] = item;
        return item;
    }

    @Override
    public String add(int index, String item) {
        checkLength();
        checkIndex(index);
        System.arraycopy(array, index, array, index + 1,size - index);
        array[index] = item;
        size++;
        return item;
    }

    @Override
    public String set(int index, String item) {
        checkLength();
        checkIndex(index);
        array[index] = item;
        return item;
    }

    @Override
    public String remove(String item) {
        return remove(indexOf(item));
    }

    @Override
    public String remove(int index) {
        checkSearchIndex(index);
        String item = array[index];
        System.arraycopy(array, index + 1, array, index,size - index);
        array[size - 1] = null;
        size--;
        return item;
    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(String item) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get(int index) {
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
        array = new String[array.length];
    }

    @Override
    public String[] toArray() {
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
