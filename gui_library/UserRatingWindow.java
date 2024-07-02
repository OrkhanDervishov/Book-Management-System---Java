package gui_library;


import database_system.RatingDataBase;
import lang_change.Lang;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import program_settings.Parametres;

public class UserRatingWindow extends JFrame {
    private JTable bookTable;
    private JButton saveButton;
    
    public UserRatingWindow(String title, String author) {
        super("User Rating");

        Object[][] bookData = { { title, author, "" } }; 
        String[] bookColumns = { Lang.bookTitle, Lang.bookAuthor, Lang.bookRating };

        DefaultTableModel bookModel = new DefaultTableModel(bookData, bookColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Only allow editing in the rating column
            }
        };

        bookTable = new JTable(bookModel) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 2) {
                    return new RatingCellRenderer();
                }
                return super.getCellRenderer(row, column);
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (column == 2) {
                    return new DefaultCellEditor(new RatingComboBox());
                }
                return super.getCellEditor(row, column);
            }
        };

        bookTable.setRowHeight(20);
        JTableHeader bookHeader = bookTable.getTableHeader();
        bookHeader.setBackground(Color.decode("#ADC4CE"));
        bookTable.setSelectionBackground(new Color(0, 0, 0, 0));

        JScrollPane bookScrollPane = new JScrollPane(bookTable);

        JPanel bookPanel = new JPanel(new GridLayout(0, 1));
        bookPanel.setBorder(BorderFactory.createTitledBorder(Lang.bookDetails));
        bookPanel.add(bookScrollPane);

        saveButton = new JButton(Lang.confirm);
        saveButton.setBackground(Color.decode("#E5E1DA")); 
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveRating();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        setLayout(new BorderLayout());
        add(bookPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(400, 150));
        pack(); 
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void saveRating() {
        // Get the rating information from the table
        String title = bookTable.getValueAt(0, 0).toString();
        String author = bookTable.getValueAt(0, 1).toString();
        String rating = bookTable.getValueAt(0, 2).toString();
    
        // Perform saving operations here, e.g., updating the database
        RatingDataBase.addRating(Parametres.getActiveUser(), title, author, Integer.parseInt(rating));

        dispose(); 
    }

    private class RatingComboBox extends JComboBox<Integer> {
        public RatingComboBox() {
            super(new Integer[]{1, 2, 3, 4, 5});
        }
    }

    private class RatingCellRenderer extends DefaultTableCellRenderer {
        public RatingCellRenderer() {
            super();
            setHorizontalAlignment(JLabel.CENTER);
        }
    }
}
