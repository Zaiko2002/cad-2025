package ru.bsuedu.cad.lab.service;

import ru.bsuedu.cad.lab.entity.*;
import ru.bsuedu.cad.lab.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class DataInitializerService {
    
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    
    public DataInitializerService(CategoryRepository categoryRepository,
                                   ProductRepository productRepository,
                                   CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }
    
    @Transactional
    public void initializeData() {
        System.out.println("Инициализация данных...");
        
        // Категории
        Category food = new Category("Корм", "Сухие и влажные корма");
        Category hygiene = new Category("Гигиена", "Товары для гигиены");
        Category toys = new Category("Игрушки", "Игрушки для животных");
        
        categoryRepository.save(food);
        categoryRepository.save(hygiene);
        categoryRepository.save(toys);
        
        // Продукты
        Product p1 = new Product("Корм Pedigree", food, new BigDecimal("2500"), 30, "Собака");
        Product p2 = new Product("Наполнитель Fresh Step", hygiene, new BigDecimal("800"), 50, "Кошка");
        Product p3 = new Product("Игрушка-мяч Kong", toys, new BigDecimal("450"), 25, "Собака");
        
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        
        // Клиенты
        Customer c1 = new Customer("Иван Петров", "ivan@mail.ru", "+7-999-123-45-67");
        Customer c2 = new Customer("Мария Иванова", "maria@mail.ru", "+7-999-765-43-21");
        
        customerRepository.save(c1);
        customerRepository.save(c2);
        
        System.out.println("Создано категорий: " + categoryRepository.count());
        System.out.println("Создано продуктов: " + productRepository.count());
        System.out.println("Создано клиентов: " + customerRepository.count());
    }
}