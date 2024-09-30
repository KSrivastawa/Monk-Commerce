package monk.commerce.coupons.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyError> handleCustomException(Exception e) {
        MyError myError = new MyError();
        myError.setErrorMessage(e.getMessage());
        myError.setStatus("FAILED");

        return new ResponseEntity<>(myError, HttpStatus.BAD_REQUEST);
    }

    // Several Other Runtime Exception can be handled into custom exception and respond as per required message changes.

}
