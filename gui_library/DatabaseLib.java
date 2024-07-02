package gui_library;

import app_runner.*;
import database_system.BookDataBase;
import database_system.exceptions.IllegalMemberException;
import entities.book.Book;
import entities.user_and_admin.User;
import gui_elements.Actions;
import gui_log_reg.LoginFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import lang_change.Lang;

public class DatabaseLib extends Actions implements WindowListener {
    // File name to save data
    private static final String FILE_NAME = "./brodsky.csv";
    protected Sorting sortingGeneral; // Change the type of sorting field

    // Index constants for columns
    public static final int TITLE_COLUMN_INDEX = 0;
    public static final int AUTHOR_COLUMN_INDEX = 1;
    public static final int RATING_COLUMN_INDEX = 2;
    static final int REVIEW_COLUMN_INDEX = 3;

    // Table's book list
    ArrayList<Object[]> dataRows = new ArrayList<>();

    protected JFrame jf;
    protected JPanel mainPanel;
    public JPanel tablePanel;
    protected JScrollPane js;
    public JTable jt;
    private JTextField searchField;
    protected String[] column;
    protected Object[][] data;
    public DefaultTableModel model;

    public DatabaseLib() {
        SwingUtilities.invokeLater(this::initializeGUI);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // Saved data before closing the window
        SaveData.save();
        System.exit(0);
    }

    private void initializeGUI() {
        // Initialized GUI components
        jf = new JFrame(Lang.tableTitle);
        jf.setPreferredSize(new Dimension(1150, 650));
        mainPanel = new JPanel(new BorderLayout());
        tablePanel = new JPanel(new BorderLayout());

        // It will track the changes in the window
        jf.addWindowListener(this);

        // Loaded data and initialized table
        Object[][] headersAndData = getDataAndHeaders();
        if (headersAndData != null) {
            initializeTable(headersAndData);
            addReviewColumnMouseListener();
            customizeTableAppearance();
            sorting(); // Add this line to set up table sorting
        } else {
            JOptionPane.showMessageDialog(null, Lang.loadFailed, Lang.error, JOptionPane.ERROR_MESSAGE);
        }

        // Added components to main panel
        mainPanel.add(tablePanel);
        addLeftPanels();
        jf.add(mainPanel);
        propertyJFrame();
        addSearchFunctionality();

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void sorting() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        jt.setRowSorter(sorter);
        sortingGeneral = new Sorting(jt, sorter);
    }

    protected void initializeTable(Object[][] headersAndData) {
        // Initialized table with data
        column = (String[]) headersAndData[0];
        data = new Object[headersAndData.length - 1][column.length];
        for (int i = 1; i < headersAndData.length; i++) {
            data[i - 1] = headersAndData[i];
        }

        model = new DefaultTableModel(data, column);

        jt = new JTable(model);
        jt.getTableHeader().setReorderingAllowed(false);
        js = new JScrollPane(jt);
        tablePanel.add(js, BorderLayout.CENTER);
    }

    protected void addSearchFunctionality() {
        // Added search functionality to the table
        searchField = new JTextField(20);
        JButton searchButton = new JButton(Lang.search);
        Color buttonHeaderColor = new Color(173, 196, 206);
        searchButton.setBackground(buttonHeaderColor);
        searchButton.setForeground(Color.BLACK);
        JPanel searchPanel = new JPanel();

        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        tablePanel.add(searchPanel, BorderLayout.NORTH);

        searchButton.addActionListener(e -> search());
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search();
            }
        });
    }

    private void search() {
        // Performed search based on entered text
        String searchText = searchField.getText().trim().toLowerCase();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        jt.setRowSorter(sorter);
        if (searchText.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }

    private void addReviewColumnMouseListener() {
        // Added mouse listener to review column for specific action
        jt.addMouseListener(new ReviewColumnMouseListener(jt, REVIEW_COLUMN_INDEX));
    }

    private void propertyJFrame() {
        // Set properties of JFrame
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jf.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

    private void customizeTableAppearance() {
        // Customized table appearance
        jt.getTableHeader().setForeground(Color.BLACK);
        jt.getTableHeader().setBackground(Color.decode("#ADC4CE"));
        jt.getTableHeader().setPreferredSize(new Dimension(jt.getTableHeader().getWidth(), 40));
        jt.setBackground(Color.WHITE);
        jt.setForeground(Color.BLACK);
        jt.setSelectionBackground(Color.decode("#F1F0E8"));
        jt.setSelectionForeground(Color.BLACK);

        TableColumnModel columnModel = jt.getTableHeader().getColumnModel();
        columnModel.getColumn(TITLE_COLUMN_INDEX).setPreferredWidth(200);
        columnModel.getColumn(AUTHOR_COLUMN_INDEX).setPreferredWidth(230);
        columnModel.getColumn(RATING_COLUMN_INDEX).setPreferredWidth(130);
        columnModel.getColumn(REVIEW_COLUMN_INDEX).setPreferredWidth(170);

        jt.setRowHeight(30);
        jt.getTableHeader().setResizingAllowed(false);
    }

    protected Object[][] getDataAndHeaders() {
        // Read data and headers from file
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String header = br.readLine();
            String[] headers = readHeaders(header);
            ArrayList<Object[]> dataRows = readDataRows(br);
            return assembleResult(headers, dataRows);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, Lang.readingFailed, Lang.error, JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    protected String[] readHeaders(String header) {
        // Read and modify headers
        if (header != null) {
            String[] headers = header.split(",", -1);
            String[] finalHeaders = new String[headers.length + 2];
            finalHeaders[0] = "<html><b>" + Lang.bookTitle + "</b></html>";
            finalHeaders[1] = "<html><b>" + Lang.bookAuthor + "</b></html>";
            finalHeaders[headers.length] = "<html><b>" + Lang.bookRating + "</b></html>";
            finalHeaders[headers.length + 1] = "<html><b>" + Lang.bookReviews + "</b></html>";
            return finalHeaders;
        }
        return null;
    }

    private ArrayList<Object[]> readDataRows(BufferedReader br) throws IOException {
        // Read data rows from file
        addAllBooks(dataRows);
        return dataRows;
    }

    private Object[][] assembleResult(String[] headers, ArrayList<Object[]> dataRows) {
        // Assemble data and headers
        if (headers != null && !dataRows.isEmpty()) {
            Object[][] result = new Object[dataRows.size() + 1][headers.length];
            result[0] = headers;
            for (int i = 0; i < dataRows.size(); i++) {
                result[i + 1] = dataRows.get(i);
            }
            return result;
        } else {
            return null;
        }
    }

    // Add all books from BookDatabase
    private void addAllBooks(ArrayList<Object[]> dataRows) {
        for (int i = 0; i < BookDataBase.MainBookList.size(); i++) {
            addToList(dataRows, BookDataBase.MainBookList.returnData(i));
        }
    }

    public void addBookToList(Book book) {
        addToList(dataRows, BookDataBase.MainBookList.returnData(book));
    }

    private void addToList(ArrayList<Object[]> dataRows, String[] data) {

        Book book = null;
        User[] users = null;
        try {
            book = BookDataBase.MainBookList.getMember(data[0], data[1]);
            users = book.getAllReviewedUsers();       
        } catch (IllegalMemberException e) {
        }

        Object[] dataRow = new Object[] { data[0], data[1], book.countRatingString(), users == null ? Lang.noReviews : users[0] + "," + users[1] + "," + users[2]};
        dataRows.add(dataRow);
    }

    public void addLeftPanels() {
        // Added left panel components
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JButton tableButton = new JButton(Lang.generalDatabase);

        int buttonWidth = 250;
        int buttonHeight = 30;

        tableButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight));

        leftPanel.add(tableButton);

        Color buttonColor = new Color(150, 182, 197);
        tableButton.setBackground(buttonColor);
        tableButton.setForeground(Color.BLACK);

        tableButton.addActionListener(e -> {
            mainPanel.add(tablePanel, BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
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
}
