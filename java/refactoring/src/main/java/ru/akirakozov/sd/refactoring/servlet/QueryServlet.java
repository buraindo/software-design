package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.Dao;
import ru.akirakozov.sd.refactoring.db.ProductDao;
import ru.akirakozov.sd.refactoring.db.query.MaxQuery;
import ru.akirakozov.sd.refactoring.db.query.MinQuery;
import ru.akirakozov.sd.refactoring.db.query.SumQuery;
import ru.akirakozov.sd.refactoring.html.HtmlWriter;
import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.utils.ThrowingConsumer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final Dao<Product> productDao = new ProductDao();

    private final Map<String, Consumer<HtmlWriter>> commands = new HashMap<>() {{
        put("max", QueryServlet.this::max);
        put("min", QueryServlet.this::min);
        put("sum", QueryServlet.this::sum);
        put("count", QueryServlet.this::count);
    }};

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        final var command = request.getParameter("command");
        final var htmlWriter = new HtmlWriter();

        try {
            if (commands.containsKey(command)) {
                commands.get(command).accept(htmlWriter);
                response.getWriter().println(htmlWriter.toString());
            } else {
                response.getWriter().println("Unknown command: " + command);
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void max(final HtmlWriter htmlWriter) {
        htmlWriter.addH1Header("Product with max price: ");
        productDao.findByQuery(new MaxQuery()).ifPresent(ThrowingConsumer.unchecked(product -> htmlWriter.print(product.getName() + "\t" + product.getPrice()).addBreakLine()));
    }

    private void min(final HtmlWriter htmlWriter) {
        htmlWriter.addH1Header("Product with min price: ");
        productDao.findByQuery(new MinQuery()).ifPresent(ThrowingConsumer.unchecked(product -> htmlWriter.print(product.getName() + "\t" + product.getPrice()).addBreakLine()));
    }

    private void sum(final HtmlWriter htmlWriter) {
        htmlWriter.println("Summary price: ");
        productDao.findByQuery(new SumQuery()).ifPresent(ThrowingConsumer.unchecked(sum -> htmlWriter.println(sum.toString())));
    }

    private void count(final HtmlWriter htmlWriter) {
        htmlWriter.println("Number of products: ");
        htmlWriter.println(Integer.toString(productDao.count()));
    }
}
