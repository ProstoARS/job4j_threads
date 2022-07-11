package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> predicate) throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) > 0) {
                if (predicate.test((char) data)) {
                    output.append((data));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}