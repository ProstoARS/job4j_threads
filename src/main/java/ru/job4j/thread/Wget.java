package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int count = 1;
            long now = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long leadTime = System.currentTimeMillis() - now;
                System.out.println("\r downloading time = " + (speed - leadTime) + " count = " + count);
                if (leadTime < speed) {
                    Thread.sleep(speed - leadTime);
                    now = System.currentTimeMillis();
                }
                count++;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public static void checkValidateArgs(String[] args) {
        if (args.length < 2) {
            System.out.println("Check the correctness of the arguments."
                    + "\nEnter 2 parameters: url, speed to save");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        checkValidateArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        long now = System.currentTimeMillis();
        wget.start();
        wget.join();
        System.out.println();
        System.out.println(System.currentTimeMillis() - now);
    }
}
