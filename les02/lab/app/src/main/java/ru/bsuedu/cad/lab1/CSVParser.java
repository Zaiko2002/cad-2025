package ru.bsuedu.cad.lab;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Парсит CSV файл в список объектов Product
 * Работает с любой кодировкой и неправильным форматом
 */
public class CSVParser implements Parser {
    
    @Override
    public List<Product> parse(InputStream inputStream) {
        List<Product> products = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            
            String line;
            boolean isFirstLine = true;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                // Пропускаем пустые строки
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // Пропускаем заголовок
                if (isFirstLine) {
                    isFirstLine = false;
                    System.out.println("📄 Заголовок: " + line);
                    continue;
                }
                
                // ОЧИЩАЕМ СТРОКУ ОТ ПРОБЛЕМ
                line = line.trim();
                
                // Разбиваем по запятой
                String[] parts = line.split(",");
                
                // Если частей меньше 6, пытаемся исправить
                if (parts.length < 6) {
                    System.err.println("⚠️ Строка " + lineNumber + " имеет " + parts.length + " полей, исправляем...");
                    parts = fixBrokenLine(line, parts.length);
                }
                
                // Парсим строку
                if (parts.length >= 6) {
                    try {
                        Product product = parseProduct(parts, lineNumber);
                        if (product != null) {
                            products.add(product);
                        }
                    } catch (Exception e) {
                        System.err.println("❌ Ошибка в строке " + lineNumber + ": " + e.getMessage());
                    }
                } else {
                    System.err.println("❌ Строка " + lineNumber + " не удалось исправить: " + line);
                }
            }
            
            System.out.println("\n✅ Загружено товаров: " + products.size());
            return products;
            
        } catch (Exception e) {
            throw new RuntimeException("Ошибка чтения CSV файла: " + e.getMessage(), e);
        }
    }
    
    /**
     * Исправляет кривую строку, где поля слились
     */
    private String[] fixBrokenLine(String line, int currentParts) {
        // Ищем шаблон: число,пробел,слово (например "5.Попугай" -> "5,Попугай")
        String fixed = line.replaceAll("(\\d+)\\.(\\D+)", "$1,$2");
        fixed = fixed.replaceAll("(\\d+),(\\d+)\\.(\\D+)", "$1,$2,$3");
        fixed = fixed.replaceAll("(\\d+)\\.(\\d+),", "$1,$2,");
        
        return fixed.split(",");
    }
    
    /**
     * Парсит одну строку в объект Product
     */
    private Product parseProduct(String[] parts, int lineNumber) {
        try {
            // Очищаем от кавычек и пробелов
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].trim().replaceAll("^\"|\"$", "");
            }
            
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            String category = parts[2];
            
            // Обработка цены - заменяем возможную запятую на точку
            String priceStr = parts[3].replace(',', '.').trim();
            // Убираем всё кроме цифр и точки
            priceStr = priceStr.replaceAll("[^\\d.]", "");
            double price = Double.parseDouble(priceStr);
            
            int quantity = Integer.parseInt(parts[4].replaceAll("[^\\d]", ""));
            String animalType = parts[5];
            
            // Очищаем тип животного от мусора
            animalType = animalType.replaceAll("[^\\p{IsCyrillic}\\p{IsLatin}]", "");
            
            return new Product(id, name, category, price, quantity, animalType);
            
        } catch (Exception e) {
            System.err.println("  Ошибка парсинга: " + e.getMessage());
            return null;
        }
    }
}