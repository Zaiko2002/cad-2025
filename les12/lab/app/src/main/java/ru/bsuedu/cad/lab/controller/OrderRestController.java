package ru.bsuedu.cad.lab.controller;

import ru.bsuedu.cad.lab.entity.Order;
import ru.bsuedu.cad.lab.entity.Customer;
import ru.bsuedu.cad.lab.entity.Product;
import ru.bsuedu.cad.lab.repository.OrderRepository;
import ru.bsuedu.cad.lab.repository.CustomerRepository;
import ru.bsuedu.cad.lab.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private ProductRepository productRepository;

    // GET /api/orders - получить все заказы
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // GET /api/orders/{id} - получить заказ по ID
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден: " + id));
    }

    // POST /api/orders - создать заказ
    @PostMapping
    public Order createOrder(@RequestBody Map<String, Object> request) {
        Long customerId = Long.valueOf(request.get("customerId").toString());
        Long productId = Long.valueOf(request.get("productId").toString());
        int quantity = Integer.parseInt(request.get("quantity").toString());

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Товар не найден"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("NEW");
        order.setTotalAmount(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

        return orderRepository.save(order);
    }

    // PUT /api/orders/{id} - обновить заказ
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден: " + id));
        
        if (request.containsKey("status")) {
            order.setStatus(request.get("status").toString());
        }
        if (request.containsKey("totalAmount")) {
            order.setTotalAmount(new BigDecimal(request.get("totalAmount").toString()));
        }
        
        return orderRepository.save(order);
    }

    // DELETE /api/orders/{id} - удалить заказ
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
    }
}