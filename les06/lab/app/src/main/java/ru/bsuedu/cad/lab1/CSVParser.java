package ru.bsuedu.cad.lab;

import java.io.InputStream;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CSVParser implements Parser {
    
    private final ProductParser productParser;
    
    public CSVParser(ProductParser productParser) {
        this.productParser = productParser;
    }
    
    @Override
    public List<Product> parse(InputStream inputStream) {
        return productParser.parse(inputStream);
    }
}