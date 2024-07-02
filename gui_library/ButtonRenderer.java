package gui_library;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import lang_change.Lang;

import java.awt.Color;
import java.awt.Component;

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        String buttonText = (value != null && value.equals(Lang.bookAdd)) ? Lang.bookAdded : Lang.bookAdd;
        setText(buttonText);

        // Set background color based on the value
        if (value != null && value.equals(Lang.bookAdd)) {
            setBackground(new Color(0xB0A695)); // Change to whatever color you prefer
        } else {
            setBackground(new Color(0xE5E1DA)); // Change to whatever color you prefer
        }

        return this;
    }

}