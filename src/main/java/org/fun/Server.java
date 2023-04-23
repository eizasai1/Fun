package org.fun;


import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        try {
            System.out.println(ServerSetup.getHostAddress());
            int port = 1210;
            new WebHTTP(ServerSetup.getHostAddress(), 5000, port);
            ServerSetup server = new ServerSetup(port);
            server.start();
            server.communicationLoop();
        }
        catch (RuntimeException | IOException e) {

            throw new RuntimeException(e);
        }
    }
}
