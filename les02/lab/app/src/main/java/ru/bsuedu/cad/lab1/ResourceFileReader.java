package ru.bsuedu.cad.lab;

import java.io.InputStream;
import java.util.List;


public class ResourceFileReader implements Reader {
    private final String filePath;
    private final Parser parser;
    
    public ResourceFileReader(String filePath, Parser parser) {
        this.filePath = filePath;
        this.parser = parser;
    }
    
    @Override
    public List<Product> read() {
        
        InputStream inputStream = getClass().getClassLoader()
            .getResourceAsStream(filePath);
        
        if (inputStream == null) {
            throw new RuntimeException("Файл не найден: " + filePath);
        }
        
    
        return parser.parse(inputStream);
    }
}