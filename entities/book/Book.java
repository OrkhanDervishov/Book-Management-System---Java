package entities.book;

import database_system.BookDataBase;
import database_system.RatingDataBase;
import database_system.ReviewDataBase;
import database_system.UserDataBase;
import entities.other.ControlOpinion;
import entities.user_and_admin.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import lang_change.Lang;
import program_settings.Parametres;

public class Book extends AbstractWork {

    private Book(String bookTitle, String author) {
        super(bookTitle, author);

        try {
        } catch (Exception e) {
        }
    }

    public static Book readBook(File file) {

        try (BufferedReader br = new BufferedReader(new FileReader(file.getPath()))) {

            String data[] = br.readLine().split(";", -1);

            return new Book(data[0], data[1]);
        } catch (IOException e) {
            System.out.println(e);
        }

        return null;
    }

    // Retruns array of all users who reviewed the book
    public User[] getAllReviewedUsers(){
        int[] reviewIndexes = getAllReviews();

        if(reviewIndexes == null){
            return null;
        }

        User[] users = new User[reviewIndexes.length];

        for(int i = 0; i < reviewIndexes.length; i++){
            users[i] = UserDataBase.MainUserList.getMember(ReviewDataBase.getReviewAuthor(reviewIndexes[i]));
        }

        return users;
    }

    // Returns array of indexes of all reviews of the book
    public int[] getAllReviews(){
        return ControlOpinion.getAllOpinion(Parametres.BOOK_REVIEW_PATH + super.title + Parametres.FILE_FORMAT, getTitle() + "_" + getAuthor());
    }

    public static Book readBook(String title, String author) {

        return new Book(title, author);
    }

    public static Book createBook(String bookTitle, String author) {
        Book book = new Book(bookTitle, author);
        return book;
    }

    public static Book deleteBook(String bookTitle, String author) {
        return BookDataBase.MainBookList.deleteBook(bookTitle, author);
    }

    public String countRatingString(){
        double[] dataRating = countTotalRating();
        return dataRating == null ?  Lang.noRating : dataRating[0] + "(" + dataRating[1] + ")";
    }

    
    // Count total rating of the book
    public double[] countTotalRating(){

        double count = 0;
        int ratings[] = readRatings();

        if(ratings == null) return null;
        
        for(int rating : ratings){
            count += RatingDataBase.getRatingContent(rating);
        }

        double[] data = {count/ratings.length, (double)ratings.length};
        return data;
    }

    // Reads rating data of the book
    private int[] readRatings(){
        try(BufferedReader br = new BufferedReader(new FileReader(Parametres.BOOK_RATING_PATH + getTitle() + "_" + getAuthor() + Parametres.FILE_FORMAT))){

            String ratings[] = br.readLine().split(" ");
            int rating_indexes[] = new int[ratings.length];

            for(int i = 0; i < ratings.length; i++){
                rating_indexes[i] = Integer.parseInt(ratings[i]);
            }

            return rating_indexes;
        }catch(IOException e){

        }

        return null;
    }
}
