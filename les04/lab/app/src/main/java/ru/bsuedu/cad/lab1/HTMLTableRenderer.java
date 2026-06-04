package ru.bsuedu.cad.lab;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HTMLTableRenderer implements Renderer {
    
    @Override
    public void render(List<Product> products) {
        if (products == null || products.isEmpty()) {
            System.out.println("Нет данных для HTML отчета");
            return;
        }
        
        String htmlContent = generateHTML(products);
        
        try {
            String outputPath = Paths.get("products_report.html").toAbsolutePath().toString();
            FileWriter writer = new FileWriter(outputPath);
            writer.write(htmlContent);
            writer.close();
            
            System.out.println("HTML отчет создан: " + outputPath);
        } catch (IOException e) {
            System.err.println("Ошибка при создании HTML файла: " + e.getMessage());
        }
    }
    
    private String generateHTML(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
        sb.append("<head>\n");
        sb.append("    <meta charset=\"UTF-8\">\n");
        sb.append("    <title>Зоомагазин - Отчет о товарах</title>\n");
        sb.append("    <style>\n");
        sb.append("        body { font-family: Arial, sans-serif; margin: 20px; }\n");
        sb.append("        h1 { color: #2c3e50; text-align: center; }\n");
        sb.append("        table { border-collapse: collapse; width: 100%; margin-top: 20px; }\n");
        sb.append("        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }\n");
        sb.append("        th { background-color: #4CAF50; color: white; }\n");
        sb.append("        tr:nth-child(even) { background-color: #f2f2f2; }\n");
        sb.append("        tr:hover { background-color: #ddd; }\n");
        sb.append("        .stats { margin-top: 30px; padding: 15px; background-color: #e9f7ef; border-radius: 5px; }\n");
        sb.append("    </style>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        sb.append("    <h1>🐾 Зоомагазин - Отчет о товарах 🐾</h1>\n");
        sb.append("    \n");
        sb.append("    <table>\n");
        sb.append("        <thead>\n");
        sb.append("            <tr>");
        sb.append("                <th>ID</th>\n");
        sb.append("                <th>Название товара</th>\n");
        sb.append("                <th>Категория</th>\n");
        sb.append("                <th>Цена (₽)</th>\n");
        sb.append("                <th>Остаток</th>\n");
        sb.append("                <th>Для кого</th>\n");
        sb.append("            </td>\n");
        sb.append("        </thead>\n");
        sb.append("        <tbody>\n");
        
        for (Product p : products) {
            sb.append("            <tr>");
            sb.append("                <td>").append(p.getId()).append("</td>\n");
            sb.append("                <td>").append(escapeHtml(p.getName())).append("</td>\n");
            sb.append("                <td>").append(escapeHtml(p.getCategory())).append("</td>\n");
            sb.append("                <td>").append(String.format("%.2f", p.getPrice())).append("</td>\n");
            sb.append("                <td>").append(p.getQuantity()).append("</td>\n");
            sb.append("                <td>").append(escapeHtml(p.getAnimalType())).append("</td>\n");
            sb.append("            </td>\n");
        }
        
        sb.append("        </tbody>\n");
        sb.append("    </table>\n");
        sb.append("    \n");
        sb.append("    <div class=\"stats\">\n");
        sb.append("        <h3>📊 Статистика</h3>\n");
        
        long totalProducts = products.size();
        double totalValue = products.stream().mapToDouble(p -> p.getPrice() * p.getQuantity()).sum();
        
        sb.append("        <p><strong>Всего товаров:</strong> ").append(totalProducts).append("</p>\n");
        sb.append("        <p><strong>Общая стоимость:</strong> ").append(String.format("%.2f", totalValue)).append(" ₽</p>\n");
        sb.append("    </div>\n");
        sb.append("</body>\n");
        sb.append("</html>\n");
        
        return sb.toString();
    }
    
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;");
    }
}