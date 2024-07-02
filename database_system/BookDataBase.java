package database_system;

import database_system.exceptions.IllegalMemberException;
import entities.book.Book;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import program_settings.Parametres;

public class BookDataBase extends AbstractDataBase<Book> {

    public static BookDataBase MainBookList = new BookDataBase();

    public BookDataBase() {
        super();
    }

    public void loadData() {

        try (BufferedReader br = new BufferedReader(new FileReader("./data/book_list.csv"))) {

            String line;
            while ((line = br.readLine()) != null) {
                String data[] = line.split(";");
                super.list.add(Book.readBook(data[0], data[1]));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void writeData() {

        new File("./data/book_list.csv").delete();

        try {
            File newFile = new File("./data/book_list.csv");
            newFile.createNewFile();

            BufferedWriter bw = new BufferedWriter(new FileWriter("./data/book_list.csv"));

            for (int i = 0; i < super.list.size(); i++) {
                Book b = super.list.get(i);
                bw.write(b.getTitle() + ";" + b.getAuthor());
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // Add book to the database
    public void add(Book book) throws IllegalMemberException {

        for(Book b : super.list){
            if(b.getTitle().equals(book.getTitle()) && b.getAuthor().equals(book.getAuthor())){
                throw new IllegalMemberException("Book already exists");
            }
        }
        super.add(book);
    }

    // Returns some data of the book by index from the database
    public String[] returnData(int index) {

        Book book = MainBookList.getMemberByIndex(index);
        String data[] = { book.getTitle(), book.getAuthor() };
        return data;
    }

    public String[] returnData(Book book) {

        String data[] = { book.getTitle(), book.getAuthor() };
        return data;
    }

    // Checks if book with following title and author exists
    public boolean contains(String title, String author){

        for(Book book : list){
            if(book.getTitle().equals(title) && book.getAuthor().equals(author)){
                return true;
            }
        }
        return false;
    }

    // Return Book object that has following parameters
    public Book getMember(String title, String author) throws IllegalMemberException{

        for(Book book : list){
            if(book.getTitle().equals(title) && book.getAuthor().equals(author)){
                return book;
            }
        }
        
        throw new IllegalMemberException("There is no such book in the database");
    }

    // Deletes book from database
    public Book deleteBook(String bookTitle, String author) {
        for (int i = 0; i < list.size(); i++) {
            Book book = list.get(i);
            if (book.getTitle().equals(bookTitle) && book.getAuthor().equals(author)) {
                list.remove(i);
                deleteBookFiles(bookTitle, author);
                writeData();
                return book;
            }
        }
        return null;
    }

    // deletes all files of the book
    private void deleteBookFiles(String title, String author){

        try {
            File file = new File(Parametres.BOOK_REVIEW_PATH + title + "_" + author + Parametres.FILE_FORMAT);

        } catch (Exception e) {
        }
    }

    // Edits some parameters of the book
    public Book editBook(String originalTitle, String originalAuthor, String newTitle, String newAuthor) {
        Book bookToEdit = null;
        for (Book book : list) {
            if (book.getTitle().equals(originalTitle) && book.getAuthor().equals(originalAuthor)) {
                bookToEdit = book;
                break;
            }
        }

        if (bookToEdit != null) {
            bookToEdit.setTitle(newTitle);
            bookToEdit.setAuthor(newAuthor);
            writeData();
        }
        return bookToEdit;
    }

}