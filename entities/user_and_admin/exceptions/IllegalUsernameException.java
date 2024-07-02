package entities.user_and_admin.exceptions;

public class IllegalUsernameException extends Exception {

    public IllegalUsernameException() {

    }

    public IllegalUsernameException(String message) {
        super(message);
    }
}