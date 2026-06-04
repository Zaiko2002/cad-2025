package ru.bsuedu.cad.lab;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
public class CategoryRequest {
    
    private static final Logger logger = LoggerFactory.getLogger(CategoryRequest.class);
    private final JdbcTemplate jdbcTemplate;
    
    public CategoryRequest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @PostConstruct
    public void executeRequest() {
        String sql = """
            SELECT c.id, c.name, COUNT(p.id) as product_count
            FROM CATEGORIES c
            JOIN PRODUCTS p ON c.id = p.category_id
            GROUP BY c.id, c.name
            HAVING COUNT(p.id) > 1
            ORDER BY product_count DESC
        """;
        
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
        
        logger.info("=== Категории с количеством товаров больше 1 ===");
        for (Map<String, Object> row : results) {
            logger.info("Категория: {}, Количество товаров: {}", 
                row.get("name"), row.get("product_count"));
        }
        logger.info("Всего найдено: {} категорий", results.size());
    }
}