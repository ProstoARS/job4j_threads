package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> mapQueue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String type = req.httpRequestType();
        String sourceName = req.getSourceName();
        String param = req.getParam();
        String status = "200 OK";
        String text = "";
        if ("POST".equals(type)) {
            if (mapQueue.putIfAbsent(sourceName,
                    new ConcurrentLinkedQueue<>()) == null) {
                mapQueue.get(sourceName).add(param);
            }
        } else if ("GET".equals(type)) {
            text = mapQueue.get(sourceName).poll();
        }
        return new Resp(text, status);
    }
}