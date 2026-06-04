package ru.bsuedu.cad.lab;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProductParser {
    
    public List<Product> parse(InputStream inputStream) {
        List<Product> products = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String category = parts[2].trim();
                    double price = Double.parseDouble(parts[3].trim());
                    int quantity = Integer.parseInt(parts[4].trim());
                    String animalType = parts[5].trim();
                    
                    products.add(new Product(id, name, category, price, quantity, animalType));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return products;
    }
}