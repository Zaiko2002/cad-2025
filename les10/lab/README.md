# Отчет о лабораторной работе 5

## Цель работы

Добавить веб-интерфейс к приложению зоомагазина: реализовать сервлеты для просмотра и создания заказов, а также REST-сервис для получения информации о продуктах. Настроить сборку WAR-файла и развернуть приложение на сервере Apache Tomcat 11

## Выполнение работы

### 1. Настройка сборки WAR

В build.gradle.kts добавлен плагин war и настроены зависимости, включая spring-webmvc и jakarta.servlet-api.

WAR-файл создаётся в app/build/libs/zoomagazin.war.

### 2.  Реализация сервлетов

Используется встраиваемая база данных H2 с пулом соединений HikariCP. Схема создается автоматически Hibernate на основе JPA сущностей.

1. OrderServlet – список заказов
URL: /orders

Метод doGet загружает все заказы из БД через OrderRepository и формирует HTML-таблицу.

На странице есть кнопка «Создать новый заказ», ведущая на /create-order.

2. CreateOrderServlet – форма создания заказа
URL: /create-order

Метод doGet отображает HTML-форму с выпадающими списками клиентов и товаров, а также полем для количества.

Метод doPost получает параметры, создаёт новый заказ (с вычислением суммы) и сохраняет его в БД, после чего перенаправляет на /orders.

3. ProductRestController – REST API для продуктов
URL: /api/products

Метод doGet возвращает список продуктов в формате JSON.

Для каждого продукта выводятся:

productName – название продукта

categoryName – название категории

quantity – количество на складе

### 3. Тестирование

Веб-страницы открыты в браузере:

http://localhost:8080/zoomagazin/orders – список заказов

http://localhost:8080/zoomagazin/create-order – форма создания заказа

REST API протестирован через Postman: GET-запрос к /api/products возвращает корректный JSON.


### 4. Результат работы

Список заказов

https://github.com/Zaiko2002/cad-2025/blob/main/les10/lab/screenshots/orders.PNG

Форма создания заказа

https://github.com/Zaiko2002/cad-2025/blob/main/les10/lab/screenshots/create%20orders.PNG

REST API (Postman)

https://github.com/Zaiko2002/cad-2025/blob/main/les10/lab/screenshots/postman.PNG

 
## Вывод

✅ Приложение успешно переведено в веб-режим: собран WAR-файл и развёрнут на Tomcat 11.

✅ Реализованы два сервлета для работы с заказами (просмотр + создание).

✅ Реализован REST-сервис, выдающий информацию о продуктах в формате JSON.

✅ Взаимодействие с базой данных (Spring Data JPA) сохранено и работает внутри веб-контейнера.

✅ Выполнено тестирование через браузер и Postman.


## UML диаграмма классов

```mermaid
classDiagram
    class OrderServlet {
        -OrderRepository orderRepository
        +init()
        +doGet()
    }

    class CreateOrderServlet {
        -CustomerRepository customerRepository
        -ProductRepository productRepository
        -OrderRepository orderRepository
        +init()
        +doGet()
        +doPost()
    }

    class ProductRestController {
        -ProductRepository productRepository
        -ObjectMapper mapper
        +init()
        +doGet()
    }

    class OrderRepository {
        <<interface>>
        +findAll()
        +save()
    }

    class CustomerRepository {
        <<interface>>
        +findAll()
        +findById()
    }

    class ProductRepository {
        <<interface>>
        +findAll()
        +findById()
    }

    class Order {
        +Long id
        +Customer customer
        +BigDecimal totalAmount
        +String status
        +LocalDateTime orderDate
    }

    class Customer {
        +Long id
        +String name
        +String email
    }

    class Product {
        +Long id
        +String name
        +Category category
        +Integer quantity
        +BigDecimal price
    }

    class Category {
        +Long id
        +String name
    }

    OrderServlet --> OrderRepository
    CreateOrderServlet --> CustomerRepository
    CreateOrderServlet --> ProductRepository
    CreateOrderServlet --> OrderRepository
    ProductRestController --> ProductRepository
    OrderRepository --> Order
    CustomerRepository --> Customer
    ProductRepository --> Product
    Product --> Category
	```
