package ru.bsuedu.cad.lab;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.nio.charset.StandardCharsets;

@Configuration
public class Main {
    
    @Bean
    public Parser parser() {
        return new CSVParser();
    }
    
    @Bean
    public Reader reader() {
        return new ResourceFileReader("products.csv", parser());
    }
    
    @Bean
    public ProductProvider productProvider() {
        return new ConcreteProductProvider(reader());
    }
    
    @Bean
    public Renderer renderer() {
        return new ConsoleTableRenderer();
    }
    
    public static void main(String[] args) {
        // Устанавливаем UTF-8 для корректного отображения русских букв
        System.setOut(new java.io.PrintStream(System.out, true, StandardCharsets.UTF_8));
        
        System.out.println("Загрузка зоомагазина...");
        
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        
        ProductProvider provider = context.getBean(ProductProvider.class);
        Renderer renderer = context.getBean(Renderer.class);
        
        var products = provider.getProducts();
        renderer.render(products);
        
        ((AnnotationConfigApplicationContext) context).close();
    }
}