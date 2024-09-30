package keyValueStore.server;

import java.io.IOException;
import java.net.SocketException;

public class ServerApp {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java server.ServerApp <tcp-port> <udp-port>");
            return;
        }

        int tcpPort = Integer.parseInt(args[0]);
        int udpPort = Integer.parseInt(args[1]);

        try {
            Thread tcpThread = new Thread(() -> {
                try {
                    TCPHandler tcpHandler = new TCPHandler(tcpPort);
                    tcpHandler.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Thread udpThread = new Thread(() -> {
                try {
                    UDPHandler udpHandler = new UDPHandler(udpPort);
                    udpHandler.start();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            });

            tcpThread.start();
            udpThread.start();

            tcpThread.join();
            udpThread.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}