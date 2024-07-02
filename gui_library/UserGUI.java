package gui_library;

import gui_log_reg.LoginFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import lang_change.Lang;

public class UserGUI extends DatabaseLib {

    private static final int START_DATE_COLUMN_INDEX = 6;
    private static final int END_DATE_COLUMN_INDEX = 7;
    private final int REMOVE_COLUMN_INDEX = 10; 
    private JPanel personalDatabasePanel;
    private static DefaultTableModel personalDatabaseTableModel;
    private JTable personalDatabaseTable;
    private JTextField personalDatabaseSearchField;
    private ArrayList<Object> addedBooks = new ArrayList<>();
    private SpinnerDateModel startDateModel;
    private SpinnerDateModel endDateModel;
    @SuppressWarnings("unused")
    private JSpinner startDateSpinner;
    @SuppressWarnings("unused")
    private JSpinner endDateSpinner;

    public UserGUI() {
        super();
    }

    @Override
    public void initializeTable(Object[][] headersAndData) {
        // Overrided to customize table initialization
        column = Arrays.copyOf((String[]) headersAndData[0], ((String[]) headersAndData[0]).length + 1);
        column[column.length - 1] = "<html><b>" + Lang.addBook + "</b></html>";
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

        jt = new JTable(model);
        jt.getTableHeader().setReorderingAllowed(false);

        int addBookColumnWidth = 100;
        jt.getColumnModel().getColumn(column.length - 1).setPreferredWidth(addBookColumnWidth);

        jt.getColumnModel().getColumn(column.length - 1).setCellRenderer(new ButtonRenderer());
        js = new JScrollPane(jt);
        tablePanel.add(js, BorderLayout.CENTER);

        jt.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = jt.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / jt.getRowHeight();

                if (column == jt.getColumnCount() - 1 && row < jt.getRowCount()) {
                    addToPersonalDatabase(row);
                }
            }
        });

    }

    @Override
    public void addLeftPanels() {
        // Overrided to customize left panel addition
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JButton tableButton = new JButton(Lang.generalDatabase);
        JButton personalDatabaseButton = new JButton(Lang.personalDatabase);

        int buttonWidth = 250;
        int buttonHeight = 30;
        tableButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        personalDatabaseButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight));

        leftPanel.add(tableButton);
        leftPanel.add(personalDatabaseButton);

        Color buttonColor = new Color(150, 182, 197);
        tableButton.setBackground(buttonColor);
        personalDatabaseButton.setBackground(buttonColor);

        Color textColor = Color.BLACK;
        tableButton.setForeground(textColor);
        personalDatabaseButton.setForeground(textColor);

        tableButton.addActionListener(e -> {
            showTablePanel();
        });

        personalDatabaseButton.addActionListener(e -> {
            showPersonalDatabasePanel();
        });

        JButton logoutButton = new JButton(Lang.logOut);
        logoutButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight)); // Set maximum size for logout button
        logoutButton.setBackground(buttonColor);
        logoutButton.setForeground(Color.BLACK);

        // Action listener for logout button
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                LoginFrame.Login();
            }
        });

        leftPanel.add(logoutButton);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(1, 1, 1, 1)));

        jf.add(leftPanel, BorderLayout.WEST);
    }

    // Method to add selected book to personal database
    private void addToPersonalDatabase(int row) {
        Object[] rowData = new Object[column.length];
        for (int i = 0; i < column.length; i++) {
            rowData[i] = jt.getValueAt(row, i);
        }

        String bookTitle = (String) rowData[0];
        String bookAuthor = (String) rowData[1];
        String bookIdentifier = bookTitle + " - " + bookAuthor; // Unique identifier for the book

        if (addedBooks.contains(bookIdentifier)) {
            System.out.println(Lang.bookAlreadyAdded);
            return;
        }

        addedBooks.add(bookIdentifier);

        rowData[4] = Lang.notStarted;

        if (rowData.length > 7) {
            Object startDate = rowData[6];
            Object endDate = rowData[7];
            if (startDate != null && endDate != null) {
                if (((String) startDate).compareTo((String) endDate) > 0) {
                    Object temp = startDate;
                    rowData[6] = endDate;
                    rowData[7] = temp;
                }
            }
        }

        if (rowData.length > 9) {
            if (rowData[8] == null) {
                rowData[8] = Lang.addRating;
            }
            if (rowData[9] == null) {
                rowData[9] = Lang.addReview;
            }
        }

        jt.setValueAt(Lang.bookAdd, row, column.length - 1);

        addRowToPersonalDatabasePanel(rowData);

        System.out.println(Lang.addedBook + rowData[0]);
    }

    // Method to add a row to personal database panel
    private void addRowToPersonalDatabasePanel(Object[] rowData) {
        if (personalDatabasePanel == null) {
            initializePersonalDatabasePanel();
        }

        if (personalDatabaseTableModel == null) {
            personalDatabaseTableModel = new DefaultTableModel();
            personalDatabaseTable = new JTable(personalDatabaseTableModel);
            personalDatabaseTableModel.addColumn(Lang.bookTitle);
            personalDatabaseTableModel.addColumn(Lang.bookAuthor);
            personalDatabaseTableModel.addColumn(Lang.bookRating);
            personalDatabaseTableModel.addColumn(Lang.bookReviews);

            personalDatabaseTable.getTableHeader().setForeground(Color.BLACK);
            personalDatabaseTable.getTableHeader().setBackground(Color.decode("#ADC4CE"));
            personalDatabaseTable.getTableHeader()
                    .setPreferredSize(new Dimension(personalDatabaseTable.getTableHeader().getWidth(), 30));
            personalDatabaseTable.setBackground(Color.WHITE);
            personalDatabaseTable.setForeground(Color.BLACK);
            personalDatabaseTable.setSelectionBackground(Color.decode("#F1F0E8"));
            personalDatabaseTable.setSelectionForeground(Color.BLACK);

            TableColumnModel columnModel = personalDatabaseTable.getTableHeader().getColumnModel();
            columnModel.getColumn(TITLE_COLUMN_INDEX).setPreferredWidth(170);
            columnModel.getColumn(AUTHOR_COLUMN_INDEX).setPreferredWidth(200);
            columnModel.getColumn(RATING_COLUMN_INDEX).setPreferredWidth(170);
            columnModel.getColumn(REVIEW_COLUMN_INDEX).setPreferredWidth(260);

            personalDatabaseTable.setRowHeight(30);
            personalDatabaseTable.getTableHeader().setResizingAllowed(false);

            JScrollPane scrollPane = new JScrollPane(personalDatabaseTable);
            personalDatabasePanel.add(scrollPane);
        }

        personalDatabaseTableModel.addRow(rowData);
    }

    // Method to initialize personal database panel
    @SuppressWarnings("unchecked")
    private void initializePersonalDatabasePanel() {
        personalDatabasePanel = new JPanel();
        personalDatabasePanel.setLayout(new BorderLayout());

        addPersonalDatabaseSearchFunctionality();
        personalDatabaseTableModel = new DefaultTableModel();
        personalDatabaseTableModel.addColumn("<html><b>" + Lang.bookTitle + "</b></html>");
        personalDatabaseTableModel.addColumn("<html><b>" + Lang.bookAuthor + "</b></html>");
        personalDatabaseTableModel.addColumn("<html><b>" + Lang.bookRating + "</b></html>");
        personalDatabaseTableModel.addColumn("<html><b>" + Lang.bookReviews + "</b></html>");
        personalDatabaseTableModel.addColumn("<html><b>" + Lang.bookStatus + "</b></html>");
        personalDatabaseTableModel.addColumn("<html><b>" + Lang.bookSpentTime + "</b></html>");
        personalDatabaseTableModel.addColumn("<html><b>" + Lang.bookStartDate + "</b></html>");
        personalDatabaseTableModel.addColumn("<html><b>" + Lang.bookEndDate + "</b></html>");
        personalDatabaseTableModel.addColumn("<html><b>" + Lang.userRating + "</b></html>");
        personalDatabaseTableModel.addColumn("<html><b>" + Lang.userReview + "</b></html>");
        personalDatabaseTableModel.addColumn("<html><b>" + Lang.removeBook + "</b></html>");
        
        personalDatabaseTable = new JTable(personalDatabaseTableModel);
        personalDatabaseTable.getTableHeader().setReorderingAllowed(false);
        
        personalDatabaseTable.getTableHeader().setForeground(Color.BLACK);
        personalDatabaseTable.getTableHeader().setBackground(Color.decode("#ADC4CE"));
        personalDatabaseTable.getTableHeader()
        .setPreferredSize(new Dimension(personalDatabaseTable.getTableHeader().getWidth(), 50));
        personalDatabaseTable.setForeground(Color.BLACK);
        personalDatabaseTable.setSelectionBackground(Color.decode("#F1F0E8"));
        personalDatabaseTable.setSelectionForeground(Color.BLACK);
        
        
        TableColumnModel columnModel = personalDatabaseTable.getTableHeader().getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);
        columnModel.getColumn(1).setPreferredWidth(170);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(100);
        columnModel.getColumn(7).setPreferredWidth(100);
        columnModel.getColumn(8).setPreferredWidth(100);
        columnModel.getColumn(9).setPreferredWidth(100);

        personalDatabaseTable.setRowHeight(30);
        personalDatabaseTable.getTableHeader().setResizingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(personalDatabaseTable);
        personalDatabasePanel.add(scrollPane, BorderLayout.CENTER);
        
        personalDatabaseTable.getColumnModel().getColumn(REMOVE_COLUMN_INDEX).setHeaderValue("<html><b>" + Lang.removeBook + "</b></html>");
        personalDatabaseTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = personalDatabaseTable.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / personalDatabaseTable.getRowHeight();

                if (column == 8) {
                    openUserRatingWindow(row);
                } else if (column == 9) {
                    openUserReviewWindow(row);
                } else if (column == 10){
                    removeRowFromPersonalDatabase(row);
                }
            }
        });

        startDateModel = new SpinnerDateModel();
        endDateModel = new SpinnerDateModel();
        startDateSpinner = new JSpinner(startDateModel);
        endDateSpinner = new JSpinner(endDateModel);
        
        personalDatabaseTable.getColumnModel().getColumn(START_DATE_COLUMN_INDEX)
        .setCellEditor(new DateCellEditor());
        
        personalDatabaseTable.getColumnModel().getColumn(END_DATE_COLUMN_INDEX)
                .setCellEditor(new DateCellEditor());

        personalDatabaseTable.getTableHeader().setReorderingAllowed(false);
        personalDatabaseTable.setRowSorter(new TableRowSorter<>(personalDatabaseTableModel));
        new Sorting(personalDatabaseTable, (TableRowSorter<TableModel>) personalDatabaseTable.getRowSorter());
    }

    private void removeRowFromPersonalDatabase(int row) {
        // Get the book title and author from the row being removed
        String bookTitle = personalDatabaseTableModel.getValueAt(row, 0).toString();
        String bookAuthor = personalDatabaseTableModel.getValueAt(row, 1).toString();
    
        personalDatabaseTableModel.removeRow(row);

        addedBooks.remove(bookTitle + " - " + bookAuthor);
    
        for (int i = 0; i < jt.getRowCount(); i++) {
            String generalBookTitle = jt.getValueAt(i, 0).toString();
            String generalBookAuthor = jt.getValueAt(i, 1).toString();
            // If the book is found, update the button appearance to "Add"
            if (bookTitle.equals(generalBookTitle) && bookAuthor.equals(generalBookAuthor)) {
                // Update button text to "Add"
                jt.setValueAt(Lang.bookAdded, i, column.length - 1); 
                // Update button appearance
                jt.getColumnModel().getColumn(column.length - 1).setCellRenderer(new ButtonRenderer());
                jt.repaint(); // Repaint the table to reflect changes
                break;
            }
        }
    }
    

    // Method to open user rating window
    private void openUserRatingWindow(int row) {
        String selectedTitle = personalDatabaseTableModel.getValueAt(row, 0).toString();
        String selectedAuthor = personalDatabaseTableModel.getValueAt(row, 1).toString();

        new UserRatingWindow(selectedTitle, selectedAuthor);
    }

    // Method to open user review window
    private void openUserReviewWindow(int row) {
        String selectedTitle = personalDatabaseTableModel.getValueAt(row, 0).toString();
        String selectedAuthor = personalDatabaseTableModel.getValueAt(row, 1).toString();
    
        // Check if a review window is already open for the selected book
        for (Window window : Window.getWindows()) {
            if (window instanceof UserReviewWindow) {
                UserReviewWindow reviewWindow = (UserReviewWindow) window;
                if (reviewWindow.getTitle().equals(selectedTitle) && reviewWindow.getTitle().equals(selectedAuthor)) {
                    // If window is already open, bring it to front and return
                    reviewWindow.toFront();
                    return;
                }
            }
        }
    
        // If review window is not open, create a new one
        new UserReviewWindow(selectedTitle, selectedAuthor
        );
    }
    
    
    // Method to add search functionality to personal database
    private void addPersonalDatabaseSearchFunctionality() {
        personalDatabaseSearchField = new JTextField(20);
        JButton searchButton = new JButton(Lang.search);
        Color buttonHeaderColor = new Color(173, 196, 206);
        searchButton.setBackground(buttonHeaderColor);
        searchButton.setForeground(Color.BLACK);
        JPanel searchPanel = new JPanel();

        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(personalDatabaseSearchField);
        searchPanel.add(searchButton);
        personalDatabasePanel.add(searchPanel, BorderLayout.NORTH);

        searchButton.addActionListener(e -> searchPersonalDatabase());
        personalDatabaseSearchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchPersonalDatabase();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchPersonalDatabase();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchPersonalDatabase();
            }
        });
    }

    // Method to search personal database
    private void searchPersonalDatabase() {
        String searchText = personalDatabaseSearchField.getText().trim().toLowerCase();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(personalDatabaseTableModel);
        personalDatabaseTable.setRowSorter(sorter);
        if (searchText.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }

    // Method to show table panel
    private void showTablePanel() {
        if (personalDatabasePanel != null) {
            mainPanel.remove(personalDatabasePanel);
        }
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // Method to show personal database panel
    private void showPersonalDatabasePanel() {
        if (personalDatabasePanel == null) {
            initializePersonalDatabasePanel();
        }
        if (tablePanel != null) {
            mainPanel.remove(tablePanel);
        }
        mainPanel.add(personalDatabasePanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void updateUserReview(String title, String author, String review) {
        for (int row = 0; row < personalDatabaseTableModel.getRowCount(); row++) {
            String bookTitle = personalDatabaseTableModel.getValueAt(row, 0).toString();
            String bookAuthor = personalDatabaseTableModel.getValueAt(row, 1).toString();
            if (bookTitle.equals(title) && bookAuthor.equals(author)) {
                personalDatabaseTableModel.setValueAt(review, row, 9); // Assuming user review column index is 9
                break;
            }
        }
    }
    
}
