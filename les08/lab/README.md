# Отчет о лабораторной работе 4

## Цель работы

Перейти с использования Spring JDBC на ORM Hibernate и Spring Data. Расширить приложение новыми сущностями и привести структуру приложения в соответствие со слоистой архитектурой.

## Выполнение работы

### 1. Зависимости

В `build.gradle.kts` добавлены:

kotlin
dependencies {
    implementation("org.springframework:spring-context:6.1.14")
    implementation("org.springframework:spring-orm:6.1.14")
    implementation("org.springframework.data:spring-data-jpa:3.2.5")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.h2database:h2:2.2.224")
    implementation("org.slf4j:slf4j-simple:2.0.13")
}
### 2.  База данных

Используется встраиваемая база данных H2 с пулом соединений HikariCP. Схема создается автоматически Hibernate на основе JPA сущностей.

java
@Bean
public DataSource dataSource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:h2:mem:zoomagazin_db");
    config.setUsername("sa");
    config.setPassword("");
    return new HikariDataSource(config);
}

### 3. JPA Сущности

Сущность	Таблица	    Описание

Category	categories	Категории товаров
Product	    products	Товары
Customer	customers	Клиенты
Order	    orders	    Заказы
OrderItem	order_items	Элементы заказа


### 4. Репозитории

Используются интерфейсы, расширяющие JpaRepository:

java
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {}
public interface ProductRepository extends JpaRepository<Product, Long> {}
public interface CustomerRepository extends JpaRepository<Customer, Long> {}
public interface OrderRepository extends JpaRepository<Order, Long> {}

### 5.Сервисы

DataInitializerService - инициализация данных

java
@Service
public class DataInitializerService {
    @Transactional
    public void initializeData() {
        // Создание категорий, продуктов и клиентов
    }
}

OrderService - создание заказов

java
@Service
public class OrderService {
    @Transactional
    public Order createOrder(Long customerId, List<Long> productIds, List<Integer> quantities) {
        // Создание заказа в рамках транзакции
    }
}

### 6. Конфигурация Spring

java
@Configuration
@ComponentScan(basePackages = "ru.bsuedu.cad.lab")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.bsuedu.cad.lab.repository")
public class AppConfig {
    // DataSource, EntityManagerFactory, TransactionManager
}

### 7. UML диаграмма классов

```mermaid
classDiagram
    class Category {
        -Long id
        -String name
        -String description
        -List~Product~ products
        +Category()
        +Category(name, description)
    }
    
    class Product {
        -Long id
        -String name
        -Category category
        -BigDecimal price
        -Integer quantity
        -String animalType
        +Product()
        +Product(name, category, price, quantity, animalType)
    }
    
    class Customer {
        -Long id
        -String name
        -String email
        -String phone
        -List~Order~ orders
        +Customer()
        +Customer(name, email, phone)
    }
    
    class Order {
        -Long id
        -Customer customer
        -LocalDateTime orderDate
        -BigDecimal totalAmount
        -String status
        -List~OrderItem~ items
        +Order()
        +addItem(item)
    }
    
    class OrderItem {
        -Long id
        -Order order
        -Product product
        -Integer quantity
        -BigDecimal price
        +OrderItem()
        +getSubtotal() BigDecimal
    }
    
    class CategoryRepository {
        <<interface>>
        +findById()
        +save()
        +findAll()
        +delete()
    }
    
    class ProductRepository {
        <<interface>>
        +findById()
        +save()
        +findAll()
    }
    
    class CustomerRepository {
        <<interface>>
        +findById()
        +save()
        +findAll()
    }
    
    class OrderRepository {
        <<interface>>
        +findById()
        +save()
        +findAll()
    }
    
    class DataInitializerService {
        -CategoryRepository categoryRepository
        -ProductRepository productRepository
        -CustomerRepository customerRepository
        +initializeData()
    }
    
    class OrderService {
        -OrderRepository orderRepository
        -ProductRepository productRepository
        -CustomerRepository customerRepository
        +createOrder() Order
        +getAllOrders() List~Order~
    }
    
    class AppConfig {
        +dataSource() DataSource
        +entityManagerFactory() LocalContainerEntityManagerFactoryBean
        +transactionManager() PlatformTransactionManager
    }
    
    class Main {
        +main(String[])
    }
    
    Category "1" --> "*" Product
    Product "*" --> "1" Category
    Customer "1" --> "*" Order
    Order "*" --> "1" Customer
    Order "1" --> "*" OrderItem
    OrderItem "*" --> "1" Order
    Product "1" --> "*" OrderItem
    OrderItem "*" --> "1" Product
    
    DataInitializerService --> CategoryRepository
    DataInitializerService --> ProductRepository
    DataInitializerService --> CustomerRepository
    OrderService --> OrderRepository
    OrderService --> ProductRepository
    OrderService --> CustomerRepository
    Main --> DataInitializerService
    Main --> OrderService
    Main --> AppConfig
	```
### 8. Результат работы

Вывод в консоль:

=== ЗАПУСК ЗООМАГАЗИНА ===

Инициализация данных...
Создано категорий: 3
Создано продуктов: 3
Создано клиентов: 2

--- СОЗДАНИЕ ЗАКАЗА ---
Создание заказа для клиента ID: 1
Заказ создан! ID: 1
Сумма заказа: 5800.00 руб.

--- ПРОВЕРКА ---
Всего заказов в базе: 1

=== ПРИЛОЖЕНИЕ ЗАВЕРШИЛО РАБОТУ ===

### 9. Доказательство работы транзакции

Создание заказа выполняется в рамках транзакции (аннотация @Transactional):

Если все операции успешны → заказ сохраняется в БД

Если ошибка → все изменения откатываются

В выводе видно, что заказ успешно создан и сохранен в базе данных (проверка через getAllOrders() показывает 1 заказ).	


 
## Вывод

В ходе лабораторной работы:

✅ Выполнен переход с Spring JDBC на Hibernate и Spring Data JPA

✅ Создана схема базы данных на основе JPA сущностей (автоматическое создание таблиц)

✅ Реализована слоистая архитектура: entity → repository → service → app

✅ Созданы репозитории для работы с сущностями

✅ Реализован сервис для создания заказов с использованием транзакций

✅ Данные инициализируются при старте приложения

✅ Приложение успешно запускается и демонстрирует работу с БД

Получены практические навыки работы с Hibernate, Spring Data JPA, транзакциями и слоистой архитектурой Spring-приложений.