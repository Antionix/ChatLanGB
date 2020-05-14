import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8189;

    protected static Socket socket;
    protected static DataInputStream in;
    protected static DataOutputStream out;
    protected static boolean toAutorized;

    private static boolean running;

    public Client() {
//        try {
        openConnection();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        running = true;
        startGUI();
    }

    private void startGUI() {
        JFrame frame = new ChatFrame();
        frame.setTitle("MyChatLan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void openConnection() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            setAutorized(false);
            Thread thread = new Thread(() -> {
                while (running) {
                    String messageIn = null;
                    try {
                        messageIn = in.readUTF();
                        if (messageIn.startsWith("/authok")) {
                            setAutorized(true);
                        }
                        if (messageIn.equalsIgnoreCase("/exit:")) {
                            closeConnection();
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(messageIn);
                }
            });
            thread.setDaemon(true);
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
    }

    private void setAutorized(boolean b) {
        toAutorized = b;
    }

    private void closeConnection() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void userAuth(String text) {
        // TODO: 13.05.2020
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Client();
            }
        });

//        try (socket =new Socket(SERVER_HOST,SERVER_PORT)){
//            DataInputStream in = new DataInputStream(socket.getInputStream());
//            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//            boolean running = true;
//            Scanner sin = new Scanner(System.in);
//
//            Thread thread = new Thread(() -> {
//                while (running) {
//                    String messageIn = null;
//                    try {
//                        messageIn = in.readUTF();
//                        if (messageIn.equalsIgnoreCase("/exit:")) {
//                            in.close();
//                            out.close();
//                            break;
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(messageIn);
//                }
//            });
//            thread.setDaemon(true);
//            thread.start();
//
//            while (running) {
//                String messageOut = sin.nextLine();
//                if (messageOut.equals("/exit")) {
//                    out.writeUTF("/exit");
//                    out.flush();
//                    break;
//                }
//                out.writeUTF(messageOut);
//                out.flush();
//            }
//
//        } catch(Exception e){
//            e.printStackTrace();
//        };

    }
}
