import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Server implements Runnable {
    Socket socket; // the client socket
    public final static Object sentinel = new Object(); // gatekeeper object for concurrency
    private static HashMap<String, User> users; // all the users

    /**
     * Initiate a new server object
     * @param socket the client object associate with this server
     */
    public Server(Socket socket) {
        this.socket = socket;
    }

//    private boolean buyItem(String command, ObjectOutputStream output) {
//        // do sth
//        output.writeObject(new ArrayList<>());
//    }

    /**
     *
     * @param command
     * @param output
     */
    private void processCommand(String command, ObjectOutputStream output) {

    }

    public void run() {
        System.out.printf("Connection received from %s\n", socket);
        try (Scanner input = new Scanner(socket.getInputStream())) {
            try (ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {
                while (input.hasNextLine()) {
                    String command = input.nextLine();
                    System.out.printf("Received command: %s", command);
                    processCommand(command, output);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException { // TODO: handle exception instead
//        ArrayList<String> product = new ArrayList<>();
//        product.add("apple");
//        product.add("pen");
//        product.add("pencil");
//        Store store1 = new Store("Amazon", product);
//
//        ArrayList<String> product2 = new ArrayList<>();
//        product2.add("banana");
//        product2.add("eraser");
//        product2.add("computer");
//        Store store2 = new Store("Lazada", product2);
//
//
//        ArrayList<String> inbox = new ArrayList<>();
//        inbox.add("alo");
//        inbox.add("dmm");
//
//        ArrayList<Store> stores = new ArrayList<>();
//        stores.add(store1);
//        stores.add(store2);
//
//        Seller seller1 = new Seller("bao2803", "phan43@purdue.edu", "Abc@1", inbox, stores);
//        seller1.printSeller();
//        Seller seller2 = new Seller("bao2003", "phan34@purdue.edu", "Abc@2", inbox, stores);
//        seller2.printSeller();

//        HashMap<String, Seller> users = new HashMap<>();
//        users.put(seller1.getEmail(), seller1);
//        users.put(seller2.getEmail(), seller2);
//
//        Server.setUsers(users);

        // Reading in existing users
        FileIO fileIO = new FileIO();
        Server.users = fileIO.readUsers();

        // Allocate server socket at given port...
        ServerSocket serverSocket = new ServerSocket(8080);

        // infinite server loop: accept connection,
        // spawn thread to handle...
        while (true) {
            System.out.printf("Socket open, waiting for connections on %s\n",
                    serverSocket); // TODO: delete all print statements after debugging
            Socket socket = serverSocket.accept();
            Server server = new Server(socket);
            new Thread(server).start();
        }
    }
}
