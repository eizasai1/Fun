package org.fun;

public class ClientThread extends Thread{
    @Override
    public void run(){
        String ip = "127.0.0.1";
        int port = 4040;
        System.out.println(ip + " " + port);
        Host host = new Host(ip, port);
        Client client = new Client(host.getAddress(), host.getPort());
        client.communicationLoop();
    }
}
