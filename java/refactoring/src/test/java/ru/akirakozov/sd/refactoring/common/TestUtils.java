package ru.akirakozov.sd.refactoring.common;

import java.sql.DriverManager;
import java.sql.SQLException;

public class TestUtils {
    public static void clearProductTable(final String address) throws SQLException {
        try (final var connection = DriverManager.getConnection(address)) {
            final var query = "drop table if exists product";
            connection.prepareStatement(query).execute();
        }

        try (final var connection = DriverManager.getConnection(address)) {
            final var query = """
                    create table if not exists product(
                        id integer primary key autoincrement not null,
                        name text not null,
                        price int not null
                    )
                    """;
            connection.prepareStatement(query).execute();
        }
    }
}
