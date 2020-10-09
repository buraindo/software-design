package ru.akirakozov.sd.refactoring.db.query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SumQuery implements Query<Long> {
    @Override
    public Long getResult(final ResultSet resultSet) {
        try {
            return resultSet.next() ? resultSet.getLong(1) : 0;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getQuery() {
        return "select sum(price) from product";
    }
}
