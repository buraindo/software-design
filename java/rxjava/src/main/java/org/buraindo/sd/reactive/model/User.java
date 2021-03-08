package org.buraindo.sd.reactive.model;

public class User {

    private final String login;
    private final String currency;

    public User(String login, String currency) {
        this.login = login;
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", login, currency);
    }

}
