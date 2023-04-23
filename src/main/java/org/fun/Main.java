package org.fun;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main extends Thread{
    public static void main(String[] args) throws UnknownHostException {
        if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-IVC9O4I")) {
            Server.startUp();
        }
        ClientThread clientThread = new ClientThread();
        clientThread.start();
    }
}