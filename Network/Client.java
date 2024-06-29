package Network;

import Configuration.NetworkConfiguration;
import Utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final int port;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Client(int port) {
        this.port = port;
        connect();
    }

    public void connect() {
        try {
            socket = new Socket(NetworkConfiguration.HOST, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            close();
            Logger.error("Error: Starting Network.Client");
        }
    }

    public String receive() {
        try {
            return input.readLine();
        } catch (IOException e) {
            Logger.error("Error: Reading Network.Server Response");
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
            Logger.error("Error: Closing Network.Client Socket");
        }
    }
}
