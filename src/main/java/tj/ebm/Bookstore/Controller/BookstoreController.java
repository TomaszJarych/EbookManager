package tj.ebm.Bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tj.ebm.Bookstore.Service.BookstoreService;
import tj.ebm.Commons.Result.Result;

@RestController
@RequestMapping(path = "/bookstore")
public class BookstoreController {

	private final BookstoreService bookstoreService;

	@Autowired
	public BookstoreController(BookstoreService bookstoreService) {
		this.bookstoreService = bookstoreService;
	}

	@GetMapping(path = "/all", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result getAllBookstores() {
		return Result.ok(bookstoreService.getAll());
	}

	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result getBookstoreById(@PathVariable("id") Long id) {
		return Result.ok(bookstoreService.findById(id));
	}

}
