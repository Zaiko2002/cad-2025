package ru.bsuedu.cad.lab.app;

import ru.bsuedu.cad.lab.config.AppConfig;
import ru.bsuedu.cad.lab.service.DataInitializerService;
import ru.bsuedu.cad.lab.service.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== ЗАПУСК ЗООМАГАЗИНА ===\n");
        
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        // 1. Инициализация данных
        DataInitializerService initializer = context.getBean(DataInitializerService.class);
        initializer.initializeData();
        
        System.out.println("\n--- СОЗДАНИЕ ЗАКАЗА ---");
        
        // 2. Создание заказа
        OrderService orderService = context.getBean(OrderService.class);
        
        List<Long> productIds = List.of(1L, 2L);      // ID товаров
        List<Integer> quantities = List.of(2, 1);      // Количество
        
        var order = orderService.createOrder(1L, productIds, quantities);
        
        // 3. Проверка что заказ сохранился
        System.out.println("\n--- ПРОВЕРКА ---");
        var orders = orderService.getAllOrders();
        System.out.println("Всего заказов в базе: " + orders.size());
        
        ((AnnotationConfigApplicationContext) context).close();
        
        System.out.println("\n=== ПРИЛОЖЕНИЕ ЗАВЕРШИЛО РАБОТУ ===");
    }
}