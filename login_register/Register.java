/*


 * Created by Orkhan
 * 
 * 
 */

package login_register;

import database_system.UserDataBase;
import database_system.exceptions.IllegalMemberException;
import entities.user_and_admin.User;
import entities.user_and_admin.exceptions.*;
import lang_change.Lang;
;

public class Register {

    /*
     * tryRegister() method works completely same as tryLogin()
     * method, except that it will require 2 passwords.
     */
    public static boolean tryRegister(String username, String password, String password2)
            throws IllegalPasswordException, IllegalUsernameException, IllegalMemberException {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {

        }

        if (!registerProcess(username, password, password2)) {
            return false;
        }

        return true;
    }

    private static boolean registerProcess(String username, String password, String password2)
            throws IllegalPasswordException, IllegalUsernameException, IllegalMemberException {

        if (!password.equals(password2)) {
            throw new IllegalPasswordException(Lang.passwordsUnmatch);
        }

        if (username.toLowerCase().equals("admin")) {
            throw new IllegalUsernameException(Lang.adminIsReserved);
        }

        if (!UserDataBase.MainUserList.contains(username)) {
            User u = User.createUser(username, password2);
            if (u != null) {
                UserDataBase.MainUserList.add(u);
                System.out.println("Registered");
                return true;
            }
            return false;
        }

        throw new IllegalUsernameException(Lang.existingUsername);
    }
}