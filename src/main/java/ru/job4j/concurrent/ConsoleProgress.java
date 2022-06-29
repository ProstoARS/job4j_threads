package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        char[] process = {'|', '/', '-', '\\'};
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.print("\r load: " + process[i++]);
                if (i == process.length) {
                    i = 0;
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println();
                System.out.println("Interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }
}
