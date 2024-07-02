package gui_library.admin_crud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import database_system.BookDataBase;
import entities.book.Book;
import gui_library.AdminGUI;
import lang_change.Lang;

public class DeleteBook implements ActionListener {
    private AdminGUI adminGUI;
    private int TITLE_COLUMN_INDEX;
    private int AUTHOR_COLUMN_INDEX;

    public DeleteBook(AdminGUI adminGUI, int titleColumnIndex, int authorColumnIndex) {
        this.adminGUI = adminGUI;
        this.TITLE_COLUMN_INDEX = titleColumnIndex;
        this.AUTHOR_COLUMN_INDEX = authorColumnIndex;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = adminGUI.jt.getSelectedRow();
        if (selectedRow != -1) {
            String bookTitle = (String) adminGUI.jt.getValueAt(selectedRow, TITLE_COLUMN_INDEX);
            String author = (String) adminGUI.jt.getValueAt(selectedRow, AUTHOR_COLUMN_INDEX);

            int option = JOptionPane.showConfirmDialog(null, "Do you want to delete " + bookTitle + " book?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                Book deletedBook = BookDataBase.MainBookList.deleteBook(bookTitle, author);
                if (deletedBook != null) {
                    adminGUI.model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Book not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, Lang.selectBookToDelete, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
