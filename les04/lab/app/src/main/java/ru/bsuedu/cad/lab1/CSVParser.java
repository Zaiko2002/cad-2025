package ru.bsuedu.cad.lab;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CSVParser implements Parser {
    
    @Override
    public List<Product> parse(InputStream inputStream) {
        List<Product> products = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            
            String line;
            boolean isFirstLine = true;
            int lineNum = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty()) continue;
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length != 6) continue;
                
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String category = parts[2].trim();
                    double price = Double.parseDouble(parts[3].trim());
                    int quantity = Integer.parseInt(parts[4].trim());
                    String animalType = parts[5].trim();
                    
                    products.add(new Product(id, name, category, price, quantity, animalType));
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка в строке " + lineNum);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
}