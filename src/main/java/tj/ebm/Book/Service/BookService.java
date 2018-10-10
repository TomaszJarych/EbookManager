package tj.ebm.Book.Service;

import java.util.List;

import tj.ebm.Book.dto.BookDto;
import tj.ebm.Commons.Service.BaseCrudService;
import tj.ebm.Genre.dto.GenreDto;

public interface BookService extends BaseCrudService<BookDto, Long> {

	List<BookDto> findAllBooksByAuthorsId(Long id);
	
	List<BookDto> findAllBooksByGenresIn(List<GenreDto> genres);
	
	List<BookDto> findAllBooksByGenresId(Long id);

}
