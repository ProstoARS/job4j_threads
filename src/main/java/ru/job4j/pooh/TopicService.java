package ru.job4j.pooh;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<
            String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>
            > topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String type = req.httpRequestType();
        String sourceName = req.getSourceName();
        String param = req.getParam();
        String status = "200 OK";
        String text = "";
        if ("POST".equals(type)) {
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>
                    topic = topics.get(sourceName);
            if (topic != null) {
                for (ConcurrentLinkedQueue<String> queue : topic.values()) {
                    queue.add(param);
                }
            }
        } else if ("GET".equals(type)) {
            topics.putIfAbsent(sourceName, new ConcurrentHashMap<>());
            topics.get(sourceName).putIfAbsent(param, new ConcurrentLinkedQueue<>());
                text = topics.get(sourceName).get(param).poll();
            if (text == null) {
                text = "";
                status = "204";
            }
        }
        return new Resp(text, status);
    }
}