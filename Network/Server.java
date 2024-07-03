package Network;

import Utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Server {
    private final int port;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Server(int port) {
        this.port = port;
        start();
    }

    public void start() {
        try(ServerSocket server = new ServerSocket(port)) {
           socket = server.accept();
           input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           output = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Server started listening on port " + port);
        } catch (IOException exception) {
            Logger.error("Error: Starting Server");
        }
    }

    public String receive() {
        try {
            return input.readLine();
        } catch (IOException e) {
            Logger.error("Error: Reading Client Message");
        }
        return "";
    }

    public void send(String command) {
        output.println(command);
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.error("Error: Closing Server Socket");
        }
    }
}
