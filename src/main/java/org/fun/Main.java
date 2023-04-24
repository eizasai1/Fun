package org.fun;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main extends Thread{
    public static void main(String[] args) throws UnknownHostException {
        boolean useWebserver = true;
//        if (InetAddress.getLocalHost().getHostName().equals("DESKTOP-IVC9O4I")) {
//            Server.startUp(useWebserver);
//        }
        ClientThread clientThread = new ClientThread(useWebserver);
        clientThread.start();
    }
}