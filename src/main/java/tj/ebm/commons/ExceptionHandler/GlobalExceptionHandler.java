package tj.ebm.commons.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tj.ebm.commons.Result.Result;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice(annotations = RestController.class)
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private Result entityNotFoundError() {
        return Result.error("EntityNotFound!");
    }

    @ExceptionHandler(value = MailException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Result MailExceptionError() {
        return Result.error("Cannont send the email. Please try again later");
    }

}
