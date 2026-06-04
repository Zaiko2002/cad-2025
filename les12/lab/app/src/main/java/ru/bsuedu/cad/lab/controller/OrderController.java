package ru.bsuedu.cad.lab.controller;

import ru.bsuedu.cad.lab.entity.Order;
import ru.bsuedu.cad.lab.entity.Customer;
import ru.bsuedu.cad.lab.entity.Product;
import ru.bsuedu.cad.lab.repository.OrderRepository;
import ru.bsuedu.cad.lab.repository.CustomerRepository;
import ru.bsuedu.cad.lab.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/web/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<Customer> customers = customerRepository.findAll();
        List<Product> products = productRepository.findAll();
        model.addAttribute("customers", customers);
        model.addAttribute("products", products);
        return "orders/create";
    }

    @PostMapping("/create")
    public String createOrder(@RequestParam Long customerId,
                               @RequestParam Long productId,
                               @RequestParam Integer quantity) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Товар не найден"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("NEW");
        order.setTotalAmount(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

        orderRepository.save(order);
        return "redirect:/web/orders";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
        model.addAttribute("order", order);
        return "orders/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateOrder(@PathVariable Long id,
                               @RequestParam String status,
                               @RequestParam BigDecimal totalAmount) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
        order.setStatus(status);
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        return "redirect:/web/orders";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return "redirect:/web/orders";
    }
}