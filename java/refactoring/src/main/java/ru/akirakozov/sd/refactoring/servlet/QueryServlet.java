package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.Dao;
import ru.akirakozov.sd.refactoring.db.ProductDao;
import ru.akirakozov.sd.refactoring.db.query.MaxQuery;
import ru.akirakozov.sd.refactoring.db.query.MinQuery;
import ru.akirakozov.sd.refactoring.db.query.SumQuery;
import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.utils.ThrowingConsumer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final Dao<Product> productDao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                final var result = productDao.findByQuery(new MaxQuery());
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with max price: </h1>");
                result.ifPresent(ThrowingConsumer.unchecked(product -> response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>")));
                response.getWriter().println("</body></html>");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                final var result = productDao.findByQuery(new MinQuery());
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with min price: </h1>");
                result.ifPresent(ThrowingConsumer.unchecked(product -> response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>")));
                response.getWriter().println("</body></html>");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                final var result = productDao.findByQuery(new SumQuery());
                response.getWriter().println("<html><body>");
                response.getWriter().println("Summary price: ");
                result.ifPresent(ThrowingConsumer.unchecked(sum -> response.getWriter().println(sum)));
                response.getWriter().println("</body></html>");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                final var result = productDao.count();
                response.getWriter().println("<html><body>");
                response.getWriter().println("Number of products: ");
                response.getWriter().println(result);
                response.getWriter().println("</body></html>");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
