package gui_library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import lang_change.Lang;


public class EditableReviewFrame extends JFrame {
    private JTextField ratingField;
    private JTextArea reviewArea;

    public EditableReviewFrame(String title, String author, String rating, String username) {
        super("Edit User Review");

        // Create rating field
        ratingField = new JTextField(10);
        JLabel ratingLabel = new JLabel("Rating:");
        JPanel ratingPanel = new JPanel();
        ratingPanel.add(ratingLabel);
        ratingPanel.add(ratingField);

        // Create review text area
        reviewArea = new JTextArea(5, 20);
        JScrollPane reviewScrollPane = new JScrollPane(reviewArea);
        JLabel reviewLabel = new JLabel("Review:");
        JPanel reviewPanel = new JPanel();
        reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
        reviewPanel.add(reviewLabel);
        reviewPanel.add(reviewScrollPane);

        // Create save button
        JButton saveButton = new JButton(Lang.confirm);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        // Configure the layout of the frame and add panels
        setLayout(new BorderLayout());
        JPanel editPanel = new JPanel(new GridLayout(2, 1));
        editPanel.add(ratingPanel);
        editPanel.add(reviewPanel);
        add(editPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    // Method to get the modified rating
    public String getRating() {
        return ratingField.getText();
    }

    // Method to get the modified review
    public String getReview() {
        return reviewArea.getText();
    }
}
