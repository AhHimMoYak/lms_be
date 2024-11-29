package click.ahimmoyak.institutionservice.auth.exception;

public class InvalidPasswordException extends IllegalArgumentException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
