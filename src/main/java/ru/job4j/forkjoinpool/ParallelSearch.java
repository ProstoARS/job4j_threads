package ru.job4j.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private static final int MIN_ARRAY_LENGTH = 10;
    private final T[] array;
    private final int from;
    private final int to;
    private final T value;

    public ParallelSearch(T[] array, int from, int to, T value) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    public static <T> Integer findIndex(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(array, 0, array.length - 1, value));
    }

    @Override
    protected Integer compute() {
        if (to - from <= MIN_ARRAY_LENGTH) {
           return search();
        }
        int mid = (to + from) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(array, from, mid, value);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(array, mid + 1, to, value);
        leftSearch.fork();
        rightSearch.fork();
        return Math.max(leftSearch.join(), rightSearch.join());
    }

    private int search() {
        for (int i = from; i <= to; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }
}
