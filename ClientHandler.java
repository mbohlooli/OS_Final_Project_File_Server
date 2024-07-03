import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.Lock;

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final Lock fileLock;

    public ClientHandler(Socket clientSocket, Lock fileLock) {
        this.clientSocket = clientSocket;
        this.fileLock = fileLock;
    }

    public void run() {
        try (
            var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            var out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String request;
            while ((request = in.readLine()) != null) {
                if (request.startsWith("READ")) {
                    String filename = request.substring(5);
                    out.println(readFile(filename));
                } else if (request.startsWith("WRITE")) {
                    String[] parts = request.split(" ", 3);
                    String filename = parts[1];
                    String content = parts[2];
                    writeFile(filename, content);
                    out.println("WRITE SUCCESS");
                } else {
                    out.println("INVALID COMMAND");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFile(String filename) {
        fileLock.lock();
        try (BufferedReader reader = new BufferedReader(new FileReader(AppConfig.FILES_PATH + filename))) {
            var content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            content.append("END_OF_FILE");
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR READING FILE";
        } finally {
            fileLock.unlock();
        }
    }

    private void writeFile(String filename, String content) {
        fileLock.lock();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AppConfig.FILES_PATH + filename, true))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileLock.unlock();
        }
    }
}