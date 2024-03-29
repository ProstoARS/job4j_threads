package ru.job4j.synch;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;


public class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        SingleLockList<Integer> list = new SingleLockList<>(new ArrayList<>());
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl).containsAll(Set.of(1, 2));
    }
}