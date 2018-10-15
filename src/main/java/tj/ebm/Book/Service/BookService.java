package tj.ebm.Book.Service;

import java.util.List;

import tj.ebm.Book.dto.BookDto;
import tj.ebm.Commons.ServiceInterface.BaseCrudService;
import tj.ebm.Genre.dto.GenreDto;

public interface BookService extends BaseCrudService<BookDto, Long> {

	List<BookDto> findAllBooksByAuthorsId(Long id);

	List<BookDto> findAllBooksByGenresIn(List<GenreDto> genres);

	List<BookDto> findAllBooksByGenresId(Long id);

	Boolean setIsReadByBookId(Long id);

	Boolean setInReaderByBookId(Long id);

	List<BookDto> findAllBooksByOwnerIdOrderByTitleAsc(Long id);

	List<BookDto> findAllBooksByOwnerIdOrderByCreatedDesc(Long id);

	List<BookDto> findAllBooksByOwnerId(Long id);
}
