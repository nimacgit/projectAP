package ir.nimac.view;

import javax.swing.*;
import java.awt.*;

public class ServerNewGamePanel extends JPanel {

    private int fontSize = 40;
    private JTextField heightField = new JTextField(),
            widthField = new JTextField(),
            maxUserField = new JTextField("maxuser");
    private JButton submit = new JButton("make");
    private JLabel heightLabel = new JLabel("heigth:"),
            widthLabel = new JLabel("width:");

    public ServerNewGamePanel() {
        setLayout(new GridLayout(3, 2));
        setSize(500, 500);
        heightLabel.setFont(new Font(null, Font.PLAIN, fontSize));
        widthLabel.setFont(new Font(null, Font.PLAIN, fontSize));
        heightField.setFont(new Font(null, Font.PLAIN, fontSize));
        widthField.setFont(new Font(null, Font.PLAIN, fontSize));
        maxUserField.setFont(new Font(null, Font.PLAIN, fontSize));
        submit.setFont(new Font(null, Font.PLAIN, fontSize));
        add(heightLabel);
        add(heightField);
        add(widthLabel);
        add(widthField);
        add(maxUserField);
        add(submit);
        setVisible(true);

    }

    public JTextField getHeightField() {
        return heightField;
    }

    public JTextField getWidthField() {
        return widthField;
    }

    public JTextField getMaxUserField() {
        return maxUserField;
    }

    public JButton getSubmit() {
        return submit;
    }
}
