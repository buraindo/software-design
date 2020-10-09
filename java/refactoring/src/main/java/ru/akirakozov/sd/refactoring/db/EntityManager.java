package ru.akirakozov.sd.refactoring.db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class EntityManager {
    private final String address;

    public EntityManager(final String address) {
        this.address = address;
    }

    public void execute(final String query, final Consumer<ResultSet> callback) throws SQLException {
        try (final var connection = DriverManager.getConnection(address)) {
            final var resultSet = connection.prepareStatement(query).executeQuery();
            if (callback != null) {
                callback.accept(resultSet);
            }
        }
    }

    public void execute(final String query) throws SQLException {
        try (final var connection = DriverManager.getConnection(address)) {
            connection.prepareStatement(query).execute();
        }
    }

    public <T> T execute(final String query, final Function<ResultSet, T> mapper) throws SQLException {
        try (final var connection = DriverManager.getConnection(address)) {
            return mapper.apply(connection.prepareStatement(query).executeQuery());
        }
    }

}
