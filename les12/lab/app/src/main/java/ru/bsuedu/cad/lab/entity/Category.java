package ru.bsuedu.cad.lab.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    private String description;
    
    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();
    
    public Category() {}
    
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    // Геттеры
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Product> getProducts() { return products; }
    
    // Сеттеры
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setProducts(List<Product> products) { this.products = products; }
}