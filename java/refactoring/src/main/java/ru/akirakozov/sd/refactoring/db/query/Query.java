package ru.akirakozov.sd.refactoring.db.query;

import java.sql.ResultSet;

public interface Query<T> {
    T getResult(ResultSet resultSet);

    String getQuery();
}
