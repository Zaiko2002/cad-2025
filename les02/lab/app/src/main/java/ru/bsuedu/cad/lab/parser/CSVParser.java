package ru.bsuedu.cad.lab;

import java.util.ArrayList;
import java.util.List;

public class CSVParser implements Parser {

    @Override
    public List<Product> parse(List<String> lines) {
        List<Product> products = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] p = lines.get(i).split(",");

            products.add(new Product(
                    Integer.parseInt(p[0]),
                    p[1],
                    p[2],
                    Double.parseDouble(p[3]),
                    Integer.parseInt(p[4])
            ));
        }

        return products;
    }
}