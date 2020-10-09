package ru.akirakozov.sd.refactoring.db;

import ru.akirakozov.sd.refactoring.db.query.Query;
import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.utils.ThrowingConsumer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements Dao<Product> {
    private final EntityManager entityManager = new EntityManager(DB_ADDRESS);

    private static final String DB_ADDRESS = "jdbc:sqlite:test.db";

    @Override
    public List<Product> findAll() {
        final var result = new ArrayList<Product>();
        try {
            entityManager.execute("select * from product", ThrowingConsumer.unchecked(resultSet -> {
                while (resultSet.next()) {
                    final var product = new Product() {{
                        setId(resultSet.getLong("id"));
                        setName(resultSet.getString("name"));
                        setPrice(resultSet.getLong("price"));
                    }};
                    result.add(product);
                }
            }));
            return result;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(final Product product) {
        try {
            entityManager.execute(String.format("insert into product(name, price) values ('%s', %d)", product.getName(), product.getPrice()));
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int count() {
        return findAll().size();
    }

    @Override
    public <E> E findByQuery(final Query<E> query) {
        try {
            return entityManager.execute(query.getQuery(), query::getResult);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
