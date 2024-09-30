package keyValueStore.client;

public class ClientApp {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java client.ClientApp <host> <port> <protocol>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String protocol = args[2];

        if (protocol.equalsIgnoreCase("tcp")) {
            TCPClient client = new TCPClient(host, port);
            client.start();
        } else if (protocol.equalsIgnoreCase("udp")) {
            UDPClient client = new UDPClient(host, port);
            client.start();
        } else {
            System.out.println("Unknown protocol: " + protocol);
        }
    }
}