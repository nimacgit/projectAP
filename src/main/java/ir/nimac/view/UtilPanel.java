package ir.nimac.view;

import ir.nimac.controler.User;
import ir.nimac.controler.client.Client;
import ir.nimac.model.map.Map;

import javax.swing.*;
import java.awt.*;

public class UtilPanel extends JPanel {

    Map map;
    private BoardPanel boardPanel;
    private ChatBox chatBox;

    public UtilPanel(Map map) {
        super();
        this.map = map;
        boardPanel = new BoardPanel(map);
        chatBox = new ChatBox(map);
        this.setLayout(new GridLayout(2, 1));
        add(boardPanel);
        add(chatBox);
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public ChatBox getChatBox() {
        return chatBox;
    }
}
