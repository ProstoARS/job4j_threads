package ru.job4j.forkjoinpool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelSearchTest {

    @Test
    public void whenSearchIndex() {
        Integer[] array = new Integer[200];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 10;
        }
        Integer result = ParallelSearch.findIndex(array, 105);
        assertThat(result).isEqualTo(95);
    }
}