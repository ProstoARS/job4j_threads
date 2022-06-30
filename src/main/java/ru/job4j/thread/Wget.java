package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    private static final int SECOND = 1000;
    private final String url;
    private final int speed;
    private final String fileName;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long now = System.currentTimeMillis();
            int downloadData = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long leadTime = System.currentTimeMillis() - now;
                    if (leadTime < SECOND) {
                        try {
                            Thread.sleep(SECOND - leadTime);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    now = System.currentTimeMillis();
                    downloadData = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkValidateArgs(String[] args) {
        if (args.length != 3) {
            System.out.println("Check the correctness of the arguments."
                    + System.lineSeparator()
                    + "Enter 3 parameters: url, speed and fileName to save");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        checkValidateArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String fileName = args[2];
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        wget.join();
    }
}
