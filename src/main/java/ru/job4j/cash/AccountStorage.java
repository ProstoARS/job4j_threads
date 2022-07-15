package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("accounts")

    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            return accounts.putIfAbsent(account.getId(), account) == null;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            return accounts.replace(account.getId(), account) != null;
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            return accounts.remove(id) == null;
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
                return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
            Optional<Account> from = getById(fromId);
            Optional<Account> to = getById(toId);
        if (from.isPresent() && to.isPresent()
            && from.get().getAmount() >= amount) {
                    update(new Account(getById(fromId).get().getId(),
                            getById(fromId).get().getAmount() - amount));
                    update(new Account(getById(toId).get().getId(),
                            getById(toId).get().getAmount() + amount));
                    return true;
            } else {
                System.out.println("there are not enough funds on the account!");
            }
        }
        return false;
    }
}
