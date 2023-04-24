package org.fun;

import java.io.IOException;

public class ClientThread extends Thread{
    boolean webServerCommunication;
    public ClientThread(boolean communicationType) {
        webServerCommunication = communicationType;
    }
    @Override
    public void run(){
        String url = "http://ac79-199-111-214-74.ngrok-free.app";
        try {
            if (webServerCommunication) {
                Client client = new Client(url);
                client.webCommunicationLoop();
            }
            else {
                Host host = new Host((new WebHTTP(url)).getHost());
                Client client = new Client(host.getAddress(), host.getPort());
                client.communicationLoop();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
