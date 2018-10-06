package tj.ebm.Genre.Controller;

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

import tj.ebm.Commons.ErrorsUtil.ErrorsUtil;
import tj.ebm.Commons.Result.Result;
import tj.ebm.Genre.Service.GenreService;
import tj.ebm.Genre.dto.GenreDto;

@RestController
@RequestMapping(path = "/genre")
public class GenreController {

	private final GenreService genreService;

	@Autowired
	public GenreController(GenreService genreService) {
		this.genreService = genreService;
	}

	@GetMapping(path = "/all", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result getAllGenres() {
		return Result.ok(genreService.getAll());
	}

	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result getGenreById(@PathVariable("id") Long id) {
		try {
			return Result.ok(genreService.findById(id));
		} catch (EntityNotFoundException e) {
			return Result.error("Entity not found");
		}
	}

	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public Result saveNewGenre(@Valid @RequestBody GenreDto dto,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
						bindingResult.getFieldErrors()), dto);
			}
			return Result.ok(genreService.save(dto));
		} catch (Exception e) {
			return Result.error("Cannot save Genre");
		}
	}

	@PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public Result editGenre(@Valid @RequestBody GenreDto dto,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
						bindingResult.getFieldErrors()), dto);
			}
			return Result.ok(genreService.save(dto));
		} catch (Exception e) {
			return Result.error("Cannot save Genre");
		}
	}

	@DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result deleteGenre(@PathVariable("id") Long id) {
		return (genreService.deleteFromDb(id))
				? Result.ok("Genre has been deleted")
				: Result.error("Cannot delete Genre!");
	}

}
