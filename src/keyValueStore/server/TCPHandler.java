package keyValueStore.server;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class TCPHandler {
    private Map<String, String> store;
    private ServerSocket serverSocket;

    public TCPHandler(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.store = new HashMap<>();
    }

    public void start() {
        while (true) {
            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String request = in.readLine();
                String response = handleRequest(request);
                out.println(response);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String handleRequest(String request) {
        // handle PUT, GET, DELETE
        String[] parts = request.split(" ");
        String command = parts[0];
        switch (command) {
            case "PUT":
                return store.put(parts[1], parts[2]);
            case "GET":
                return store.get(parts[1]);
            case "DELETE":
                return store.remove(parts[1]);
            default:
                return "ERROR: Invalid command";
        }
    }
}