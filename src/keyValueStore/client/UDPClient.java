package keyValueStore.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPClient {
    private String host;
    private int port;

    public UDPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName(host);

            System.out.println("UDP client connected to server.");

            byte[] sendBuffer;
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket sendPacket;
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;

            while ((userInput = stdIn.readLine()) != null) {
                sendBuffer = userInput.getBytes(StandardCharsets.UTF_8);
                sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
                socket.send(sendPacket);

                // Receive response from server
                socket.receive(receivePacket);
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
                System.out.println("Server response: " + response);
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}