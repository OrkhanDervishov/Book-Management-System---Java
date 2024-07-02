package gui_library.admin_crud;

import database_system.BookDataBase;
import database_system.exceptions.IllegalMemberException;
import entities.book.Book;
import gui_elements.Button;
import gui_elements.Label;
import gui_elements.TextField;
import gui_library.AdminGUI;
import lang_change.Lang;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;

public class AddBook extends JFrame {

    private TextField field1;
    private TextField field2;
    private Button button;
    private JPanel panel;
    @SuppressWarnings("unused")
    private AdminGUI adminGUI;

    public AddBook(AdminGUI adminGUI) {
        this.adminGUI = adminGUI;

        JFrame frame = new JFrame(Lang.addBook);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setBounds(500, 250, 350, 200);
        frame.setResizable(false);

        panel = new JPanel();
        panel.setVisible(true);
        panel.setLayout(null);
        frame.add(panel);

        new Label(10, 30, 50, 25, Lang.bookTitle, panel);
        new Label(10, 70, 50, 25, Lang.bookAuthor, panel);

        field1 = new TextField(70, 30, 200, 25, 25, panel);
        field2 = new TextField(70, 70, 200, 25, 25, panel);

        button = new Button(120, 120, 100, 25, Lang.confirm, panel);
        button.getObject().addActionListener((ActionEvent e) -> {
            try {
                Book newBook = Book.createBook(field1.getObject().getText(), field2.getObject().getText());
                BookDataBase.MainBookList.add(newBook);
                adminGUI.addBookToList(newBook); 
                frame.dispose();
            } catch (IllegalMemberException ex) {
                @SuppressWarnings("unused")
                Label info = new Label(100, 100, 200, 25, ex.getMessage(), panel);
            }
        });
    }
}
