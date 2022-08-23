package ru.job4j.pooh;

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
            if (topics.containsKey(sourceName)) {
                topics.get(sourceName).forEach((a, b) -> b.add(param));
            } else {
                throw new IllegalArgumentException();
            }
        } else if ("GET".equals(type)) {
            topics.putIfAbsent(sourceName, new ConcurrentHashMap<>());
            if (topics.get(sourceName).putIfAbsent(param, new ConcurrentLinkedQueue<>()) != null) {
                text = topics.get(sourceName).get(param).poll();
            } else {
                status = "204";
            }
        }
        return new Resp(text, status);
    }
}