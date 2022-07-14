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
            if (!accounts.containsKey(account.getId())) {
                accounts.put(account.getId(), account);
                return true;
            }
        }
        System.out.println("account with current Id already exist!");
        return false;
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            if (accounts.containsKey(account.getId())) {
                accounts.replace(account.getId(), account);
                return true;
            }
        }
        System.out.println("this account does not exist!");
        return false;
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            accounts.remove(id);
            return true;
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            if (accounts.containsKey(id)) {
                return Optional.of(accounts.get(id));
            }
        }
        return Optional.empty();
    }

    public boolean transfer(int fromId, int toId, int amount) {

        if (getById(fromId).isPresent() && getById(toId).isPresent()) {
            if (getById(fromId).get().getAmount() >= amount) {
                synchronized (accounts) {
                    update(new Account(getById(fromId).get().getId(),
                            getById(fromId).get().getAmount() - amount));
                    update(new Account(getById(toId).get().getId(),
                            getById(toId).get().getAmount() + amount));
                    return true;
                }
            } else {
                System.out.println("there are not enough funds on the account!");
            }
        } else {
            System.out.println("accounts not found!");
        }
        return false;
    }
}
