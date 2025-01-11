package click.ahimmoyak.companyservice.auth.exception;

public class InvalidPasswordException extends IllegalArgumentException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
