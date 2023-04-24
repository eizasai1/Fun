package org.fun;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PostHandler implements HttpHandler{
    String response = "";
    int status = 0;
    @Override
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(he.getRequestBody()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            response += line + "\n";
        }
        response += "Connected to " + he.getRemoteAddress().getAddress();
        status = 1;
    }
    public String getResponse() {
        String copy = response;
        status = 0;
        response = "";
        return copy;
    }
    public int getStatus() {
        return status;
    }
}
