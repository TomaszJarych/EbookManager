package tj.ebm.Bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tj.ebm.Bookstore.Service.BookstoreService;
import tj.ebm.Bookstore.dto.BookstoreDto;
import tj.ebm.Commons.ErrorsUtil.ErrorsUtil;
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

	@PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
	public Result addNewBookstore(@Valid @RequestBody BookstoreDto dto,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
						bindingResult.getFieldErrors()), dto);
			}
			return Result.ok(bookstoreService.save(dto));

		} catch (EntityNotFoundException e) {
			return Result.error("Entity nof found!");
		}
	}
	@PutMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
	public Result editBookstore(@Valid @RequestBody BookstoreDto dto,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
						bindingResult.getFieldErrors()), dto);
			}
			return Result.ok(bookstoreService.save(dto));

		} catch (EntityNotFoundException e) {
			return Result.error("Entity nof found!");
		}
	}

	@DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result deleteBookstore(@PathVariable("id") Long id) {
		return (bookstoreService.deleteFromDb(id))
				? Result.ok("Bookstore has been deleted")
				: Result.error("Cannont delete given Bookstore");
	}

}
