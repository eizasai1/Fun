package org.fun;

import java.io.IOException;

public class ClientThread extends Thread{
    @Override
    public void run(){
        String url = "http://1e00-199-111-214-74.ngrok-free.app";
        Host host = new Host((new WebHTTP(url)).getHost());
        try {
            Client client = new Client(host.getAddress(), host.getPort());
            client.communicationLoop();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
