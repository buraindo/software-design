package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.common.TestUtils;
import ru.akirakozov.sd.refactoring.db.Dao;
import ru.akirakozov.sd.refactoring.db.ProductDao;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QueryServletTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final StringWriter writer = new StringWriter();
    private final QueryServlet queryServlet = new QueryServlet();
    private final Dao<Product> productDao = new ProductDao();

    private static final String DB_ADDRESS = "jdbc:sqlite:test.db";

    @BeforeEach
    public void setup() throws IOException, SQLException {
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        TestUtils.clearProductTable(DB_ADDRESS);
    }

    @Test
    public void testMaxNoError() {
        when(request.getParameter("command")).thenReturn("max");
        productDao.save(new Product("test", 42L));
        productDao.save(new Product("hello", 24L));
        productDao.save(new Product("name", -11L));
        queryServlet.doGet(request, response);
        assertEquals("""
                <html><body>
                <h1>Product with max price: </h1>
                test	42</br>
                </body></html>
                """, writer.toString());
    }

    @Test
    public void testMaxEmptyDatabase() {
        when(request.getParameter("command")).thenReturn("max");
        queryServlet.doGet(request, response);
        assertEquals("""
                <html><body>
                <h1>Product with max price: </h1>
                </body></html>
                """, writer.toString());
    }

    @Test
    public void testMinNoError() {
        when(request.getParameter("command")).thenReturn("min");
        productDao.save(new Product("test", 42L));
        productDao.save(new Product("hello", 24L));
        productDao.save(new Product("name", -11L));
        queryServlet.doGet(request, response);
        assertEquals("""
                <html><body>
                <h1>Product with min price: </h1>
                name	-11</br>
                </body></html>
                """, writer.toString());
    }

    @Test
    public void testMinEmptyDatabase() {
        when(request.getParameter("command")).thenReturn("min");
        queryServlet.doGet(request, response);
        assertEquals("""
                <html><body>
                <h1>Product with min price: </h1>
                </body></html>
                """, writer.toString());
    }

    @Test
    public void testSumNoError() {
        when(request.getParameter("command")).thenReturn("sum");
        productDao.save(new Product("test", 42L));
        productDao.save(new Product("hello", 24L));
        productDao.save(new Product("name", -11L));
        queryServlet.doGet(request, response);
        assertEquals("""
                <html><body>
                Summary price:\s
                55
                </body></html>
                """, writer.toString());
    }

    @Test
    public void testSumEmptyDatabase() {
        when(request.getParameter("command")).thenReturn("sum");
        queryServlet.doGet(request, response);
        assertEquals("""
                <html><body>
                Summary price:\s
                0
                </body></html>
                """, writer.toString());
    }

    @Test
    public void testCountNoError() {
        when(request.getParameter("command")).thenReturn("count");
        productDao.save(new Product("test", 42L));
        productDao.save(new Product("hello", 24L));
        productDao.save(new Product("name", -11L));
        queryServlet.doGet(request, response);
        assertEquals("""
                <html><body>
                Number of products:\s
                3
                </body></html>
                """, writer.toString());
    }

    @Test
    public void testCountEmptyDatabase() {
        when(request.getParameter("command")).thenReturn("count");
        queryServlet.doGet(request, response);
        assertEquals("""
                <html><body>
                Number of products:\s
                0
                </body></html>
                """, writer.toString());
    }

    @Test
    public void testQueryUnknownCommand() {
        when(request.getParameter("command")).thenReturn("unknown");
        queryServlet.doGet(request, response);
        assertEquals("Unknown command: unknown\n", writer.toString());
    }

    @Test
    public void testQueryNoCommand() {
        queryServlet.doGet(request, response);
        assertEquals("Unknown command: null\n", writer.toString());
    }

    @Test
    public void testQueryHasErrorOnGettingParams() {
        when(request.getParameter("command")).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> queryServlet.doGet(request, response));
        assertEquals("", writer.toString());
    }
}
