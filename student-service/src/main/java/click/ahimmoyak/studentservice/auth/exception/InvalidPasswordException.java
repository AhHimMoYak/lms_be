package click.ahimmoyak.studentservice.auth.exception;

public class InvalidPasswordException extends IllegalArgumentException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
