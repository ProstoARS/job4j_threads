package ru.job4j.async;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Sums)) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums(0, 0);
            for (int j = 0; j < matrix[i].length; j++) {
                sums[i].setColSum(sums[i].getColSum() + matrix[i][j]);
                sums[i].setRowSum(sums[i].getRowSum() + matrix[j][i]);
            }
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> map = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            map.put(i, getTask(i, matrix));
        }
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = map.get(i).get();
        }
        return sums;
    }

    private static CompletableFuture<Sums> getTask(int i, int[][] matrix) {
       return CompletableFuture.supplyAsync(() -> {
           Sums sums = new Sums(0, 0);
           for (int j = 0; j < matrix[i].length; j++) {
               sums.setColSum(sums.getColSum() + matrix[i][j]);
               sums.setRowSum(sums.getRowSum() + matrix[j][i]);
           }
           return sums;
       });
    }

}
