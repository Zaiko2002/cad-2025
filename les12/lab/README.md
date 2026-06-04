# Отчет о лабораторной работе 6

## Цель работы

Перейти от низкоуровневых сервлетов к Spring MVC. Реализовать REST API для управления заказами (CRUD) и веб-интерфейс с использованием шаблонизатора Thymeleaf. Развернуть приложение на Apache Tomcat 11 и протестировать через Postman и браузер.

## Выполнение работы

### 1. Настройка Spring MVC и Thymeleaf

Добавлены зависимости в `build.gradle.kts`:

- `spring-webmvc`
- `thymeleaf-spring6`

Создана конфигурация `WebConfig` с бинами `SpringResourceTemplateResolver`, `SpringTemplateEngine`, `ThymeleafViewResolver`.  
Класс `WebAppInitializer` регистрирует `DispatcherServlet`.

### 2.  Реализация REST API для заказов

Создан `@RestController` по адресу `/api/orders`, поддерживающий все операции CRUD:

| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/api/orders` | Получить список всех заказов |
| GET | `/api/orders/{id}` | Получить заказ по ID |
| POST | `/api/orders` | Создать новый заказ (JSON с customerId, productId, quantity) |
| PUT | `/api/orders/{id}` | Обновить статус/сумму заказа |
| DELETE | `/api/orders/{id}` | Удалить заказ |

### 3. Реализация веб-интерфейса (Thymeleaf)

Создан `@Controller` с базовым путём `/web/orders`:

| URL | Метод | Описание |
|-----|-------|----------|
| `/web/orders` | GET | Страница со списком заказов (таблица, кнопки действий) |
| `/web/orders/create` | GET | Форма для создания заказа (выбор клиента, товара, количества) |
| `/web/orders/create` | POST | Обработка создания заказа, редирект на список |
| `/web/orders/edit/{id}` | GET | Форма редактирования заказа (статус, сумма) |
| `/web/orders/edit/{id}` | POST | Сохранение изменений, редирект на список |
| `/web/orders/delete/{id}` | POST | Удаление заказа, редирект на список |

Шаблоны `list.html`, `create.html`, `edit.html` расположены в `/WEB-INF/views/orders/`. В ссылках используется синтаксис `th:href` для автоматического добавления контекста приложения.

### 4. Решение проблем с сериализацией JSON

- Добавлена зависимость `jackson-datatype-jsr310` и настроен `ObjectMapper` для поддержки `LocalDateTime`.
- К ленивым коллекциям (`Customer.orders`, `Order.items`, `Product.orderItems`) добавлена аннотация `@JsonIgnore`, чтобы избежать `LazyInitializationException`.

### 5. Результат работы
 
 REST API (Postman)
(https://github.com/Zaiko2002/cad-2025/blob/main/les12/lab/screenshots/postman_%D1%81%D0%BE%D0%B7%D0%B4%D0%B0%D0%BD%D0%B8%D0%B5.PNG)
 
 Веб-интерфейс
Создание заказа https://github.com/Zaiko2002/cad-2025/blob/main/les12/screenshots/web_%D1%81%D0%BE%D0%B7%D0%B4%D0%B0%D0%BD%D0%B8%D0%B5.PNG
Список заказов https://github.com/Zaiko2002/cad-2025/blob/main/les12/screenshots/web_%D1%81%D0%BF%D0%B8%D1%81%D0%BE%D0%BA.PNG
Редактирование заказа https://github.com/Zaiko2002/cad-2025/blob/main/les12/screenshots/web_%D1%80%D0%B5%D0%B4%D0%B0%D0%BA%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5.PNG
Результат редактирования https://github.com/Zaiko2002/cad-2025/blob/main/les12/screenshots/web_%D1%80%D0%B5%D0%B7%D1%83%D0%BB%D1%8C%D1%82%D0%B0%D1%82%20%D1%80%D0%B5%D0%B4.PNG
Удаление заказа https://github.com/Zaiko2002/cad-2025/blob/main/les12/screenshots/screenshots/%D0%A3%D0%B4%D0%B0%D0%BB%D0%B5%D0%BD%D0%B8%D1%8F%20%D0%B7%D0%B0%D0%BA%D0%B0%D0%B7%D0%B0.PNG
 Заказ удален https://github.com/Zaiko2002/cad-2025/blob/main/les12/screenshots/screenshots/%D0%97%D0%B0%D0%BA%D0%B0%D0%B7%20%D1%83%D0%B4%D0%B0%D0%BB%D0%B5%D0%BD.PNG
 
## Вывод

В ходе лабораторной работы:

✅ Проект переведён на Spring MVC, настроена сборка WAR.

✅ Реализован полноценный REST API для заказов (CRUD) – протестирован через Postman.

✅ Подключён Thymeleaf и создан веб-интерфейс, позволяющий просматривать, создавать, редактировать и удалять заказы.

✅ Решены проблемы с ленивой инициализацией Hibernate и сериализацией LocalDateTime.

✅ Приложение развёрнуто на Apache Tomcat 11 и работает стабильно.


## UML диаграмма классов

```mermaid
classDiagram
    class OrderRestController {
        -OrderRepository orderRepository
        -CustomerRepository customerRepository
        -ProductRepository productRepository
        +getAllOrders() List~Order~
        +getOrderById(id) Order
        +createOrder(request) Order
        +updateOrder(id, request) Order
        +deleteOrder(id)
    }

    class OrderController {
        -OrderRepository orderRepository
        -CustomerRepository customerRepository
        -ProductRepository productRepository
        +listOrders(model)
        +showCreateForm(model)
        +createOrder(customerId, productId, quantity)
        +showEditForm(id, model)
        +updateOrder(id, status, totalAmount)
        +deleteOrder(id)
    }

    class WebConfig {
        +templateResolver()
        +templateEngine()
        +viewResolver()
        +objectMapper()
    }

    class Order {
        +Long id
        +Customer customer
        +LocalDateTime orderDate
        +BigDecimal totalAmount
        +String status
        +List~OrderItem~ items
    }

    class Customer {
        +Long id
        +String name
        +String email
        +String phone
        +List~Order~ orders
    }

    class Product {
        +Long id
        +String name
        +Category category
        +BigDecimal price
        +Integer quantity
        +String animalType
    }

    class OrderRepository {
        <<interface>>
        +findAll()
        +findById(id)
        +save(order)
        +deleteById(id)
    }

    OrderRestController --> OrderRepository
    OrderController --> OrderRepository
    OrderController --> CustomerRepository
    OrderController --> ProductRepository
    Order --> Customer
    Order --> OrderItem
    Product --> Category
	```
