package entities.user_and_admin.exceptions;

public class IllegalPasswordException extends Exception {

    public IllegalPasswordException() {

    }

    public IllegalPasswordException(String message) {
        super(message);
    }
}