package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.EntityManager;
import ru.akirakozov.sd.refactoring.utils.ThrowingConsumer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final EntityManager entityManager;

    private static final String DB_ADDRESS = "jdbc:sqlite:test.db";

    public QueryServlet() {
        this.entityManager = new EntityManager(DB_ADDRESS);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                entityManager.execute("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", ThrowingConsumer.unchecked(resultSet -> {
                    response.getWriter().println("<html><body>");
                    response.getWriter().println("<h1>Product with max price: </h1>");

                    while (resultSet.next()) {
                        String name = resultSet.getString("name");
                        int price = resultSet.getInt("price");
                        response.getWriter().println(name + "\t" + price + "</br>");
                    }
                    response.getWriter().println("</body></html>");

                    resultSet.close();
                }));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                entityManager.execute("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", ThrowingConsumer.unchecked(resultSet -> {
                    response.getWriter().println("<html><body>");
                    response.getWriter().println("<h1>Product with min price: </h1>");

                    while (resultSet.next()) {
                        String name = resultSet.getString("name");
                        int price = resultSet.getInt("price");
                        response.getWriter().println(name + "\t" + price + "</br>");
                    }
                    response.getWriter().println("</body></html>");

                    resultSet.close();
                }));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                entityManager.execute("SELECT SUM(price) FROM PRODUCT", ThrowingConsumer.unchecked(resultSet -> {
                    response.getWriter().println("<html><body>");
                    response.getWriter().println("Summary price: ");

                    if (resultSet.next()) {
                        response.getWriter().println(resultSet.getInt(1));
                    }
                    response.getWriter().println("</body></html>");

                    resultSet.close();
                }));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                entityManager.execute("SELECT COUNT(*) FROM PRODUCT", ThrowingConsumer.unchecked(resultSet -> {
                    response.getWriter().println("<html><body>");
                    response.getWriter().println("Number of products: ");

                    if (resultSet.next()) {
                        response.getWriter().println(resultSet.getInt(1));
                    }
                    response.getWriter().println("</body></html>");

                    resultSet.close();
                }));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
