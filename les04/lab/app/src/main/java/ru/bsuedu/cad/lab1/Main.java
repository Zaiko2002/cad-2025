package ru.bsuedu.cad.lab;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackages = "ru.bsuedu.cad.lab")
@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
public class Main {
    
    public static void main(String[] args) {
        System.out.println("Загрузка приложения...");
        
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        
        ProductProvider provider = context.getBean(ProductProvider.class);
        Renderer renderer = context.getBean(HTMLTableRenderer.class);  // HTML вместо консоли
        
        var products = provider.getProducts();
        renderer.render(products);
        
        ((AnnotationConfigApplicationContext) context).close();
        
        System.out.println("Приложение завершило работу.");
    }
}