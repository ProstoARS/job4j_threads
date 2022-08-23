package ru.job4j.pooh;

import java.util.Objects;

public class Resp {
    private final String text;
    private final String status;

    public Resp(String text, String status) {
        this.text = text;
        this.status = status;
    }

    public String text() {
        return text;
    }

    public String status() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resp)) {
            return false;
        }
        Resp resp = (Resp) o;
        return Objects.equals(text, resp.text) && Objects.equals(status, resp.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, status);
    }
}
