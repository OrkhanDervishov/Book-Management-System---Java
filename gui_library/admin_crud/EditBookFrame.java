package gui_library.admin_crud;

import database_system.BookDataBase;
import entities.book.Book;
import gui_elements.Button;
import gui_elements.Label;
import gui_elements.TextField;
import gui_library.AdminGUI;
import lang_change.Lang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditBookFrame extends JFrame {
    private TextField titleField;
    private TextField authorField;
    private Button applyButton;
    private DefaultTableModel model;
    private String originalTitle;
    private String originalAuthor;

    public EditBookFrame(String title, String author, AdminGUI parent, DefaultTableModel model) {
        this.model = model;
        this.originalTitle = title;
        this.originalAuthor = author;

        setTitle(Lang.editBook);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 200);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        new Label(10, 30, 50, 25, Lang.bookTitle, panel);
        new Label(10, 70, 50, 25, Lang.bookAuthor, panel);

        titleField = new TextField(70, 30, 200, 25, 25, panel);
        titleField.getObject().setText(title);
        authorField = new TextField(70, 70, 200, 25, 25, panel);
        authorField.getObject().setText(author);

        applyButton = new Button(120, 120, 100, 25, Lang.confirm, panel);
        applyButton.getObject().addActionListener(new ApplyChangesListener(parent));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class ApplyChangesListener implements ActionListener {
        private AdminGUI parent;

        public ApplyChangesListener(AdminGUI parent) {
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String newTitle = titleField.getObject().getText();
            String newAuthor = authorField.getObject().getText();

            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, AdminGUI.TITLE_COLUMN_INDEX).equals(originalTitle) &&
                        model.getValueAt(i, AdminGUI.AUTHOR_COLUMN_INDEX).equals(originalAuthor)) {
                    model.setValueAt(newTitle, i, AdminGUI.TITLE_COLUMN_INDEX);
                    model.setValueAt(newAuthor, i, AdminGUI.AUTHOR_COLUMN_INDEX);
                    break;
                }
            }

            Book editedBook = BookDataBase.MainBookList.editBook(originalTitle, originalAuthor, newTitle, newAuthor);

            dispose();
        }
    }
}
