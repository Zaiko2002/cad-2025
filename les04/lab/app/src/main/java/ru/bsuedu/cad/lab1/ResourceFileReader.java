package ru.bsuedu.cad.lab;

import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ResourceFileReader implements Reader {
    
    @Value("${products.file.name:products.csv}")
    private String fileName;
    
    private final Parser parser;
    
    public ResourceFileReader(Parser parser) {
        this.parser = parser;
    }
    
    @PostConstruct
    public void init() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("[ResourceFileReader] Инициализирован: " + LocalDateTime.now().format(formatter));
        System.out.println("[ResourceFileReader] Загружаемый файл: " + fileName);
    }
    
    @Override
    public List<Product> read() {
        InputStream inputStream = getClass().getClassLoader()
            .getResourceAsStream(fileName);
        
        if (inputStream == null) {
            throw new RuntimeException("Файл не найден: " + fileName);
        }
        
        return parser.parse(inputStream);
    }
}