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
                String response = in.readLine();
                System.out.println("Server response: " + response);
                if (command.startsWith("READ")) {
                    // If the command is READ, the server might send multiple lines of content
                    StringBuilder fileContent = new StringBuilder();
                    while (response != null && !response.equals("END_OF_FILE")) {
                        fileContent.append(response).append("\n");
                        response = in.readLine();
                    }
                    System.out.println("File content:\n" + fileContent);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}