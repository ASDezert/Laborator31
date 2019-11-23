package com.brainacad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client2 {
    public static void main(String[] args) throws IOException {
        try (Socket connectToServerSocket = new Socket("localhost", 8888);
             BufferedReader in = new BufferedReader(new InputStreamReader(connectToServerSocket.getInputStream()));
             PrintWriter out = new PrintWriter(connectToServerSocket.getOutputStream(), true);
             BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));)
        {
            // Нужно запустить два метода в разных потоках
            sendMessage(out, inu);
            getMessage(in);
        }
    }

    private static void sendMessage(PrintWriter out, BufferedReader inu) { // Отправка сообщения другому клиенту(с помощью сервера)
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Enter to send message to server. Write ‘exit’ to close");
                String userInput;
                try {
                    while ((userInput = inu.readLine()) != null) {
                        out.println(userInput);
                        if (userInput.equalsIgnoreCase("exit")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    private static void getMessage(BufferedReader in) throws IOException { // Принятие сообщения от другого клиента(с помощью сервера)
        new Thread(new Runnable() {
            @Override
            public void run() {
                String serverOutput;
                try {
                    serverOutput = in.readLine();
                    System.out.println(serverOutput);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}
