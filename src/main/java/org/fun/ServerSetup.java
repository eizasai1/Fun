package org.fun;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ServerSetup extends Thread{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader bufferedReader;
    private DataInputStream input;
    private DataOutputStream output;
    public ServerSetup(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        System.out.println("Connected to " + clientSocket.getInetAddress().getHostAddress());
        input = new DataInputStream(clientSocket.getInputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(input));
        output = new DataOutputStream(clientSocket.getOutputStream());
    }
    public void sendMessage(String message) throws IOException{
        if (message.length() == 0)
            return;
        output.writeChars(message + "`");
    }
    public static String getHostAddress() throws IOException {
        return InetAddress.getLocalHost().getHostAddress();
    }
    public void communicationLoop() throws IOException {
        System.out.println("communication available");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            sendMessage(scanner.nextLine());
        }
    }
    public void closeServer() {
        try {
            serverSocket.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void run() {
        String message = "";
        boolean toggle = false;
        char charToAdd;
        while (true) {
            try {
                if (input.available() > 0) {
                    while (input.available() > 0) {
                        charToAdd = input.readChar();
                        if (charToAdd == '`') {
                            toggle = true;
                            break;
                        }
                        message += charToAdd;
                    }
                    if (toggle) {
                        System.out.println(message);
                        message = "";
                        toggle = false;
                    }
                }
            } catch (IOException e) {
                this.closeServer();
                throw new RuntimeException(e);
            }
        }
    }
}