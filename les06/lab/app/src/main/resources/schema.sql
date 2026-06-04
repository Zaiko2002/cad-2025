-- Удаляем таблицы если существуют
DROP TABLE IF EXISTS PRODUCTS;
DROP TABLE IF EXISTS CATEGORIES;

-- Таблица категорий
CREATE TABLE CATEGORIES (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

-- Таблица продуктов
CREATE TABLE PRODUCTS (
    id INT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    category_id INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    animal_type VARCHAR(50),
    FOREIGN KEY (category_id) REFERENCES CATEGORIES(id)
);
