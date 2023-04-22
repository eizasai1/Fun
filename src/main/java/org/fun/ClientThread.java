package org.fun;

public class ClientThread extends Thread{
    @Override
    public void run(){
        String ip = "172.26.96.88";
        int port = 55554;
        System.out.println(ip + " " + port);
        Host host = new Host("172.26.96.88", 55554);
        Client client = new Client(host.getAddress(), host.getPort());
        client.communicationLoop();
    }
}
