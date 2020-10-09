package ru.akirakozov.sd.refactoring.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    private Long id;
    private String name;
    private Long price;

    public Product() {}

    public Product(final String name, final Long price) {
        this.name = name;
        this.price = price;
    }

    public Product(final Long id, final String name, final Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public static Product fromResultSet(final ResultSet resultSet) throws SQLException {
        return new Product(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getLong("price"));
    }
}
