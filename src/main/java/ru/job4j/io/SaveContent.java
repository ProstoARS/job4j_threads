package ru.job4j.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SaveContent {

    private final String content;
    private final File file;

    public SaveContent(String content, File file) {
        this.content = content;
        this.file = file;
    }

    public void saveContent() throws IOException {
        try (OutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i++) {
                o.write(content.charAt(i));
            }
        }
    }
}
