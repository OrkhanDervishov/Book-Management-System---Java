/*
 *
 * 
 * Created by Orkhan
 * 
 * 
 */

package entities.user_and_admin;

import entities.other.ControlOpinion;
import entities.user_and_admin.exceptions.IllegalPasswordException;
import entities.user_and_admin.exceptions.IllegalUsernameException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import program_settings.Parametres;

public class User extends AbstractUser {

    private User(String username, String password) throws IllegalUsernameException, IllegalPasswordException {
        super(username, password);
    }

    private User(String username, int hashedPassword) {
        super(username, hashedPassword);
    }

    // Searches User's data by username if there is no such file throws
    // IllegalMemberException

    public static User readUser(String username, int password) {

        return new User(username, password);

        // try (BufferedReader br = new BufferedReader(
        // new FileReader(Parametres.USER_PATH + username + Parametres.FILE_FORMAT));) {

        // String data[] = br.readLine().split(";", -1);

        // return new User(data[0], data[1].hashCode());
        // } catch (IOException e) {
        // System.out.println(e);
        // }

        // return null;
        // throw new IllegalMemberException("No such member in the list");
    }

    public static User readUser(File file) {

        try (BufferedReader br = new BufferedReader(new FileReader(file.getPath()));) {

            String data[] = new String[2];
            data = br.readLine().split(";", -1);

            return new User(data[0], data[1].hashCode());
        } catch (IOException e) {
            System.out.println(e);
        }

        return null;
    }

    // User instance can only be created by createUser() method.
    public static User createUser(String username, String password) {
        try {
            return new User(username, password);

        } catch (IllegalUsernameException | IllegalPasswordException e) {
            System.out.println(e);
        }
        return null;
    }

    public String toString() {
        return "[ username: " + super.getUsername() + " ]";
    }

    public int[] getAllReviews(){
        return ControlOpinion.getAllOpinion(Parametres.USER_REVIEW_PATH + getUsername() + Parametres.FILE_FORMAT, getUsername());
    }

    public int[] getAllRatings(){
        return ControlOpinion.getAllOpinion(Parametres.USER_RATING_PATH + getUsername() + Parametres.FILE_FORMAT, getUsername());
    }
}