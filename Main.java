import Utils.Logger;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to file server");
        System.out.println("Please choose the service you need:");
        System.out.println("1. File Server");
        System.out.println("2. File Client");

        var scanner = new Scanner(System.in);
        var response = scanner.nextInt();
        switch (response) {
            case 1:
                System.out.print("Enter the server port: ");
                var serverPort = scanner.nextInt();
                var server = new FileServer(serverPort);
                server.send("Connected to Server");
                System.out.println(server.receive());
                server.close();
                break;
            case 2:
                System.out.print("Enter the server port: ");
                var clientPort = scanner.nextInt();
                var client = new FileClient(clientPort);
                System.out.println(client.receive());
                client.send("hello");
                client.close();
                break;
            default:
                Logger.error("Invalid Service Number");
                break;
        }

    }
}
