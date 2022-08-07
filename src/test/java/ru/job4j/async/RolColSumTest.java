package ru.job4j.async;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    public void whenConsistentSums() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolColSum.Sums[] expected = {
                new RolColSum.Sums(12, 6),
                new RolColSum.Sums(15,  15),
                new RolColSum.Sums(18, 24)
        };
        RolColSum.Sums[] result = RolColSum.sum(matrix);
        assertThat(expected).isEqualTo(result);
    }

    @Test
    public void whenAsyncSums() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolColSum.Sums[] expected = {
                new RolColSum.Sums(12, 6),
                new RolColSum.Sums(15,  15),
                new RolColSum.Sums(18, 24)
        };
        RolColSum.Sums[] result = RolColSum.asyncSum(matrix);
        assertThat(expected).isEqualTo(result);
    }

}