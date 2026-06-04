package ru.bsuedu.cad.lab;

import java.io.InputStream;
import java.util.List;

public interface Parser {
    List<Product> parse(InputStream inputStream);
}