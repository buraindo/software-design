package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.common.TestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetProductServletTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final StringWriter writer = new StringWriter();
    private final GetProductsServlet getProductsServlet = new GetProductsServlet();

    private static final String DB_ADDRESS = "jdbc:sqlite:test.db";

    @BeforeEach
    public void setup() throws IOException, SQLException {
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        TestUtils.clearProductTable(DB_ADDRESS);
    }

    @Test
    public void testGetNoError() throws SQLException {
        try (final var connection = DriverManager.getConnection(DB_ADDRESS)) {
            final var query = """
                    insert into product(name, price) values
                        ('test', '42'),
                        ('hello', '24'),
                        ('name', '-11')
                    """;
            connection.prepareStatement(query).execute();
        }
        getProductsServlet.doGet(request, response);
        assertEquals("""
                <html><body>
                test	42</br>
                hello	24</br>
                name	-11</br>
                </body></html>
                """, writer.toString());
    }

    @Test
    public void testGetEmptyDatabaseNoError() {
        getProductsServlet.doGet(request, response);
        assertEquals("""
                <html><body>
                </body></html>
                """, writer.toString());
    }

}
