package ir.nimac.view;

import ir.nimac.model.Alevel.Cell;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BlockPanel extends JPanel {
    private List<Cell> cellList = new ArrayList<>();

    public BlockPanel() {
        setLayout(new GridLayout(1, 1));
    }

    protected synchronized void paintComponent(Graphics G) {
        for (Cell cell : cellList) {
            if (cell.isVisible()) {
                G.drawImage(cell.currentPic, 0, 0, getWidth(), getHeight(), null);
            }
        }
    }

    public synchronized void add(Cell cell) {
        cellList.add(cell);
    }

    public synchronized void remove(Cell cell) {
        cellList.remove(cell);
    }

}
