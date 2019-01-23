package tj.ebm.Genre.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tj.ebm.Commons.ErrorsUtil.ErrorsUtil;
import tj.ebm.Commons.Result.Result;
import tj.ebm.Genre.Service.GenreService;
import tj.ebm.Genre.dto.GenreDto;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

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
        return Result.ok(genreService.findById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public Result saveNewGenre(@Valid @RequestBody GenreDto dto,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
                    bindingResult.getFieldErrors()), dto);
        }
        System.out.println(dto);
        return Result.ok(genreService.save(dto));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public Result editGenre(@Valid @RequestBody GenreDto dto,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
                    bindingResult.getFieldErrors()), dto);
        }
        return Result.ok(genreService.save(dto));
    }

    @DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result deleteGenre(@PathVariable("id") Long id) {
        return (genreService.deleteFromDb(id))
                ? Result.ok("Genre has been deleted")
                : Result.error("Cannot delete Genre!");
    }

}
