package keyValueStore.server;

import java.net.*;

public class UDPHandler {
    private KeyValueStore store;
    private DatagramSocket socket;

    public UDPHandler(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
        this.store = new KeyValueStore();
    }

    public void start() {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            try {
                socket.receive(packet);
                String request = new String(packet.getData(), 0, packet.getLength());
                String response = handleRequest(request);
                byte[] responseData = response.getBytes();

                DatagramPacket responsePacket = new DatagramPacket(
                        responseData, responseData.length, packet.getAddress(), packet.getPort());
                socket.send(responsePacket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String handleRequest(String request) {
        // same logic as TCP
        String[] parts = request.split(" ");
        String command = parts[0];
        switch (command) {
            case "PUT":
                return store.put(parts[1], parts[2]);
            case "GET":
                return store.get(parts[1]);
            case "DELETE":
                return store.delete(parts[1]);
            default:
                return "ERROR: Invalid command";
        }
    }
}