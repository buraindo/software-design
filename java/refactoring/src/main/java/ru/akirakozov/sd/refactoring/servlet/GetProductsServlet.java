package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.Dao;
import ru.akirakozov.sd.refactoring.db.ProductDao;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    private final Dao<Product> productDao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        final var result = productDao.findAll();
        try {
            final var writer = response.getWriter();
            writer.println("<html><body>");
            for (final var product : result) {
                writer.println(product.getName() + "\t" + product.getPrice() + "</br>");
            }
            writer.println("</body></html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
