import java.io.*;
import java.net.*;

public class FileClient {
    public void start() {
        try (Socket socket = new Socket(AppConfig.SERVER_ADDRESS, AppConfig.SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the server. Type your commands:");

            String command;
            while ((command = userInput.readLine()) != null) {
                out.println(command);
                if (command.equals("EXIT")) {
                    System.out.println("Exiting...");
                    socket.close();
                    return;
                }
                String response = in.readLine();
                System.out.println("Server response: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}