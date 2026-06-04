package ru.bsuedu.cad.lab;


public class Product {
    private int id;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private String animalType;  // новый атрибут: для какого животного
    
    public Product() {}
    
    public Product(int id, String name, String category, double price, int quantity, String animalType) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.animalType = animalType;
    }
    
    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public String getAnimalType() { return animalType; }
    public void setAnimalType(String animalType) { this.animalType = animalType; }
    
    @Override
    public String toString() {
        // Форматируем строку товара с учетом типа животного
        return String.format("| %-4d | %-22s | %-12s | %10.2f | %8d | %-12s |",
            id, truncate(name, 22), category, price, quantity, animalType);
    }
    
    // Вспомогательный метод для обрезки слишком длинных названий
    private String truncate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}