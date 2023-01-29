package com.netty.nio.chat;

import java.util.Scanner;

public class ClientTest {
    public static void main(String[] args) {


        ChatClient chatClient = new ChatClient();

        new Thread(() -> {
            while (true) {
                chatClient.readInfo();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String content = scanner.nextLine();
            chatClient.sendInfo(content);
        }

        scanner.close();
    }
}
