package base.service.user.exceptions;

public class UserExistsException extends Exception{

    public UserExistsException(String message) {
        super(message);
    }
}
