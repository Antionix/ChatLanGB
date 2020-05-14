import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static java.lang.String.format;

public class ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickName;
    private boolean running;

    public ClientHandler(Socket socket, String nickName) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.nickName = nickName;
        this.running = true;
//        welcome();
    }

    private void welcome() throws IOException {
        out.writeUTF("Welcome, " + nickName);
        out.flush();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    private void sendMessage(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
    }

    public void broadCastMessage(String message) throws IOException {
        System.out.printf("> %s > All > %s\n", this.getNickName(), message);
        for (ClientHandler client : Server.getClients()) {
            if (!client.equals(this)) {
                client.sendMessage(format("%s: %s", this.getNickName(), message));
            } else {
                out.writeUTF("/OK:" + message);
                out.flush();
            }
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                if (socket.isConnected()) {
                    String clientMessage = in.readUTF();
                    if (clientMessage.equals("/exit")) {
                        Server.getClients().remove(this);
                        broadCastMessage(this.getNickName() + " break");
                        sendMessage(clientMessage);
                        running = false;
                        break;
                    } else if (clientMessage.startsWith("/auth")){
                        String[] part = clientMessage.split("\\s");
                        String newNick = "";
                        for (int i = 1; i < part.length; i++) {
                            newNick += format(" %s", part[i]);
                        }
                        for (ClientHandler u : Server.getClients()) {
                            if (u.equals(this)) {
                                u.setNickName(newNick);
                            }
                        }

                    }
                    broadCastMessage(clientMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
