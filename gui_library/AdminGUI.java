package gui_library;

import gui_library.admin_crud.AddBook;
import gui_library.admin_crud.DeleteBook;
import gui_library.admin_crud.DeleteUser;
import gui_library.admin_crud.EditBookFrame;
import lang_change.Lang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import entities.book.Book;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Arrays;

public class AdminGUI extends DatabaseLib {
    private JButton addBookButton;
    private JButton deleteBookButton;
    private JButton removeReviewButton;
    private JButton deleteUserButton;

    public AdminGUI() {
        super();

        // Initialized buttons
        addBookButton = new JButton(Lang.addNewBook);
        deleteBookButton = new JButton(Lang.deleteBook);
        removeReviewButton = new JButton(Lang.removeReview);
        deleteUserButton = new JButton(Lang.deleteUser);

        addBookButton.setBackground(new Color(0xE5E1DA));
        deleteBookButton.setBackground(new Color(0xE5E1DA));
        removeReviewButton.setBackground(new Color(0xE5E1DA));
        deleteUserButton.setBackground(new Color(0xE5E1DA));

        // Added action listeners to buttons
        addBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddBook(AdminGUI.this);
            }
        });

        deleteBookButton.addActionListener(new DeleteBook(this, TITLE_COLUMN_INDEX, AUTHOR_COLUMN_INDEX));

        // Action listener placeholders for remove review and delete user buttons
        removeReviewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // To be implemented
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeleteUser deleteUser = new DeleteUser();
                deleteUser.deleteUserAction();
            }
        });
    }

    // Method to add a book to the JTable
    public void addBookToList(Book book) {
        Object[] rowData = { book.getTitle(), book.getAuthor(), Lang.noRating, Lang.noReviews };
        model.addRow(rowData);
    }

    // Method to initialize the JTable with data
    public void initializeTable(Object[][] headersAndData) {
        column = Arrays.copyOf((String[]) headersAndData[0], ((String[]) headersAndData[0]).length + 1);
        column[column.length - 1] = "<html><b>" + Lang.operation + "</b></html>";

        data = new Object[headersAndData.length - 1][column.length];
        for (int i = 1; i < headersAndData.length; i++) {
            Object[] rowData = headersAndData[i];
            for (int j = 0; j < rowData.length; j++) {
                data[i - 1][j] = rowData[j];
            }
            data[i - 1][column.length - 1] = "";
        }

        model = new DefaultTableModel(data, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Created JTable
        jt = new JTable(model);
        jt.getTableHeader().setReorderingAllowed(false);
        jt.getColumnModel().getColumn(column.length - 1).setPreferredWidth(100);
        jt.getColumnModel().getColumn(column.length - 1).setCellRenderer(new ButtonRenderer());

        // Added mouse listener to JTable for edit functionality
        jt.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jt.rowAtPoint(evt.getPoint());
                int col = jt.columnAtPoint(evt.getPoint());
                if (col == column.length - 1) {
                    String title = (String) model.getValueAt(row, TITLE_COLUMN_INDEX);
                    String author = (String) model.getValueAt(row, AUTHOR_COLUMN_INDEX);
                    new EditBookFrame(title, author, AdminGUI.this, model);
                }
            }
        });

        // Added JTable to JScrollPane and add to tablePanel
        JScrollPane js = new JScrollPane(jt);
        tablePanel.add(js, BorderLayout.CENTER);

        // Created button panel and add buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBookButton);
        buttonPanel.add(deleteBookButton);
        buttonPanel.add(removeReviewButton);
        buttonPanel.add(deleteUserButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        TableColumnModel columnModel = jt.getColumnModel();
        TableColumn editColumn = columnModel.getColumn(columnModel.getColumnCount() - 1);
        editColumn.setCellRenderer(new EditButtonRenderer());
    }
}