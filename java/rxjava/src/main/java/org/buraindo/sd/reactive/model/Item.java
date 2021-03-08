package org.buraindo.sd.reactive.model;

public class Item {

    private final String name;
    private final Double price; // in dollars

    public Item(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return toString("usd");
    }

    public String toString(String currency) {
        return String.format("%s, %f%s", name, price, currency);
    }

}
