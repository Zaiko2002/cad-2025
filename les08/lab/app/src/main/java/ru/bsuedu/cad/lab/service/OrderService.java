package ru.bsuedu.cad.lab.service;

import ru.bsuedu.cad.lab.entity.*;
import ru.bsuedu.cad.lab.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    
    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }
    
    @Transactional
    public Order createOrder(Long customerId, List<Long> productIds, List<Integer> quantities) {
        System.out.println("Создание заказа для клиента ID: " + customerId);
        
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Клиент не найден: " + customerId));
        
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("NEW");
        
        BigDecimal total = BigDecimal.ZERO;
        
        for (int i = 0; i < productIds.size(); i++) {
            final Long productId = productIds.get(i);  // ← создаем final переменную
            final Integer quantity = quantities.get(i);
            
            Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Товар не найден: " + productId));
            
            OrderItem item = new OrderItem(product, quantity, product.getPrice());
            order.addItem(item);
            total = total.add(item.getSubtotal());
        }
        
        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);
        
        System.out.println("Заказ создан! ID: " + savedOrder.getId());
        System.out.println("Сумма заказа: " + savedOrder.getTotalAmount() + " руб.");
        
        return savedOrder;
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}