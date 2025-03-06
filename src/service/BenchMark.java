package service;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

public class BenchMark {
    private static final Random RANDOM = new Random();

    public static void printSpeedTest(Consumer<Integer[]> sorting, Integer[] array, String method) {
        long start = System.currentTimeMillis();
        sorting.accept(array);
        long finish = System.currentTimeMillis();
        System.out.printf("Sorting: %s, time: %s ms.%n", method, (finish - start));
    }

    private static void bubbleSort(Integer[] table) {   // O(n^2)
        boolean swapped;
        for (int i = 0; i < table.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < table.length - i - 1; j++) {
                if (table[j] > table[j + 1]) {
                    swapElements(table, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    private static void swapElements(Integer[] table, int fir, int sec) {
        int temp = table[fir];
        table[fir] = table[sec];
        table[sec] = temp;
    }

    private static void insertionSort(Integer[] table) {
        for (int i = 1; i < table.length; i++) {
            int key = table[i];
            int j = i - 1;
            while (j >= 0 && table[j] > key) {
                table[j + 1] = table[j];
                j = j - 1;
            }
            table[j + 1] = key;
        }
    }

    private static void selectionSort(Integer[] table) {
        for (int i = 0; i < table.length - 1; i++) {
            int minindex = i;
            for (int j = i + 1; j < table.length; j++) {
                if (table[j] < table[minindex]) {
                    minindex = j;
                }
            }
            swapElements(table, minindex, i);
        }
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

    public static void main(String[] args) {
        Integer[] testArray = new Integer[50000];
        for (int a = 0; a < testArray.length; a++) {
            testArray[a] = RANDOM.nextInt();
        }
        printSpeedTest(BenchMark::mergeSort,
                Arrays.copyOf(testArray, testArray.length),
                "mergeSort");
        printSpeedTest(BenchMark::selectionSort,
                Arrays.copyOf(testArray, testArray.length),
                "selectionSort");
        printSpeedTest(BenchMark::insertionSort,
                Arrays.copyOf(testArray, testArray.length),
                "insertionSort");
        printSpeedTest(BenchMark::bubbleSort,
                Arrays.copyOf(testArray, testArray.length),
                "bubbleSort");
    }
}