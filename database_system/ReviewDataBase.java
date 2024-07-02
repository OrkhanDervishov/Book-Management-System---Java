package database_system;

import database_system.exceptions.IllegalMemberException;
import entities.book.Book;
import entities.other.ControlOpinion;
import entities.review.Review;
import entities.user_and_admin.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import program_settings.Parametres;

public class ReviewDataBase {

    public static Review addReview(User user, String title, String author, String content){

        if(reviewExists(user.getUsername(), title, author)){
            return null;
        }

        Review review = Review.createReview(user.getUsername(), title, author, content);
        
        File file = new File(Parametres.REVIEW_PATH + "review" + Review.getGeneralIndex() + Parametres.FILE_FORMAT);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            file.createNewFile();

            bw.write(user.getUsername() + " ; " + title + "_" + author + " ; " + content);
        }catch(IOException e){
            System.out.println("Error while creating a review");
        }

        return review;
    }

    // removes review from reviews folder
    public static void removeReview(int reviewIndex){

        ControlOpinion.deleteOpinionFromEntity(reviewIndex, Parametres.USER_REVIEW_PATH + getReviewAuthor(reviewIndex) + Parametres.FILE_FORMAT);
        ControlOpinion.deleteOpinionFromEntity(reviewIndex, Parametres.BOOK_REVIEW_PATH + getReviewBook(reviewIndex) + Parametres.FILE_FORMAT);

        File review_folder = new File(Parametres.REVIEW_PATH);
        for(File file : review_folder.listFiles()){
            if(file.getName().equals("review" + reviewIndex + Parametres.FILE_FORMAT)){

                file.delete();
                return;
            }
        }
    }

    // Finds review in the reviews folder
    public static Review findReview(int reviewIndex){

        File review_folder = new File(Parametres.REVIEW_PATH);
        for(File file : review_folder.listFiles()){
            if(file.getName().equals("review" + reviewIndex)){
                return fileToReview(file);
            }
        }
        return null;
    }

    // For personal database
    public static int getReviewIndex(String username, String title, String author) throws IllegalMemberException{

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
        throw new IllegalMemberException("No review");
    }

    public static boolean reviewExists(String username, String title, String author){

        File folder = new File(Parametres.REVIEW_PATH);
        File[] files = folder.listFiles();

        if(files == null){
            return false;
        }
        for(File file : files){

            Review r = fileToReview(file);
            if(r.getWriter().equals(username) && r.getBookTo().equals(title) && r.getBookAuthor().equals(author)) return true;
        }

        return false;
    }

    // Converts file data to Review object
    static Review fileToReview(File file){

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            
            String data[] = br.readLine().split(" ; ", -1);
            int index = Integer.parseInt(file.getName().split("review")[1].split(".txt")[0]);
            String bookData[] = data[1].split("_");

            return Review.readReview(data[0], bookData[0], bookData[1], data[2], index);
        } catch (IOException e) {
            System.out.println("Review file does not exists");
        }

        return null;
    }


    // This method will return specific data of review
    private static String getReviewData(int review_index, int dataIndex){

        try(BufferedReader br = new BufferedReader(new FileReader(Parametres.REVIEW_PATH + "review" + review_index + Parametres.FILE_FORMAT));){

            String line = br.readLine();
            String[] data = line.split(" ; ", -1);
            return  data[dataIndex];
        }catch(IOException ex){
        }
        return null;
    }


    // Return user by whom review was written
    public static String getReviewAuthor(int review_index){
        return getReviewData(review_index, 0);
    }

    // Return book(Title and author) that was reviewed
    public static String[] getReviewBook(int review_index){
        return getReviewData(review_index, 1).split("_");
    }

    // Return content of the review
    public static String getReviewContent(int review_index){
        return getReviewData(review_index, 2);
    }
}
