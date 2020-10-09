package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.Dao;
import ru.akirakozov.sd.refactoring.db.ProductDao;
import ru.akirakozov.sd.refactoring.html.HtmlWriter;
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
        final var htmlWriter = new HtmlWriter();
        try {
            for (final var product : result) {
                htmlWriter.print(product.getName() + "\t" + product.getPrice()).addBreakLine();
            }
            response.getWriter().println(htmlWriter.toString());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
