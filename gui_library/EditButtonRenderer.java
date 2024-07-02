package gui_library;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import lang_change.Lang;

import java.awt.Color;
import java.awt.Component;

public class EditButtonRenderer extends JButton implements TableCellRenderer {
    public EditButtonRenderer() {
        setOpaque(true);
        setBackground(new Color(0xE5E1DA));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        setText("<html><b>" + Lang.editBook + "</b></html>");
        setFont(table.getFont());
        setForeground(Color.BLACK);
        return this;
    }
}