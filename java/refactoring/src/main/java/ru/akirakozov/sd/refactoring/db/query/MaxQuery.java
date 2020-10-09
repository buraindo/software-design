package ru.akirakozov.sd.refactoring.db.query;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MaxQuery implements Query<Optional<Product>> {
    @Override
    public Optional<Product> getResult(final ResultSet resultSet) {
        try {
            return Optional.ofNullable(resultSet.next() ? Product.fromResultSet(resultSet) : null);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getQuery() {
        return "select * from product order by price desc limit 1";
    }
}
