package ru.bsuedu.cad.lab.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    private Integer quantity;
    
    @Column(name = "animal_type")
    private String animalType;
    
    public Product() {}
    
    public Product(String name, Category category, BigDecimal price, Integer quantity, String animalType) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.animalType = animalType;
    }
    
    // Геттеры
    public Long getId() { return id; }
    public String getName() { return name; }
    public Category getCategory() { return category; }
    public BigDecimal getPrice() { return price; }
    public Integer getQuantity() { return quantity; }
    public String getAnimalType() { return animalType; }
    
    // Сеттеры
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(Category category) { this.category = category; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setAnimalType(String animalType) { this.animalType = animalType; }
}