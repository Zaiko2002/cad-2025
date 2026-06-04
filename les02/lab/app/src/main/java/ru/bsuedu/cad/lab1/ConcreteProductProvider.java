package ru.bsuedu.cad.lab;

import java.util.List;

public class ConcreteProductProvider implements ProductProvider {
    private final Reader reader;
    
    public ConcreteProductProvider(Reader reader) {
        this.reader = reader;
    }
    
    @Override
    public List<Product> getProducts() {
        return reader.read();
    }
}