package ru.bsuedu.cad.lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore   // <-- Игнорируем при JSON-сериализации
    private List<OrderItem> orderItems = new ArrayList<>();
    
    public Product() {}
    
    public Product(String name, Category category, BigDecimal price, Integer quantity, String animalType) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.animalType = animalType;
    }
    
    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getAnimalType() { return animalType; }
    public void setAnimalType(String animalType) { this.animalType = animalType; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
}