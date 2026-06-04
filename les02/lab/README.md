# Отчет о лабораторной работе

## Цель работы

Разработать консольное приложение на Spring Framework для чтения товаров зоомагазина из CSV-файла и вывода их в консоль в виде таблицы.

## Выполнение работы

### 1. Установка ПО

Установлены JDK 17.0.14 и Gradle 8.12.

### 2. Создание проекта

Создан Gradle проект с параметрами:
- Пакет: ru.bsuedu.cad.lab
- Название: product-table
- Тип: Application
- Java 17
- DSL: Kotlin

### 3. Добавлены зависимости

В build.gradle.kts добавлены:
- spring-context:6.2.2
- opencsv:5.9

### 4. Создан CSV файл (src/main/resources/products.csv)

| id | name | category | price | quantity | animal_type |
|----|------|----------|-------|----------|-------------|
| 1 | Корм Pedigree | Корм | 2500 | 30 | Собака |
| 2 | Наполнитель Fresh Step | Гигиена | 800 | 50 | Кошка |
| 3 | Игрушка-мяч Kong | Игрушки | 450 | 25 | Собака |
| 4 | Витамины Beaphar | Добавки | 600 | 15 | Универсальное |
| 5 | Когтеточка Jungle Cat | Аксессуары | 1200 | 10 | Кошка |
| 6 | Корм Tetra | Корм | 350 | 40 | Рыбка |
| 7 | Шампунь Espree | Гигиена | 550 | 20 | Универсальное |
| 8 | Лежанка Pet Palace | Мебель | 1800 | 8 | Собака |
| 9 | Колесо Ferplast | Аксессуары | 950 | 12 | Хомяк |
| 10 | Клетка Bella | Жилища | 3200 | 5 | Попугай |

### 5. Реализованы классы

Согласно диаграмме созданы:

- **Product** - модель товара (id, name, category, price, quantity, animalType)
- **Reader** - интерфейс чтения данных
- **ResourceFileReader** - читает CSV из resources
- **Parser** - интерфейс парсинга
- **CSVParser** - парсит CSV в объекты Product
- **ProductProvider** - интерфейс поставщика товаров
- **ConcreteProductProvider** - предоставляет список товаров
- **Renderer** - интерфейс отображения
- **ConsoleTableRenderer** - выводит таблицу в консоль
- **Main** - точка входа и Java-конфигурация Spring

### 6. Конфигурация Spring

@Configuration
public class Main {
    @Bean public Parser parser() { return new CSVParser(); }
    @Bean public Reader reader() { return new ResourceFileReader("products.csv", parser()); }
    @Bean public ProductProvider productProvider() { return new ConcreteProductProvider(reader()); }
    @Bean public Renderer renderer() { return new ConsoleTableRenderer(); }
}


## Вывод

Лабораторная работа выполнена полностью. В результате:
- Разработано консольное приложение на Spring с Java-конфигурацией
- Реализована архитектура согласно диаграмме классов
- Данные о товарах зоомагазина читаются из CSV-файла
- Вывод реализован в виде таблицы со статистикой
- Приложение запускается командой gradlew run
- Получены практические навыки работы со Spring Framework, Gradle и парсингом CSV-файлов.