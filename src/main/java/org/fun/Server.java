package org.fun;

public class Server {
    public static void main(String[] args) {
        System.out.println(ServerSetup.getHostAddress());
        ServerSetup server = new ServerSetup(12344);
        server.start();
        server.communicationLoop();
    }
}
