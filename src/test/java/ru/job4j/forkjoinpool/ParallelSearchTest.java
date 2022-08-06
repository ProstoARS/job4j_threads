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

    @Test
    public void whenSearchLastElement() {
        String[] array = new String[3];
        array[0] = "ars";
        array[1] = "pablo";
        array[2] = "vova";
        Integer result = ParallelSearch.findIndex(array, "vova");
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void whenNoElement() {
        String[] array = new String[3];
        array[0] = "ars";
        array[1] = "pablo";
        array[2] = "vova";
        Integer result = ParallelSearch.findIndex(array, "dimentiy");
        assertThat(result).isEqualTo(-1);
    }
}