import javax.swing.*;

public class TestGUI {

    private static void startGUI() {
        JFrame frame = new ChatFrame();
        frame.setTitle("MyChatLan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        startGUI();
    }
}
