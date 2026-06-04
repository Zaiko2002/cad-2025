package ru.bsuedu.cad.lab.servlet;

import ru.bsuedu.cad.lab.entity.Product;
import ru.bsuedu.cad.lab.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/api/products")
public class ProductRestController extends HttpServlet {

    private ProductRepository productRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        productRepository = ctx.getBean(ProductRepository.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        if (productRepository == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ProductRepository не инициализирован");
            return;
        }

        
        List<Product> products = productRepository.findAll();
        
        // Вывод в лог для отладки
        System.out.println("=== REST API /api/products ===");
        for (Product p : products) {
            System.out.println("Product: " + p.getName() + 
                ", category: " + (p.getCategory() != null ? p.getCategory().getName() : "null") +
                ", quantity: " + p.getQuantity());
        }

        List<Map<String, Object>> result = products.stream()
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("productName", p.getName() != null ? p.getName() : "");
                    map.put("categoryName", p.getCategory() != null ? p.getCategory().getName() : "");
                    map.put("quantity", p.getQuantity() != null ? p.getQuantity() : 0);
                    return map;
                })
                .collect(Collectors.toList());

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        mapper.writeValue(resp.getWriter(), result);
    }
}