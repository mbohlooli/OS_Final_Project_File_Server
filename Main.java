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
                var server = new FileServer();
                server.start();
                break;
            case 2:
                var client = new FileClient();
                client.start();
                break;
            default:
                break;
        }
    }
}

