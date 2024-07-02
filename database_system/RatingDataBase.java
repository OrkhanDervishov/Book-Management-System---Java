package database_system;

import entities.book.Book;
import entities.other.ControlOpinion;
import entities.rating.Rating;
import entities.review.Review;
import entities.user_and_admin.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import database_system.exceptions.IllegalMemberException;
import program_settings.Parametres;

public class RatingDataBase {

     public static void addRating(User user, String title, String author, double rate){
        Rating.createRating(user.getUsername(), title, author, rate);
        
        File file = new File(Parametres.RATING_PATH + "rating" + Rating.getGeneralIndex() + Parametres.FILE_FORMAT);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            file.createNewFile();

            bw.write(user.getUsername() + " ; " + title + "_" + author + " ; " + rate);
        }catch(IOException e){
            System.out.println("Error while creating a rating");
        }
    }

    // removes review from reviews folder
    public static void removeRating(int ratingIndex){

        ControlOpinion.deleteOpinionFromEntity(ratingIndex, Parametres.USER_RATING_PATH + getRatingAuthor(ratingIndex) + Parametres.FILE_FORMAT);


        File rating_folder = new File(Parametres.RATING_PATH);
        for(File file : rating_folder.listFiles()){
            if(file.getName().equals("rating" + ratingIndex + Parametres.FILE_FORMAT)){
                file.delete();
                return;
            }
        }
    }

    // Finds review in the reviews folder
    public static Rating findRating(int ratingIndex){

        File review_folder = new File(Parametres.REVIEW_PATH);
        for(File file : review_folder.listFiles()){
            if(file.getName().equals("rating" + ratingIndex)){
                return fileToRating(file);
            }
        }
        return null;
    }

    public static int getRatingIndex(String username, String title, String author) throws IllegalMemberException{

        User user = UserDataBase.MainUserList.getMember(username);
        Book book;

        book = BookDataBase.MainBookList.getMember(title, author);
        int userReviewIndexes[] = user.getAllReviews();
        int bookReviewIndexes[] = book.getAllReviews();  

        for(int i : userReviewIndexes){
            for(int j : bookReviewIndexes){
                if(i == j) return i;
            }
        }
        throw new IllegalMemberException("No rating");
    }

    public static boolean ratingExists(String username, String title, String author){

        File folder = new File(Parametres.REVIEW_PATH);
        File[] files = folder.listFiles();

        if(files == null){
            return false;
        }
        for(File file : files){

            Rating r = fileToRating(file);
            if(r.getWriter().equals(username) && r.getBookTo().equals(title) && r.getBookAuthor().equals(author)) return true;
        }

        return false;
    }

    // Converts file data to Review object
    static Rating fileToRating(File file){

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            
            String data[] = br.readLine().split(" ; ", -1);
            String bookData[] = data[1].split("_");

            return Rating.createRating(data[0], bookData[0], bookData[1], Double.parseDouble(data[2]));
        } catch (Exception e) {
        }

        return null;
    }


    // This method will return specific data of review
    private static String getRatingData(int rating_index, int dataIndex){

        try(BufferedReader br = new BufferedReader(new FileReader(Parametres.RATING_PATH + "rating" + rating_index + Parametres.FILE_FORMAT));){

            String line = br.readLine();

            String[] data = line.split(" ; ");

            return  data[dataIndex];
        }catch(IOException ex){
            System.out.println("Reading the file failed");
        }

        return null;
    }

    // Return user by whom review was written
    public static String getRatingAuthor(int rating_index){
        return getRatingData(rating_index, 0);
    }

    // Return book that was reviewed
    public static String[] getRatingBook(int rating_index){
        return getRatingData(rating_index, 1).split("_");
    }

    // Return content of the review
    public static Double getRatingContent(int rating_index){
        return Double.parseDouble(getRatingData(rating_index, 2));
    }
}
