package ru.bsuedu.cad.lab;

import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceFileReader implements Reader {

    private final String filename;

    public ResourceFileReader(String filename) {
        this.filename = filename;
    }

    @Override
    public List<String> readLines() {
        InputStream is = getClass().getClassLoader().getResourceAsStream(filename);

        BufferedReader reader = new BufferedReader(
    new InputStreamReader(is, StandardCharsets.UTF_8)
);

        return reader.lines().collect(Collectors.toList());
    }
}