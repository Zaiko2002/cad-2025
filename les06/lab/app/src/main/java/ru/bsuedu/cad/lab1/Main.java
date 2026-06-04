package ru.bsuedu.cad.lab;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "ru.bsuedu.cad.lab")
@EnableAspectJAutoProxy
@Import(DatabaseConfig.class)
public class Main {
    
    public static void main(String[] args) {
        System.out.println("Загрузка приложения...");
        
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        
        ProductProvider provider = context.getBean(ProductProvider.class);
        Renderer renderer = context.getBean(DatabaseRenderer.class);
        
        var products = provider.getProducts();
        renderer.render(products);
        
        // Выполняем запрос к БД
        CategoryRequest categoryRequest = context.getBean(CategoryRequest.class);
        categoryRequest.executeRequest();
        
        ((AnnotationConfigApplicationContext) context).close();
        
        System.out.println("Приложение завершило работу.");
    }
}