package gui_library;

import javax.swing.*;
import javax.swing.table.*;

import database_system.RatingDataBase;
import database_system.ReviewDataBase;
import database_system.exceptions.IllegalMemberException;
import lang_change.Lang;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReviewFrame extends JFrame {
    private String[] userList;

    public ReviewFrame(String title, String author, String rating, String username) {
        super("User Review");

        // Create data for the book table
        Object[][] bookData = { { title, author, rating } };
        String[] bookColumns = { Lang.bookTitle, Lang.bookAuthor, Lang.bookRating };

                // Create and configure the book table
        JTable bookTable = new JTable(bookData, bookColumns);
        bookTable.setEnabled(false);
        bookTable.setRowHeight(20);
        JTableHeader bookHeader = bookTable.getTableHeader();
        bookHeader.setBackground(Color.decode("#ADC4CE"));

        // Create data for the user review table
        Object[][] userData = { { username, "", "" } };
        String[] userColumns = { Lang.usernameLabel, Lang.userRating, Lang.userReview };

        // Create and configure the user review table
        JTable userTable = new JTable(userData, userColumns);
        userTable.setEnabled(false);
        userTable.setRowHeight(20);
        JTableHeader userHeader = userTable.getTableHeader();
        userHeader.setBackground(Color.decode("#ADC4CE"));

        // Create scroll panes for the tables
        JScrollPane bookScrollPane = new JScrollPane(bookTable);
        JScrollPane userScrollPane = new JScrollPane(userTable);

        // Create panels to hold the tables
        JPanel bookPanel = new JPanel(new GridLayout(0, 1));
        bookPanel.setBorder(BorderFactory.createTitledBorder(Lang.bookDetails));
        bookPanel.add(bookScrollPane);

        JPanel userPanel = new JPanel(new GridLayout(0, 1));
        userPanel.setBorder(BorderFactory.createTitledBorder("User Review"));
        userPanel.add(userScrollPane);

        setLayout(new GridLayout(2, 1));
        add(bookPanel);
        add(userPanel);

        setPreferredSize(new Dimension(500, 230));
        pack();
        setVisible(true);
        setLocationRelativeTo(null);

        loadUserList();

        userTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = userTable.getColumnModel().getColumnIndex(Lang.usernameLabel); 
                int row = userTable.rowAtPoint(e.getPoint());

                if (column == 0 && row != -1) {
                    String selectedUser = showUserListDialog();
                    if (selectedUser != null) {
                        openReviewFrame(selectedUser, title, author, rating);
                    }
                }
            }
        });
    }

    private String showUserListDialog() {
        return (String) JOptionPane.showInputDialog(null, Lang.selectUser, "User List", JOptionPane.PLAIN_MESSAGE, null, userList, null);
    }

    private void loadUserList() {
        String userListFilePath = "./data/user_list.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(userListFilePath))) {
            userList = reader.lines().map(line -> line.split(";")[0]).toArray(String[]::new);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading user list: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void openReviewFrame(String username, String title, String author, String rating) {
        dispose();

        try {
            String content = ReviewDataBase.getReviewContent(ReviewDataBase.getReviewIndex(username, title, author));
        } catch (IllegalMemberException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // String rating = RatingDataBase.getRatingContent(RatingDataBase.)
        // ReviewFrame reviewFrame = new ReviewFrame(title, author, rating, username, content);
        // reviewFrame.setVisible(true);
    }
}
