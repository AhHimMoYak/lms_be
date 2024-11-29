package click.ahimmoyak.companyservice.auth.exception;

import click.ahimmoyak.companyservice.global.dto.MessageResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidExceptionHandler {
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<MessageResponseDto> handleInvalidPasswordException(InvalidPasswordException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponseDto(e.getMessage()));
    }

}
