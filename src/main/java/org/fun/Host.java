package org.fun;

public class Host {
    private String address;
    private int port;
    public Host(String address, int port){
        this.address = address;
        this.port = port;
    }
    public Host(String address) {
        String[] fullAddress = address.strip().split(" ");
        this.address = fullAddress[0];
        this.port = Integer.parseInt(fullAddress[1]);
    }
    public String getAddress() {
        return address;
    }
    public int getPort() {
        return port;
    }
}
