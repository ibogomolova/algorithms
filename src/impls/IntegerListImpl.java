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
        checkResize();
        array[size++] = item;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        checkResize();
        checkIndex(index);
        System.arraycopy(array, index, array, index + 1,size - index);
        array[index] = item;
        size++;
        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        checkResize();
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
        sort();
        return binarySearch(item);
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

    private int binarySearch(Integer item) {
        int left = 0;
        int right = size - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid].equals(item)) {
                return mid;
            } else if (array[mid] < item) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    private void sort() {
        mergeSort(array);
    }

    public static void mergeSort(Integer[] table) {
        if (table.length < 2) {
            return;
        }
        int mid = table.length / 2;
        Integer[] left = new Integer[mid];
        Integer[] right = new Integer[table.length - mid];

        for (int i = 0; i < left.length; i++) {
            left[i] = table[i];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = table[mid + i];
        }
        mergeSort(left);
        mergeSort(right);
        merge(table, left, right);
    }
    public static void merge(Integer[] table, Integer[] left, Integer[] right) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                table[k++] = left[i++];
            } else {
                table[k++] = right[j++];
            }
        }

        while (i < left.length) {
            table[k++] = left[i++];
        }

        while (j < right.length) {
            table[k++] = right[j++];
        }
    }

    private void grow() {
        array = Arrays.copyOf(array, (int) (array.length * 1.5));
    }

    public String toString() {
        return Arrays.toString(this.toArray());
    }

    private void checkResize() {
        if (size >= array.length) {
            grow();
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
