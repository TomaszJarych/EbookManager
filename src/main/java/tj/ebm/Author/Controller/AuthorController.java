package tj.ebm.Author.Controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tj.ebm.Author.Service.AuthorService;
import tj.ebm.Author.dto.AuthorDto;
import tj.ebm.Commons.ErrorsUtil.ErrorsUtil;
import tj.ebm.Commons.Result.Result;

@RestController
@RequestMapping(path = "/author")
public class AuthorController {

	private final AuthorService authorService;

	@Autowired
	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@GetMapping(path = "/all", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result getAllAuthors() {
		return Result.ok(authorService.getAll());
	}

	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result getAuthorById(@PathVariable("id") Long id) {
		return Result.ok(authorService.findById(id));

	}

	@PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
	public Result saveNewAuthor(@Valid @RequestBody AuthorDto dto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
					bindingResult.getFieldErrors()), dto);
		}
		return Result.ok(authorService.save(dto));
	}

	@PutMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
	public Result editAuthor(@Valid @RequestBody AuthorDto dto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
					bindingResult.getFieldErrors()), dto);
		}
		return Result.ok(authorService.save(dto));
	}

	@DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result deleteAuthor(@PathVariable("id") Long id) {
		return (authorService.deleteFromDb(id))
				? Result.ok("Author has been deleted")
				: Result.error("Cannot delete author");
	}

}
