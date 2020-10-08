package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.EntityManager;
import ru.akirakozov.sd.refactoring.utils.ThrowingConsumer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    private final EntityManager entityManager;

    private static final String DB_ADDRESS = "jdbc:sqlite:test.db";

    public GetProductsServlet() {
        this.entityManager = new EntityManager(DB_ADDRESS);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            entityManager.execute("SELECT * FROM PRODUCT", ThrowingConsumer.unchecked(resultSet -> {
                final var writer = response.getWriter();
                writer.println("<html><body>");

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    long price = resultSet.getLong("price");
                    writer.println(name + "\t" + price + "</br>");
                }
                writer.println("</body></html>");

                resultSet.close();
            }));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
