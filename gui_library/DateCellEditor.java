package gui_library;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.table.TableCellEditor;

import lang_change.Lang;

class DateCellEditor extends AbstractCellEditor implements TableCellEditor {
    private SpinnerDateModel dateModel;
    private JSpinnerDate dateSpinner;
    private JButton editButton;
    private JButton confirmButton;
    private JDialog dialog;
    private boolean saveOnClose = true;

    public DateCellEditor() {
        dateModel = new SpinnerDateModel();
        dateSpinner = new JSpinnerDate(dateModel);
        editButton = new JButton("...");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDialog();
            }
        });

        confirmButton = new JButton(Lang.confirm);
        confirmButton.setBackground(new Color(235, 226, 213));
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAndClose();
            }
        });
    }

    private void showDialog() {
        JFrame frame = new JFrame("Select Date/Tarix seç");
        dialog = new JDialog(frame, "Select Date/Tarix seç", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveOnClose = false;
            }
        });

        JPanel panel = new JPanel();
        panel.add(dateSpinner);
        panel.add(confirmButton);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        fireEditingStopped();
    }

    private void saveAndClose() {
        if (saveOnClose) {
            // we should add code to save info
        }
        dialog.dispose();
    }

    @Override
    public Object getCellEditorValue() {
        return dateModel.getDate();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return editButton;
    }
}

class JSpinnerDate extends JPanel {
    private JSpinner spinner;

    public JSpinnerDate(SpinnerDateModel model) {
        spinner = new JSpinner(model);
        SimpleDateFormat format = ((JSpinner.DateEditor) spinner.getEditor()).getFormat();
        format.applyPattern("yyyy-MM-dd");
        add(spinner);
    }

    public Date getDate() {
        return (Date) spinner.getValue();
    }
}
