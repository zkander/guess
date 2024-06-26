package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class ClientDriver {
    private static Client client = new Client();

    public static void main(String[] args) {


        try {
            if (args.length != 2) {
                args = new String[2];
//                System.out.println("Usage: java ClientDriver <server-ip> <server-port>");
//                System.exit(1);
//              to simplify the process
                args[0] = "localhost";
                args[1] = "13337";
            }

            client.setScanner(new Scanner(System.in));
            client.connectToServer(args[0], Integer.parseInt(args[1]));
            client.setReader(new BufferedReader(new InputStreamReader(client.getServerSocket().getInputStream())));
            client.setWriter(new PrintWriter(client.getServerSocket().getOutputStream(), true));
            client.setClientConsole(new BufferedReader(new InputStreamReader(System.in)));

            BufferedReader reader = client.getReader();
            PrintWriter writer = client.getWriter();
            BufferedReader console = client.getClientConsole();
            Scanner scanner = client.getScanner();

            // // Display the welcome message from the server
            System.out.println(reader.readLine());
            // Display the help message from the server
            System.out.println(reader.readLine());

            String msg = "end";
            String choice = "help";
            while (true) {
                while (reader.ready()) {
                    msg = reader.readLine();
                    System.out.println(msg);
                }
                // Read the user's choice and send it to the server
                if (console.ready()) {
                    choice = console.readLine();
                    if (choice.equals("exit"))
                        break;
                    writer.println(choice);
                }

            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            System.out.println("Client shutting down...");
            client.exit(0);
        }
    }
}
