package org.fun;

public class Server {
    public static void main(String[] args) {
        System.out.println(ServerSetup.getHostAddress());
        ServerSetup server = new ServerSetup(55554);
        server.start();
        server.communicationLoop();
    }
}
