/*


 * Created by Orkhan
 * 
 * 
 */

package database_system;

import database_system.exceptions.IllegalMemberException;
import entities.user_and_admin.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// This class is used for holding User oblects
public class UserDataBase extends AbstractDataBase<User>{

    public static UserDataBase MainUserList = new UserDataBase();

    public UserDataBase(){
        super();
    }

    public void loadData() {

        try (BufferedReader br = new BufferedReader(new FileReader("./data/user_list.csv"))) {

            String line;
            while ((line = br.readLine()) != null) {
                String data[] = line.split(";");
                super.list.add(User.readUser(data[0], Integer.parseInt(data[1])));

                super.nameList.add(data[0]);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void writeData() {

        new File("./data/user_list.csv").delete();

        try {
            File newFile = new File("./data/user_list.csv");
            newFile.createNewFile();

            BufferedWriter bw = new BufferedWriter(new FileWriter("./data/user_list.csv"));

            for (int i = 0; i < nameList.size(); i++) {
                User u = super.list.get(i);
                bw.write(u.getUsername() + ";" + u.getPassword());
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // private static void listFiles() {

    // UserDataFile userFolder = new UserDataFile("../data/users");

    // // Add all files form users folder to the map
    // for (UserDataFile file : userFolder.listFiles()) {
    // userMap.put(file.getName(), file);
    // }
    // }

    public void add(User user) throws IllegalMemberException {

        if (contains(user.getUsername())) {
            throw new IllegalMemberException("User with username " + user.getUsername() + " already exists");
        }

        super.add(user);
        super.nameList.add(user.getUsername());
    }

    // public User search(String username) {
    // if (user_map.get(username) == null) {
    // return false;
    // }

    // return true;
    // }

    // returns true if user with username "some username"
    // exits in the user_map
    // public static boolean isInMap(String username) {
    // return usernameList.contains(username) ? true : false;
    // }

    // // This method will return user
    // public static User getMember(String username) {

    // return userList.get(usernameList.indexOf(username));
    // }

    /*
     * This method checks user for login class.
     * It first will check if user with entered username exists.
     * If user with this username does not exist it will suggest user to register.
     * But, if user with endered username exists, it will check if entered password
     * for this username is correct. If yes user Successfully logined, else
     * it will throw WrongPasswordException.
     */
    public boolean checkUserForLogin(String username, String password) {
        if (!super.nameList.contains(username)) {
            System.out.println("There is no such user. You can register");
            return false;
        }

        else if (super.list.get(super.nameList.indexOf(username)).getPassword() == password.hashCode()) {
            return true;
        }

        return false;
    }

    // This method will return user
    public User getMember(String name) {
    
        return super.list.get(super.nameList.indexOf(name));
    }

    // This method removes user from user_list by its username
    public void remove(String name) {
        if (contains(name)) {
    
            super.list.remove(super.list.get(super.nameList.indexOf(name)));
        }
    
        System.out.println("There is no such object in the list");
    }
    
    public boolean contains(String name) {
        return super.nameList.contains(name) ? true : false;
    }
}