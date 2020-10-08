package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddProductServletTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final StringWriter writer = new StringWriter();
    private final AddProductServlet addProductServlet = new AddProductServlet();

    private static final String DB_ADDRESS = "jdbc:sqlite:test.db";

    @BeforeEach
    public void setup() throws IOException, SQLException {
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        try (final var connection = DriverManager.getConnection(DB_ADDRESS)) {
            final var query = "drop table if exists product";
            connection.prepareStatement(query).execute();
        }

        try (final var connection = DriverManager.getConnection(DB_ADDRESS)) {
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

    @Test
    public void testAddNoError() throws IOException {
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("price")).thenReturn("42");
        addProductServlet.doGet(request, response);
        assertEquals("OK\n", writer.toString());
    }

    @Test
    public void testAddHasErrorOnGettingParams() {
        when(request.getParameter("name")).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> addProductServlet.doGet(request, response));
        assertEquals("", writer.toString());
    }

    @Test
    public void testAddPriceEmpty() {
        when(request.getParameter("name")).thenReturn("test");
        assertThrows(NumberFormatException.class, () -> addProductServlet.doGet(request, response));
        assertEquals("", writer.toString());
    }

    @Test
    public void testAddPriceNotNumber() {
        when(request.getParameter("price")).thenReturn("not a number");
        assertThrows(NumberFormatException.class, () -> addProductServlet.doGet(request, response));
        assertEquals("", writer.toString());
    }

    @Test
    public void testAddNameEmpty() throws IOException {
        when(request.getParameter("price")).thenReturn("42");
        addProductServlet.doGet(request, response);
        assertEquals("OK\n", writer.toString());
    }

    @Test
    public void testAddEmptyParameters() {
        assertThrows(NumberFormatException.class, () -> addProductServlet.doGet(request, response));
        assertEquals("", writer.toString());
    }

}
