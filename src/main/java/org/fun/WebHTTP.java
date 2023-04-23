package org.fun;

import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.Buffer;

public class WebHTTP {
    private String host;
    public WebHTTP(String ip, int port, int connectionPort) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("Server started at " + port);
            server.createContext("/", new GetHandler(ip, connectionPort));
            server.setExecutor(null);
            server.start();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public WebHTTP(String connect) {
        try {
            URL url = new URL(connect);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()));
                String message = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    message += line + "\n";
                }
                bufferedReader.close();
                host = message;
            }
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public String getHost() {
        return host;
    }
}
