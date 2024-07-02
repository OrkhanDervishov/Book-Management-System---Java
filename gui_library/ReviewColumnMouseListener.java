package gui_library;

import javax.swing.*;
import lang_change.Lang;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReviewColumnMouseListener extends MouseAdapter {
    private final JTable table;
    private final int reviewColumnIndex;

    // Constructor to initialize the table and review column index
    public ReviewColumnMouseListener(JTable table, int reviewColumnIndex) {
        this.table = table;
        this.reviewColumnIndex = reviewColumnIndex;
    }

    // Override mouseClicked method to handle mouse clicks
    @Override
    public void mouseClicked(MouseEvent e) {
        int column = table.columnAtPoint(e.getPoint());
        int row = table.rowAtPoint(e.getPoint());

        if (column == reviewColumnIndex) {
            Object reviewValue = table.getValueAt(row, column);

            if (reviewValue != null && reviewValue.toString().equals(Lang.noReviews)) {
                String username = (String) reviewValue;
                String title = (String) table.getValueAt(row, DatabaseLib.TITLE_COLUMN_INDEX);
                String author = (String) table.getValueAt(row, DatabaseLib.AUTHOR_COLUMN_INDEX);
                String rating = (String) table.getValueAt(row, DatabaseLib.RATING_COLUMN_INDEX);
                new ReviewFrame(title, author, rating, username);
            }
        }
    }
}
