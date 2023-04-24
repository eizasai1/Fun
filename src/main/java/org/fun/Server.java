package org.fun;


import java.io.IOException;

public class Server {
    public static void startUp(boolean webCommunication) {
        try {
            if (webCommunication) {
                WebHTTP webHTTP = new WebHTTP(ServerSetup.getHostAddress(), 5000);
                webHTTP.start();
                ServerSetup server = new ServerSetup(webHTTP.getGetHandler());
                server.webCommunicationLoop();
            }
            else {
                System.out.println(ServerSetup.getHostAddress());
                int port = 1210;
                new WebHTTP(ServerSetup.getHostAddress(), 5000, port);
                ServerSetup server = new ServerSetup(port);
                server.start();
                server.communicationLoop();
            }
        }
        catch (RuntimeException | IOException e) {

            throw new RuntimeException(e);
        }
    }
}
