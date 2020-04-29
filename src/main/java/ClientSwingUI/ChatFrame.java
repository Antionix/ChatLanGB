package ClientSwingUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.String.format;

public class ChatFrame extends JFrame {
    private JTextArea textArea;
    private JTextField inputTextField;
    private JButton sendButton;

    public ChatFrame() {
        buildGUI();
    }

    private void buildGUI() {
        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        Box box = Box.createHorizontalBox();
        add(box, BorderLayout.SOUTH);
        inputTextField = new JTextField();
        sendButton = new JButton("Send");
        box.add(inputTextField);
        box.add(sendButton);

        ActionListener sendListener = (ActionEvent e) -> {
            String inputText = inputTextField.getText();
            if (inputText != null && inputText.trim().length() > 0) {
                textArea.append(format("%s\n", inputText));

                inputTextField.selectAll();
                inputTextField.requestFocus();
                inputTextField.setText("");
            }
        };
        inputTextField.addActionListener(sendListener);
        sendButton.addActionListener(sendListener);
    }
}