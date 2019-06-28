package ir.nimac.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GameConfigPanel extends JPanel{


    private JPanel sizePanel = new JPanel(), namePanel = new JPanel(), typePanel = new JPanel();
    private int fontSize = 40;
    private JTextField heightField = new JTextField(),
            widthField = new JTextField(),
            ipField = new JTextField(),
            portField = new JTextField(),
            nameField = new JTextField();
    private JButton submit = new JButton("Go");
    private JButton loadButton = new JButton("load");
    private JRadioButton multi = new JRadioButton("multiPlayer"),
            single = new JRadioButton("singlePlayer");
    private ButtonGroup gameType = new ButtonGroup();
    private JLabel heightLabel = new JLabel("heigth:"),
            widthLabel = new JLabel("width:"),
            ipLabel = new JLabel("ip:"),
            portLabel = new JLabel("port:"),
            nameLabel = new JLabel("name:");

    public GameConfigPanel() {
        namePanel.setLayout(new GridLayout(3, 2));
        sizePanel.setLayout(new GridLayout(2, 2));
        heightLabel.setFont(new Font(null, Font.PLAIN, fontSize));
        widthLabel.setFont(new Font(null, Font.PLAIN, fontSize));
        ipLabel.setFont(new Font(null, Font.PLAIN, fontSize));
        portLabel.setFont(new Font(null, Font.PLAIN, fontSize));
        gameType.add(multi);
        gameType.add(single);
        nameLabel.setFont(new Font(null, Font.PLAIN, fontSize));
        heightField.setFont(new Font(null, Font.PLAIN, fontSize));
        widthField.setFont(new Font(null, Font.PLAIN, fontSize));
        ipField.setFont(new Font(null, Font.PLAIN, fontSize));
        portField.setFont(new Font(null, Font.PLAIN, fontSize));
        nameField.setFont(new Font(null, Font.PLAIN, fontSize));
        submit.setFont(new Font(null, Font.PLAIN, fontSize));
        loadButton.setFont(new Font(null, Font.PLAIN, fontSize));
        multi.setFont(new Font(null, Font.PLAIN, fontSize));
        single.setFont(new Font(null, Font.PLAIN, fontSize));
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        namePanel.add(ipLabel);
        namePanel.add(ipField);
        namePanel.add(portLabel);
        namePanel.add(portField);
        sizePanel.add(heightLabel);
        sizePanel.add(heightField);
        sizePanel.add(widthLabel);
        sizePanel.add(widthField);
        typePanel.add(multi);
        typePanel.add(single);
        single.setSelected(true);

        setLayout(new GridLayout(5, 1));
        setBackground(Color.blue);
        add(namePanel);
        add(sizePanel);
        add(typePanel);
        add(loadButton);
        add(submit);
        setVisible(true);

    }


    public JButton getLoadButton() {
        return loadButton;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JRadioButton getMulti() {
        return multi;
    }

    public JRadioButton getSingle() {
        return single;
    }


    public JTextField getHeightField() {
        return heightField;
    }


    public JTextField getWidthField() {
        return widthField;
    }

    public JButton getSubmit() {
        return submit;
    }

    public JTextField getIpField() {
        return ipField;
    }

    public JTextField getPortField() {
        return portField;
    }
}
