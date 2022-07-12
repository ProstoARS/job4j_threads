package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> predicate) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) != -1) {
                if (predicate.test((char) data)) {
                    output.append((data));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public String parseWithoutUnicode() throws IOException {
        return getContent(c -> c < 0x80);
    }

    public String parseAll() throws IOException {
        return getContent(c -> true);
    }
}