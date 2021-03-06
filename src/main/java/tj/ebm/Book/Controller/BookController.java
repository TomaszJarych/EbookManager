package tj.ebm.Book.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tj.ebm.Book.Service.BookService;
import tj.ebm.Book.dto.BookDto;
import tj.ebm.Genre.dto.GenreDto;
import tj.ebm.commons.ErrorsUtil.ErrorsUtil;
import tj.ebm.commons.Result.Result;
import tj.ebm.commons.SessionStorageData.SessionStorageData;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final SessionStorageData sessionData;

    @Autowired
    public BookController(BookService bookService,
                          SessionStorageData sessionData) {
        this.bookService = bookService;
        this.sessionData = sessionData;
    }

    @GetMapping(path = "/all", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result getAllBooks() {
        return Result.ok(bookService.getAll());
    }

    @GetMapping(path = "/allByOwner", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result getAllBooksByOwnerId() {
        if (sessionData.getLoggedUser().getId() == null) {
            return Result.error("There is no logged in User");
        }
        return Result.ok(bookService
                .findAllBooksByOwnerId(sessionData.getLoggedUser().getId()));
    }

    @GetMapping(path = "/searchBooksByInput/{input}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result getAllBooksByTitleContains(
            @PathVariable("input") String input) {
        if (sessionData.getLoggedUser().getId() == null) {
            return Result.error("There is no logged in User");
        }

        if (input == null || input == "") {
            return Result.error("There is no input to check");
        }
        return Result.ok(bookService.findAllBooksByTitleContains(input,
                sessionData.getLoggedUser().getId()));
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result getBookById(@PathVariable("id") Long id) {
        return Result.ok(bookService.findById(id));
    }

    @GetMapping(path = "/booksByAuthorsId/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result getBooksByAuthorsId(@PathVariable("id") Long id) {
        return Result.ok(bookService.findAllBooksByAuthorsId(id));
    }

    @GetMapping(path = "/booksByGenresId/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result getBooksByGenresId(@PathVariable("id") Long id) {
        if (sessionData.getLoggedUser().getId() == null) {
            return Result.error("There is no logged in User");
        }
        return Result.ok(bookService.findAllBooksByGenresId(id));
    }

    @GetMapping(path = "/booksByGenres", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public Result getBooksByGenres(@RequestBody List<GenreDto> genres) {
        return Result.ok(bookService.findAllBooksByGenresIn(genres));
    }

    @GetMapping(path = "/booksByTitle", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result getBooksOrderedByTitle() {
        if (sessionData.getLoggedUser().getId() == null) {
            return Result.error("There is no logged in User");
        }
        return Result.ok(bookService.findAllBooksByOwnerIdOrderByTitleAsc(
                sessionData.getLoggedUser().getId()));
    }

    @GetMapping(path = "/booksByCreatedDate", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result getBooksOrderedByCreatedDate() {
        if (sessionData.getLoggedUser().getId() == null) {
            return Result.error("There is no logged in User");
        }

        return Result.ok(bookService.findAllBooksByOwnerIdOrderByCreatedDesc(
                sessionData.getLoggedUser().getId()));
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

    @GetMapping(path = "/setIsRead/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result setBookIsRead(@PathVariable("id") Long id) {
        return (bookService.setIsReadByBookId(id))
                ? Result.ok("OK")
                : Result.error("ERRPR");
    }

    @GetMapping(path = "/setInReader/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result setBookIsInReader(@PathVariable("id") Long id) {
        return (bookService.setInReaderByBookId(id))
                ? Result.ok("OK")
                : Result.error("ERRPR");
    }

}
