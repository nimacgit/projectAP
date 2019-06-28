package ir.nimac.controler;

import ir.nimac.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.net.Socket;

public class IpScanner {
    private static final Point frameLocationPoint = new Point(800, 300);
    private static final int fontSize = 40;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ipScanner");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(frameLocationPoint);
        frame.setLayout(new BorderLayout());
        JPanel panel = (JPanel) frame.getContentPane();
        panel.setLayout(new GridLayout(3, 1));
        JPanel ipPanel = new JPanel(new GridLayout(1, 4));
        JPanel portPanel = new JPanel(new GridLayout(1, 3));
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.PAGE_AXIS));
        JScrollPane jScrollPane = new JScrollPane(resultPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JTextField fromIpField = new JTextField();
        JTextField toIpField = new JTextField();
        JTextField portField = new JTextField();
        JLabel fromIpLabel = new JLabel("from : ");
        fromIpLabel.setFont(new Font(null, Font.PLAIN, fontSize));
        JLabel toIpLabel = new JLabel("to : ");
        toIpLabel.setFont(new Font(null, Font.PLAIN, fontSize));
        JLabel portLabel = new JLabel("port : ");
        portLabel.setFont(new Font(null, Font.PLAIN, fontSize));
        JButton search = new JButton("search");
        ipPanel.add(fromIpLabel);
        ipPanel.add(fromIpField);
        ipPanel.add(toIpLabel);
        ipPanel.add(toIpField);
        portPanel.add(portLabel);
        portPanel.add(portField);
        portPanel.add(search);
        panel.add(ipPanel);
        panel.add(portPanel);
        panel.add(jScrollPane);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int st = Integer.valueOf(fromIpField.getText().split("\\.")[3]);
                    String ip = fromIpField.getText().substring(0, fromIpField.getText().length() -
                            fromIpField.getText().split("\\.")[3].length());
                    int fr = Integer.valueOf(toIpField.getText().split("\\.")[3]);
                    resultPanel.removeAll();
                    while (st <= fr) {
                        try (Socket socket2 = new Socket()) {
                            socket2.connect(new InetSocketAddress(ip + String.valueOf(st),
                                    Integer.valueOf(portField.getText())), 100);
                            if (socket2.isConnected()) {
                                resultPanel.add(new JLabel(ip + String.valueOf(st)));
                            }
                        } catch (Exception e2) {
                            System.err.println(ip + st);
                            Logger.getInstance().infoLog("ip : " + ip + String.valueOf(st) + " not exist");
                        }

//                        try(DatagramSocket socket = new DatagramSocket(Integer.valueOf(portField.getText()))){
//                            socket.bind(new InetSocketAddress(ip + String.valueOf(st), Integer.parseInt(portField.getText())));
//                            resultPanel.add(new JLabel(ip + String.valueOf(st)));
//                        } catch (Exception e2){
//                            System.err.println(ip + st);
//                            Logger.getInstance().infoLog("ip : " + ip + String.valueOf(st) + " not exist");
//                        }
                        st++;
                    }
                } catch (Exception e1) {
                    Logger.getInstance().debugLog(e1.getMessage());
                }

                frame.repaint();
                frame.revalidate();
            }
        });
        frame.pack();
        frame.setVisible(true);


    }
}
