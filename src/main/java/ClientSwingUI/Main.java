package ClientSwingUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new ChatFrame();
        frame.setTitle("MyChatLan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
