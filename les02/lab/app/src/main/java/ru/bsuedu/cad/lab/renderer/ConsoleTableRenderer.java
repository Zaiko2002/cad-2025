package ru.bsuedu.cad.lab;

import java.util.List;

public class ConsoleTableRenderer implements Renderer {

    private final ProductProvider provider;

    public ConsoleTableRenderer(ProductProvider provider) {
        this.provider = provider;
    }

    @Override
    public void render() {
        List<Product> products = provider.getProducts();

        System.out.println("PET SHOP \n");

        double total = 0;

        for (Product p : products) {
            double sum = p.getTotalPrice();
            total += sum;

            System.out.printf("%d | %s | %s | %.2f | %d | %.2f\n",
                    p.getId(),
                    p.getName(),
                    p.getCategory(),
                    p.getPrice(),
                    p.getQuantity(),
                    sum);
        }

        System.out.printf("\nTOTAL: %.2f\n", total);
    }
}