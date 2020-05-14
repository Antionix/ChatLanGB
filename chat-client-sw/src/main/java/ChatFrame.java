import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

import static java.lang.String.format;

public class ChatFrame extends JFrame {

    private final ImageIcon ICON_PERSON = new ImageIcon("icons8-customer-64.png");
    private Container mainWin = getContentPane();
    private JTextField inputTextField;
    private JButton sendButton;
    private JTextArea textArea;
    //    private JPanel msgPanel;
    private LinkedList<String> users = new LinkedList<>();
    JTextField userNick;
    JButton btnConnect;
    String selectUser;

    public ChatFrame() {
        selectUser = "All";
        users.addLast(selectUser);
        users.addLast("User1");
        users.addLast("User2");
        users.addLast("User3");
        buildGUI();
    }

    //    private JPanel addMessage(int own, String ownName, String msg) {
    private void sendMessage() {


    }

    private void buildGUI() {
        // панель подключения к серверу (верх окна)
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel infoSrv = new JLabel("Info from server");
        infoSrv.setPreferredSize(new Dimension(450, 32));
        topPanel.add(infoSrv, BorderLayout.WEST);
        userNick = new JTextField("User");
        userNick.setColumns(15);
        topPanel.add(userNick, BorderLayout.CENTER);
        btnConnect = new JButton("Connect");
        topPanel.add(btnConnect, BorderLayout.EAST);

        ActionListener conListener = (ActionEvent con) -> {
            onAuthClick(userNick.getText());
        };
        btnConnect.addActionListener(conListener);
        userNick.addActionListener(conListener);

        mainWin.add(topPanel, BorderLayout.NORTH);


        // область чата
        JTextArea userList = new JTextArea(20, 20);
        userList.setEditable(false);
        userList.setLineWrap(false);
//        JList<Object> userList = new JList<>(users.toArray());
//        userList.setPrototypeCellValue("Nick-Name-User");
//        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        userList.setSelectedIndex(0);
//        userList.getSelectionModel().addListSelectionListener(
//                new ListSelectionListener() {
//                    @Override
//                    public void valueChanged(ListSelectionEvent e) {
//                        int sel = ((JList<?>) e.getSource()).getSelectedIndex();
//                        if (sel != 0) {
//                            selectUser = users.get(sel);
//                            System.out.println(":Selected > " + selectUser);
//                            ;
//                        } else {
//                            selectUser = "";
//                            System.out.println(":Selected > .");
//                        }
//                    }
//                }
//        );


        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        textArea.setLineWrap(true);

        JSplitPane splitArea = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(userList), new JScrollPane(textArea)
        );
        splitArea.setDividerSize(2);
        add(splitArea, BorderLayout.CENTER);
//        add(new JScrollPane(textArea), BorderLayout.CENTER);
//
//        JSplitPane splitArea = new JSplitPane();
//
        // панель сообщения (внизу окна)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton btnSendMsg = new JButton("Send");
        bottomPanel.add(btnSendMsg, BorderLayout.EAST);
        inputTextField = new JTextField();
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(inputTextField, BorderLayout.CENTER);

        ActionListener sendListener = (ActionEvent e) -> {
            String inputText = inputTextField.getText();
            if (inputText != null && inputText.trim().length() > 0) {
                textArea.append(format("%s\n", inputText));
//                msgPanel.add(addMessage(true, "my", inputText), BorderLayout.SOUTH);
                inputTextField.selectAll();
                inputTextField.requestFocus();
                inputTextField.setText("");
            }
        };
        inputTextField.addActionListener(sendListener);
        btnSendMsg.addActionListener(sendListener);
    }

    private void onAuthClick(String textValue) {
        String s = format("/auth %s", textValue);
        try {
            Client.out.writeUTF(s);
            Client.out.flush();

            if (Client.toAutorized) {
                users.addLast(userNick.getText());
                userNick.setEnabled(false);
                btnConnect.setEnabled(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


