package ir.nimac.view;

import ir.nimac.model.map.Map;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    protected Map map;
    int fontSize = 40;

    public BoardPanel(Map map) {
        this.map = map;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setSize(500, 500);

    }

    public void setUser() {
        removeAll();
        JPanel gameInfoPanel = new JPanel(new GridLayout(1, 2));
        JLabel gameID = new JLabel(String.valueOf(map.getGameId()));
        JLabel gameInfo = new JLabel("Game ID : ");
        gameInfoPanel.add(gameInfo);
        gameInfoPanel.add(gameID);
        gameInfoPanel.setFont(new Font(null, Font.PLAIN, fontSize));
        gameInfoPanel.setSize(500, 500);
        add(gameInfoPanel);
        for (int i = 0; i < map.playerList.size(); i++) {
            JPanel userScore = new JPanel(new GridLayout(1, 2));
            JLabel name = new JLabel(map.playerList.get(i).getName());
            JLabel score = new JLabel(String.valueOf(map.playerList.get(i).getPoints()));
            userScore.add(name);
            userScore.add(score);
            userScore.setFont(new Font(null, Font.PLAIN, fontSize));
            userScore.setSize(500, 500);
            add(userScore);
        }
    }

}
