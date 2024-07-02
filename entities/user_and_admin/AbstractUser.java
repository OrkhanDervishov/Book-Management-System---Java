/*


 * Created by Orkhan
 * 
 * 
 */

package entities.user_and_admin;

import entities.user_and_admin.exceptions.IllegalPasswordException;
import entities.user_and_admin.exceptions.IllegalUsernameException;
import java.util.regex.*;

abstract class AbstractUser {
    private String username;
    private int password;

    // UserInterface() constructor does not handles exception, because if any
    // exception will be thrown, user will not be created.
    protected AbstractUser(String username, String password) throws IllegalUsernameException, IllegalPasswordException {
        setUsername(username);
        setPassword(password);
    }

    protected AbstractUser(String username, int hashedPassword) {
        this.username = username;
        this.password = hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws IllegalUsernameException {
        if (username.length() >= 2 && username.length() <= 30 && testUsername(username)) {
            this.username = username;
            return;
        }
        throw new IllegalUsernameException(
                username + " does not fit. Username must be longer than 2 and lesser than 30 characters!");
    }

    // for now username can only contain english letters, and testUsername() method
    // checks if it only contains english letters.
    private boolean testUsername(String username) {
        String regex = "^[A-Za-z]\\w{1,29}$";

        Pattern p = Pattern.compile(regex);

        if (username == null) {
            return false;
        }

        Matcher m = p.matcher(username);

        return m.matches();
    }

    public int getPassword() {
        return password;
    }

    // password only has setter method, as user only can change password not see it.
    public void setPassword(String password) throws IllegalPasswordException {
        if (password.length() >= 8 && password.length() <= 32) {
            this.password = password.hashCode();
            return;
        }
        throw new IllegalPasswordException(
                "Password must be equal or longer than 8 letters and lesser than 32 letters!");
    }

    abstract public String toString();
}