import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileServer {
    private static final int THREAD_POOL_SIZE = 10;
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    private static final Lock fileLock = new ReentrantLock();

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(AppConfig.SERVER_PORT)) {
            System.out.println("Server started. Listening on port " + AppConfig.SERVER_PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                threadPool.execute(new ClientHandler(clientSocket, fileLock));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
