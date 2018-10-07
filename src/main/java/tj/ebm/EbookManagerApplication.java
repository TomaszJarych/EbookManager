package tj.ebm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EbookManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbookManagerApplication.class, args);
	}

	// TODO excepion Hanlder - dla EntityNotFoundException!
	// @ControllerAdvice
	// public class RestResponseEntityExceptionHandler
	// extends ResponseEntityExceptionHandler {
	//
	// @ExceptionHandler(value
	// = { IllegalArgumentException.class, IllegalStateException.class })
	// protected ResponseEntity<Object> handleConflict(
	// RuntimeException ex, WebRequest request) {
	// String bodyOfResponse = "This should be application specific";
	// return handleExceptionInternal(ex, bodyOfResponse,
	// new HttpHeaders(), HttpStatus.CONFLICT, request);
	// }
	// }

}
