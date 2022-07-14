package ru.job4j.cash;

public class Account {

    private final int id;
    private final int amount;

    public Account(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }
}
