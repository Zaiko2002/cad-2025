package ru.bsuedu.cad.lab;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Выводит список товаров для животных в консоль в виде таблицы
 */
public class ConsoleTableRenderer implements Renderer {
    
    @Override
    public void render(List<Product> products) {
        if (products == null || products.isEmpty()) {
            System.out.println("🐾 В зоомагазине пока нет товаров!");
            return;
        }
        
        // Выводим шапку с логотипом
        System.out.println("\n🐶 🐱 🐹 ДОБРО ПОЖАЛОВАТЬ В ЗООМАГАЗИН 🐦 🐟 🐰\n");
        
        // Рисуем шапку таблицы
        String header = String.format("| %-4s | %-22s | %-12s | %10s | %8s | %-12s |",
            "ID", "Название товара", "Категория", "Цена (₽)", "Остаток", "Для кого");
        
        String separator = "+------+------------------------+--------------+------------+----------+--------------+";
        
        System.out.println(separator);
        System.out.println(header);
        System.out.println(separator);
        
        // Выводим каждый товар
        for (Product product : products) {
            System.out.println(product);
        }
        
        System.out.println(separator);
        
        // Дополнительная статистика
        System.out.println("\n📊 СТАТИСТИКА ЗООМАГАЗИНА:");
        System.out.println("   • Всего товаров: " + products.size());
        
        // Статистика по категориям
        Map<String, Long> byCategory = products.stream()
            .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
        
        System.out.println("   • По категориям:");
        byCategory.forEach((cat, count) -> 
            System.out.println("     - " + cat + ": " + count + " шт."));
        
        // Статистика по типам животных
        Map<String, Long> byAnimal = products.stream()
            .collect(Collectors.groupingBy(Product::getAnimalType, Collectors.counting()));
        
        System.out.println("   • Для кого товары:");
        byAnimal.forEach((animal, count) -> {
            String emoji = getAnimalEmoji(animal);
            System.out.println("     " + emoji + " " + animal + ": " + count + " шт.");
        });
        
        // Общая стоимость всех товаров
        double totalValue = products.stream()
            .mapToDouble(p -> p.getPrice() * p.getQuantity())
            .sum();
        System.out.printf("   • Общая стоимость товаров на складе: %.2f ₽%n", totalValue);
        
        System.out.println("\n🐾 Спасибо за покупку в нашем зоомагазине! 🐾\n");
    }
    
    private String getAnimalEmoji(String animal) {
        return switch (animal) {
            case "Собака" -> "🐕";
            case "Кошка" -> "🐈";
            case "Грызун" -> "🐹";
            case "Птица" -> "🐦";
            case "Рыбка" -> "🐟";
            case "Универсал" -> "🐾";
            default -> "📦";
        };
    }
}