package org.fun;

import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

public class WebHTTP extends Thread{
    private String host;
    private String url;
    private GetHandler getHandler;
    private PostHandler postHandler;
    private int index;
    public WebHTTP(String ip, int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("Server started at " + port);
            getHandler = new GetHandler();
            postHandler = new PostHandler();
            server.createContext("/GET", getHandler);
            server.createContext("/POST", postHandler);
            server.setExecutor(null);
            server.start();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
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
            httpURLConnection.setRequestProperty("User-Agent", "Vic");
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
    public WebHTTP(int startIndex, String url) {
        this.url = url;
        this.index = startIndex;
    }
    public String getHost() {
        return host;
    }
    public static int getIndex(String message) {
        int toReturn = 0;
        for (int i = 1; i < message.length(); i += 1) {
            try {
                toReturn = Integer.parseInt(message.substring(0, i));
            }
            catch (NumberFormatException e) {
                return toReturn;
            }
        }
        return toReturn;
    }
    public String httpGet(int[] result) throws IOException{
        URL url = new URL(this.url + "GET");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("User-Agent", "Vic");
        int responseCode = httpURLConnection.getResponseCode();
        String message = "";
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                message += line;
            }
            bufferedReader.close();
            result[0] = index;
            index = getIndex(message);
        }
        httpURLConnection.disconnect();
        return message;
    }
    public int httpPost(String out) throws IOException{
        URL url = new URL(this.url + "POST");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("User-Agent", "Vic");
        httpURLConnection.setDoOutput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(out.getBytes());
        outputStream.flush();
        outputStream.close();
        int responseCode = httpURLConnection.getResponseCode();
        return responseCode;
    }
    public GetHandler getGetHandler() {
        return getHandler;
    }
    @Override
    public void run() {
        while (true) {
            if (postHandler.getStatus() == 1) {
                System.out.println(postHandler.getResponse());
            }
        }
    }
}
