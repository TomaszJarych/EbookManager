package tj.ebm.Book.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tj.ebm.Book.Service.BookService;
import tj.ebm.Book.dto.BookDto;
import tj.ebm.Commons.ErrorsUtil.ErrorsUtil;
import tj.ebm.Commons.Result.Result;

@RestController
@RequestMapping("/book")
public class BookController {

	private final BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping(path = "/all", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result getAllBooks() {
		return Result.ok(bookService.getAll());
	}

	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result getBookById(@PathVariable("id") Long id) {
		return Result.ok(bookService.findById(id));
	}
	
	@GetMapping(path="/booksByAuthorsId/{id}")
	public Result getBooksByAuthorsId(@PathVariable("id")Long id) {
		return Result.ok(bookService.findAllBooksByAuthorsId(id));
	}

	@PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
	public Result addNewBook(@Valid @RequestBody BookDto dto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
					bindingResult.getFieldErrors()), dto);
		}
		return Result.ok(bookService.save(dto));
	}

	@PutMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
	public Result editBook(@Valid @RequestBody BookDto dto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
					bindingResult.getFieldErrors()), dto);
		}
		return Result.ok(bookService.save(dto));
	}

	@DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result deleteBook(@PathVariable("id") Long id) {
		return (bookService.deleteFromDb(id))
				? Result.ok("Book has been deleted")
				: Result.error("Cannont delete book");
	}

}
