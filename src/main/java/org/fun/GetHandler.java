package org.fun;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;


public class GetHandler implements HttpHandler {
    private String message;
    public GetHandler(String ip, int port) {
        message = ip + " " + port;
    }
    public GetHandler() {
        message = "powershell.exe ls";
    }
    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public void handle(HttpExchange he) throws IOException {
        String response = message;
        he.sendResponseHeaders(200, response.length());
        OutputStream outputStream =  he.getResponseBody();
        outputStream.write(response.toString().getBytes());
        outputStream.close();
    }
}
