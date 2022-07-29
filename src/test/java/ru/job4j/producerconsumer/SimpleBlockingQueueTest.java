package ru.job4j.producerconsumer;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenFetchAllThenGetIt() {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(2);
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(a -> {
                    try {
                        sbq.offer(a);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
        );
        Thread consumer = new Thread(
                () -> {
                    while (!sbq.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(sbq.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        try {
            producer.join();
            consumer.interrupt();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}