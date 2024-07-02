/*


 * Created by Orkhan
 * 
 * 
 */

package login_register;

import database_system.UserDataBase;
import database_system.exceptions.IllegalMemberException;
import entities.user_and_admin.Admin;
import lang_change.Lang;
import login_register.login_exceptions.WrongUserException;
import program_settings.Parametres;
import program_settings.Status;

public class Login {
    private static boolean stay_logined = false;

    public static boolean stayLogined() {
        return stay_logined;
    }

    public static void setLogined(boolean b){
        stay_logined = b;
    }

    /*
     * This method is used for calling loginProcess() method,
     * because user can make many attemps while logging in
     * so tryLogin method will can loginProcess() method
     * until user will able to login and it will prevent
     * the chance of occuring StackOverflow error. It also
     * will return true if login process finished successfully.
     * In the future user will able to prevent logging in by finishing
     * the program.
     */

    public static boolean tryLogin(String username, String password)
            throws WrongUserException, IllegalMemberException {

        if (loginProcess(username, password)) {
            return true;
        }

        return false;
    }

    private static boolean loginProcess(String username, String password)
            throws WrongUserException, IllegalMemberException {

        // if user enters username: admin and password: admin he enters program as admin
        if (Admin.login(username, password)) {
            Parametres.setUserStatus(Status.ADMIN);
            return true;

        }
        else if (UserDataBase.MainUserList.checkUserForLogin(username, password)) {

            System.out.println("Success");
            Parametres.setActiveUser(UserDataBase.MainUserList.getMember(username));
            Parametres.setUserStatus(Status.USER);
            return true;

        } else {
            throw new WrongUserException(Lang.wrongUsernameOrPassword);
        }
    }
}