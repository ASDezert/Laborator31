package com.brainacad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxySession {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888);
             Socket clientSocket = serverSocket.accept();
             Socket clientSocket2 = serverSocket.accept();
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));
             PrintWriter out2 = new PrintWriter(clientSocket2.getOutputStream(), true))
        {
            sendBrokerMessage(in, out, in2, out2);
        } catch (IOException e) {
            System.out.println("Error on server");
            System.exit(-1);
        }
    }

    private static void sendBrokerMessage (BufferedReader in, PrintWriter out, BufferedReader in2, PrintWriter out2) throws IOException {
        String input, input2, output, output2;
        System.out.println("Wait for messages");
        while ((input = in.readLine()) != null && (input2 = in2.readLine()) != null) {
            if (input.equalsIgnoreCase("exit") || input2.equalsIgnoreCase("exit")) break;

            //Отправка сообщения другому клиенту
            out.println("Response ::: " + "echo ->" + input2);
            System.out.println(input);
            out2.println("Response ::: " + "echo ->" + input);
            System.out.println(input2);
        }
    }
}
