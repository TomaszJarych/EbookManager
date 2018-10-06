package tj.ebm.Commons.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import tj.ebm.Commons.Result.Result;

@ControllerAdvice(annotations=RestController.class)
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = EntityNotFoundException.class)
	@ResponseBody
	private Result entityNotFoundError() {
		return Result.error("EntityNotFound!!!");
	}
}
