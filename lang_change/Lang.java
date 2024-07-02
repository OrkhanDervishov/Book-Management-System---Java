package lang_change;

import java.io.BufferedReader;
import java.io.FileReader;

public class Lang {

    // This will contain path of language file
    static String readDataFrom;

    // They will contain words in different languages
    
    // Login Register Page
    public static String loginTitle;
    public static String registerTitle;
    public static String usernameLabel;
    public static String passwordLabel;
    public static String repeatPasswordLabel;
    public static String haveAccount;
    public static String dontHaveAccount;
    public static String registerHere;
    public static String loginHere;

    // Login Register Exceptions
    public static String wrongUsernameOrPassword;
    public static String existingUsername;
    public static String passwordsUnmatch;
    public static String illegalPasswordLength;
    public static String illegalUsernameLength;

    // Table Page
    public static String tableTitle;
    public static String tableTitleAdmin;

    // General Database
    public static String generalDatabase;
    public static String bookTitle;
    public static String bookAuthor;
    public static String bookRating;
    public static String bookReviews;
    public static String noReviews;
    public static String noRating;

    //Personal Database
    public static String personalDatabase;
    public static String bookStatus;
    public static String bookSpentTime;
    public static String bookStartDate;
    public static String bookEndDate;
    public static String userReview;
    public static String userRating;
    public static String notStarted;
    public static String bookAlreadyAdded;
    
    // Database functionalities
    public static String bookAdd;
    public static String bookAdded;
    public static String removeBookFromPersonal;
    public static String addBook;
    public static String addedBook;
    public static String addReview;
    public static String addRating;
    public static String search;

    // Admin
    public static String editBook;
    public static String addNewBook;
    public static String deleteBook;
    public static String deleteUser;
    public static String removeReview;
    public static String adminIsReserved;
    public static String operation;
    public static String confirm;

    // File errors
    public static String loadFailed;
    public static String readingFailed;
    public static String error;

    // User
    public static String logOut;
    public static String removeBook;
    public static String bookDetails;

    public static String selectUser;

    public static String SelectUserToDelete;

    public static String selectBookToDelete;
    
    protected static void change(Language lang){

        switch (lang) {
            case Language.AZE:
                readDataFrom = "./lang_change/azerbaijani.csv";
                break;
            case Language.ENG:
                readDataFrom = "./lang_change/english.csv";
                break;
            default:
                readDataFrom = "./lang_change/english.csv";
        }

        
        // Reading data and assigning strings
        try(BufferedReader br = new BufferedReader(new FileReader(readDataFrom))){
            
            // Login Register Page
            loginTitle = br.readLine();
            registerTitle = br.readLine();
            usernameLabel = br.readLine();
            passwordLabel = br.readLine();
            repeatPasswordLabel = br.readLine();
            haveAccount = br.readLine();
            dontHaveAccount = br.readLine();
            registerHere = br.readLine();
            loginHere = br.readLine();

            br.readLine();

            // Login Register Exceptions
            wrongUsernameOrPassword = br.readLine();
            existingUsername = br.readLine();
            passwordsUnmatch = br.readLine();
            illegalPasswordLength = br.readLine();
            illegalUsernameLength = br.readLine();

            br.readLine();

            // Table page
            tableTitle = br.readLine();
            tableTitleAdmin = br.readLine();

            br.readLine();

            // General Database
            generalDatabase = br.readLine();
            bookTitle = br.readLine();
            bookAuthor = br.readLine();
            bookRating = br.readLine();
            bookReviews = br.readLine();
            noReviews = br.readLine();
            noRating = br.readLine();

            br.readLine();

            // Personal Database
            personalDatabase = br.readLine();
            bookStatus = br.readLine();
            bookSpentTime = br.readLine();
            bookStartDate = br.readLine();
            bookEndDate = br.readLine();
            userReview = br.readLine();
            userRating = br.readLine();
            notStarted = br.readLine();
            bookAlreadyAdded = br.readLine();

            br.readLine();

            // Database functionalities
            bookAdd = br.readLine();
            bookAdded = br.readLine();
            removeBookFromPersonal = br.readLine();
            addBook = br.readLine();
            addedBook = br.readLine();
            addReview = br.readLine();
            addRating = br.readLine();
            search = br.readLine();

            br.readLine();
            
            // Admin
            editBook = br.readLine();
            addNewBook = br.readLine();
            deleteBook = br.readLine();
            deleteUser = br.readLine();
            removeReview = br.readLine();
            adminIsReserved = br.readLine();
            operation = br.readLine();
            confirm = br.readLine();

            br.readLine();

            // File errors
            loadFailed = br.readLine();
            readingFailed = br.readLine();
            error = br.readLine();

            br.readLine();

            // User
            logOut = br.readLine();
            selectUser = br.readLine();
            removeBook = br.readLine();
            bookDetails = br.readLine();
            SelectUserToDelete = br.readLine();
            selectBookToDelete = br.readLine();


        } catch (Exception e) {
        }
    }
}
