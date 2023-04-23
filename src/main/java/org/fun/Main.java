package org.fun;

public class Main extends Thread{
    public static void main(String[] args) {
//        ClientThread clientThread = new ClientThread();
//        clientThread.start();
        String filepath = "C:\\Users\\eizak\\Desktop\\misc";
        EmailSender emailSender = new EmailSender();
        emailSender.send(filepath);
    }
}