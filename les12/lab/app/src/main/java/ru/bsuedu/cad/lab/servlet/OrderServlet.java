package ru.bsuedu.cad.lab.servlet;

import ru.bsuedu.cad.lab.entity.Order;
import ru.bsuedu.cad.lab.repository.OrderRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    private OrderRepository orderRepository;

    @Override
    public void init() throws ServletException {
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        orderRepository = ctx.getBean(OrderRepository.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        List<Order> orders = orderRepository.findAll();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>Список заказов</title>");
        out.println("<style>");
        out.println("table { border-collapse: collapse; width: 100%; }");
        out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
        out.println("th { background-color: #4CAF50; color: white; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<h1>Список заказов</h1>");
        out.println("<a href='create-order'>Создать новый заказ</a>");
        out.println("<table>");
        out.println("<tr><th>ID</th><th>Клиент</th><th>Сумма</th><th>Статус</th></tr>");

        for (Order order : orders) {
            out.println("<tr>");
            out.println("<td>" + order.getId() + "</td>");
            out.println("<td>" + order.getCustomer().getName() + "</td>");
            out.println("<td>" + order.getTotalAmount() + "</td>");
            out.println("<td>" + order.getStatus() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }
}