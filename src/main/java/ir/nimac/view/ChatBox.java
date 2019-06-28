package ir.nimac.view;

import ir.nimac.controler.Core;
import ir.nimac.controler.User;
import ir.nimac.controler.client.Client;
import ir.nimac.model.map.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatBox extends JPanel {
    JScrollPane jScrollPane;
    Map map;
    private int fontSize = 40;
    private JPanel textPanel;
    private JTextField jTextField;
    private JButton send;

    public ChatBox(Map map) {
        this.map = map;
        textPanel = new JPanel();
        jTextField = new JTextField();
        send = new JButton("send");
        send.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (map.getMe() != null) {
                    if (map.getMe().isOnline()) {
                        Core.sendMessageHelper(map.getMe().getClient().getServerHandler().getOutToServer(),
                                jTextField.getText() + "\n");
                    }
                    //map.getMe().getServerHandler().sendMessage(jTextField.getText());
                }
            }
        });
        jScrollPane = new JScrollPane(textPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
        setLayout(new GridLayout(3, 1));
        add(jScrollPane);
        add(jTextField);
        add(send);

    }

    public void write(String text) {
        textPanel.add(new ChatLable(text));
    }

    private class ChatLable extends JLabel {
        public ChatLable(String text) {
            super(text);
            this.setFont(new Font(null, Font.PLAIN, fontSize));
        }
    }
}
