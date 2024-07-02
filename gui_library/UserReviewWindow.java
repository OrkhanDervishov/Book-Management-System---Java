package gui_library;


import database_system.BookDataBase;
import database_system.ReviewDataBase;
import database_system.exceptions.IllegalMemberException;
import gui_elements.*;
import lang_change.Lang;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import program_settings.Parametres;

public class UserReviewWindow extends JFrame {
    private JFrame frame;
    private JPanel panel;
    private JTextArea textArea;
    private JButton saveButton;
    private Label titleLabel;
    private Label authorLabel;
    private Label ratingLabel;
    private String title;
    private String author;

    public UserReviewWindow(String title, String author) {
        super("User Review");

        this.title = title;
        this.author = author;

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setBounds(500, 350, 500, 400);
        this.setResizable(false);
        this.setVisible(true);

        panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);


        titleLabel = new Label(10, 10, 150, 20, Lang.bookTitle + title, panel);
        authorLabel = new Label(10, 40, 150, 20, Lang.bookAuthor + author, panel);
        try {

            ratingLabel = new Label(10, 70, 150, 20, Lang.bookRating + BookDataBase.MainBookList.getMember(title, author).countRatingString(), panel);   
        } catch (IllegalMemberException e) {
        }


        textArea = new JTextArea(10, 25);
        textArea.setBounds(50, 100, 400, 150);
        panel.add(textArea);

        saveButton = new JButton(Lang.confirm);
        saveButton.setBounds(200, 275, 100, 25);
        saveButton.setBackground(Color.decode("#E5E1DA"));
        saveButton.addActionListener(new ActionListener() {
            @Override
           public void actionPerformed(ActionEvent e) {
                String reviewContent = textArea.getText(); // Get review text from textarea
                if (!reviewContent.isEmpty()) {
                    ReviewDataBase.addReview(Parametres.getActiveUser(), title, author, reviewContent);
                    JOptionPane.showMessageDialog(UserReviewWindow.this, "Review saved successfully!");
                    // Update user review in personal database panel
                    UserGUI.updateUserReview(title, author, reviewContent);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(UserReviewWindow.this, "Please enter a review before saving.");
                }
            }
        });
        panel.add(saveButton);
    }
    
}
