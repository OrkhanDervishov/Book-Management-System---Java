package entities.rating;

import entities.other.CheckFile;
import entities.other.UserOpinion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import program_settings.Parametres;

public class Rating extends UserOpinion {

    private int index;
    private static int generalIndex;
    private double rate;

    Rating(String user, String book, String author, double rate) {
        super(user, book, author);
        this.index = ++generalIndex;
        this.rate = rate;
    }

    Rating(String user, String book, String author, double rate, int index) {
        super(user, book, author);
        this.index = index;
        this.rate = rate;
    }

    public static Rating createRating(String username, String title, String author, double rate){
        Rating rating = new Rating(username, title, author, rate);

        if(!CheckFile.check(Parametres.USER_RATING_PATH, username));{

            try {
                new File(Parametres.USER_RATING_PATH + username + Parametres.FILE_FORMAT).createNewFile();
            } catch (IOException e) {
            }
        }
        if(!CheckFile.check(Parametres.BOOK_RATING_PATH, title)){

            try {
                new File(Parametres.BOOK_RATING_PATH + title + "_" + author + Parametres.FILE_FORMAT).createNewFile();
            } catch (IOException e) {
            }
        }

        write(rating, Parametres.USER_RATING_PATH + username + Parametres.FILE_FORMAT);
        write(rating, Parametres.BOOK_RATING_PATH + title + "_" + author + Parametres.FILE_FORMAT);

        return rating;
    }

    private static void write(Rating rating, String path){

        try {

            BufferedReader br = new BufferedReader(new FileReader(path));
            String ratings = br.readLine();

            FileWriter writer = new FileWriter(path);

            if(ratings == null){
                writer.append(rating.getIndex() + " ");
            }
            else{
                writer.append(ratings + rating.getIndex() + " ");
            }
            br.close();
            writer.close(); 
        } catch (IOException e) {
        }
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double newRate) {
        this.rate = newRate;
    }


    public int getIndex(){
        return index;
    }

    public static int getGeneralIndex() {
        return generalIndex;
    }

    public static void setGeneralIndex(int newIndex) {
        if (newIndex >= 0) {
            generalIndex = newIndex;
        }
    }
}
