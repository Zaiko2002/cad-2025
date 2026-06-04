package ru.bsuedu.cad.lab.servlet;

import ru.bsuedu.cad.lab.entity.Customer;
import ru.bsuedu.cad.lab.entity.Order;
import ru.bsuedu.cad.lab.entity.Product;
import ru.bsuedu.cad.lab.repository.CustomerRepository;
import ru.bsuedu.cad.lab.repository.OrderRepository;
import ru.bsuedu.cad.lab.repository.ProductRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/create-order")
public class CreateOrderServlet extends HttpServlet {

    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    @Override
    public void init() throws ServletException {
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        customerRepository = ctx.getBean(CustomerRepository.class);
        productRepository = ctx.getBean(ProductRepository.class);
        orderRepository = ctx.getBean(OrderRepository.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        List<Customer> customers = customerRepository.findAll();
        List<Product> products = productRepository.findAll();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>Создание заказа</title>");
        out.println("<style>");
        out.println("input, select { margin: 5px; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<h1>Создание заказа</h1>");
        out.println("<form method='post'>");

        out.println("<label>Клиент:</label>");
        out.println("<select name='customerId'>");
        for (Customer c : customers) {
            out.println("<option value='" + c.getId() + "'>" + c.getName() + "</option>");
        }
        out.println("</select><br/>");

        out.println("<label>Товар:</label>");
        out.println("<select name='productId'>");
        for (Product p : products) {
            out.println("<option value='" + p.getId() + "'>" + p.getName() + " - " + p.getPrice() + "₽</option>");
        }
        out.println("</select><br/>");

        out.println("<label>Количество:</label>");
        out.println("<input type='number' name='quantity' value='1'/><br/>");

        out.println("<button type='submit'>Создать заказ</button>");
        out.println("</form>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        // Получаем параметры как строки
        String customerIdStr = req.getParameter("customerId");
        String productIdStr = req.getParameter("productId");
        String quantityStr = req.getParameter("quantity");

        // Проверяем, что все параметры присутствуют
        if (customerIdStr == null || productIdStr == null || quantityStr == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не все параметры переданы");
            return;
        }

        try {
            // Преобразуем в числа
            Long customerId = Long.parseLong(customerIdStr);
            Long productId = Long.parseLong(productIdStr);
            int quantity = Integer.parseInt(quantityStr);

            // Загружаем клиента и товар из базы
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Клиент не найден"));
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Товар не найден"));

            // Создаём заказ
            Order order = new Order();
            order.setCustomer(customer);
            order.setOrderDate(LocalDateTime.now());
            order.setStatus("NEW");
            order.setTotalAmount(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

            // Сохраняем заказ
            orderRepository.save(order);

            // Перенаправляем на список заказов
            resp.sendRedirect(req.getContextPath() + "/orders");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректные параметры: " + e.getMessage());
        }
    }
}