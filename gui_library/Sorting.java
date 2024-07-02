package gui_library;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Sorting {

    private JTable personalDatabaseTable;
    private TableRowSorter<TableModel> sortGeneral;
    private Map<Integer, SortOrder> map = new HashMap<>();
    private List<Integer> list = new ArrayList<>();

    // Constructor
    public Sorting(JTable personalDatabaseTable, TableRowSorter<TableModel> sortGeneral) {
        this.personalDatabaseTable = personalDatabaseTable;
        this.sortGeneral = sortGeneral;
        sortHeader();
    }

    // Method to handle sorting
    private void sorting(int column) {
        SortOrder sortOrder = map.getOrDefault(column, SortOrder.UNSORTED);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        switch (sortOrder) {
            case UNSORTED:
                map.put(column, SortOrder.ASCENDING);
                list.add(column);
                break;

            case ASCENDING:
                map.put(column, SortOrder.DESCENDING);
                break;

            case DESCENDING:
                map.remove(column);
                list.remove((Integer) column);
                break;
        }

        // Update sort keys based on current sorting settings
        for (int i : list) {
            if (map.containsKey(i)) {
                sortKeys.add(new RowSorter.SortKey(i, map.get(i)));
            }
        }

        sortGeneral.setSortKeys(sortKeys);
    }

    // Method to initialize sorting mechanism
    private void sortHeader() {
        JTableHeader getHeader = personalDatabaseTable.getTableHeader();
        getHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = personalDatabaseTable.columnAtPoint(e.getPoint());
                sorting(column);
            }
        });
    }
}
