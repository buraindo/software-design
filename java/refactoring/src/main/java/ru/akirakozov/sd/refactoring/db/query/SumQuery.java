package ru.akirakozov.sd.refactoring.db.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SumQuery implements Query<Optional<Long>> {
    @Override
    public Optional<Long> getResult(final ResultSet resultSet) {
        try {
            return Optional.ofNullable(resultSet.next() ? resultSet.getLong(1) : null);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getQuery() {
        return "select sum(price) from product";
    }
}
