import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Server {

    private final static int PORT = 8189;
    private final static String HOST = "localhost";

    private boolean running;
    private int cnt = 1;
    private static ConcurrentLinkedDeque<ClientHandler> clients;

    public static ConcurrentLinkedDeque<ClientHandler> getClients() {
        return clients;
    }

    public Server(int port) {
        running = true;
        clients = new ConcurrentLinkedDeque<>();
        try (ServerSocket srv = new ServerSocket(PORT)) {
            System.out.println(">>Server start");
            while (running) {
                Socket socket = srv.accept();
                ClientHandler client = new ClientHandler(socket, "Client#" + cnt);
                cnt++;
                clients.add(client);
//                System.out.println(client.getNickName()+" accept");
                client.broadCastMessage(client.getNickName() + " accept; on server " + clients.size());
                new Thread(client).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server(PORT);
    }
}

