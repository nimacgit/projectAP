package ir.nimac.view;

import ir.nimac.controler.dataBase.DataBase;
import ir.nimac.model.Windows.MapLister;
import ir.nimac.model.Windows.MapListerButtonAction;
import ir.nimac.model.map.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MapListerPanel extends JPanel {


    private JScrollPane scrollPane;
    private JPanel panel;

    public MapListerPanel(Map map, List<Long> mapsId, MapListerButtonAction mapListerButtonAction) {
        super();
        setLayout(new GridLayout(1,1));
        panel = new JPanel();
        panel.setLayout(new GridLayout(mapsId.size(), 1));
        scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        for (int i = 0; i < mapsId.size(); i++) {
            panel.add(new mapButtons(mapsId.get(i), mapListerButtonAction));
        }
        add(scrollPane);

    }



    private class mapButtons extends JPanel {
        long id;
        int fontSize = 40;
        JButton button;
        MapListerButtonAction mapListerButtonAction;

        mapButtons(long id, MapListerButtonAction mapListerButtonAction) {
            super();
            button = new JButton("id is : " + String.valueOf(id));
            this.setLayout(new GridLayout(1, 1));
            this.id = id;
            this.mapListerButtonAction = mapListerButtonAction;

            setFont(new Font(null, 0, fontSize));
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    mapListerButtonAction.doAction(id);
                    //mapListerPanel.dispatchEvent(new WindowEvent(mapListerPanel, WindowEvent.WINDOW_CLOSING));

                }
            });
            this.add(button);
            setVisible(true);
        }
    }

}
