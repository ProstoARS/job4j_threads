package ru.job4j.producerconsumer;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    public void whenOffer() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>();
        List<Integer> list = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    sbq.offer(5);
                    sbq.offer(2);
                    sbq.offer(4);
                }
        );
        Thread consumer = new Thread(
                () -> {
                    list.add(sbq.poll());
                    list.add(sbq.poll());
                    list.add(sbq.poll());
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(list).isEqualTo(List.of(5, 2, 4));
    }
}