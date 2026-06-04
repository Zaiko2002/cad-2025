package ru.bsuedu.cad.lab;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DatabaseRenderer implements Renderer {
    
    private final JdbcTemplate jdbcTemplate;
    private final CategoryParser categoryParser;
    private final ProductParser productParser;
    
    public DatabaseRenderer(JdbcTemplate jdbcTemplate, 
                            CategoryParser categoryParser,
                            ProductParser productParser) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryParser = categoryParser;
        this.productParser = productParser;
    }
    
    @Override
    public void render(List<Product> products) {
        System.out.println("💾 Сохранение данных в базу H2...");
        
        // 1. Сохраняем категории
        var categories = loadCategories();
        saveCategories(categories);
        
        // 2. Сохраняем продукты
        saveProducts(products);
        
        // 3. Проверка что сохранилось
        checkSavedData();
        
        System.out.println("✅ Данные успешно сохранены в базу данных H2!");
        System.out.println("   📁 База данных: zoomagazin_db");
        System.out.println("   📊 Категорий: " + categories.size());
        System.out.println("   📦 Товаров: " + products.size());
    }
    
    private List<Category> loadCategories() {
        var inputStream = getClass().getClassLoader().getResourceAsStream("category.csv");
        if (inputStream == null) {
            throw new RuntimeException("category.csv не найден!");
        }
        return categoryParser.parse(inputStream);
    }
    
    private void saveCategories(List<Category> categories) {
        String sql = "INSERT INTO CATEGORIES (id, name, description) VALUES (?, ?, ?)";
        for (Category category : categories) {
            jdbcTemplate.update(sql, category.getId(), category.getName(), category.getDescription());
        }
        System.out.println("   ✅ Сохранено категорий: " + categories.size());
    }
    
    private void saveProducts(List<Product> products) {
        String sql = "INSERT INTO PRODUCTS (id, name, category_id, price, quantity, animal_type) VALUES (?, ?, ?, ?, ?, ?)";
        for (Product product : products) {
            int categoryId = getCategoryIdFromName(product.getCategory());
            jdbcTemplate.update(sql, 
                product.getId(), 
                product.getName(), 
                categoryId,
                product.getPrice(), 
                product.getQuantity(), 
                product.getAnimalType());
        }
        System.out.println("   ✅ Сохранено товаров: " + products.size());
    }
    
    private int getCategoryIdFromName(String categoryName) {
        return switch (categoryName) {
            case "Корм" -> 1;
            case "Гигиена" -> 2;
            case "Игрушки" -> 3;
            case "Добавки" -> 4;
            case "Аксессуары" -> 5;
            case "Мебель" -> 6;
            case "Жилища" -> 7;
            default -> 1;
        };
    }
    
    private void checkSavedData() {
        Integer categoryCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM CATEGORIES", Integer.class);
        Integer productCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PRODUCTS", Integer.class);
        
        System.out.println("   🔍 Проверка БД: " + categoryCount + " категорий, " + productCount + " товаров");
    }
}