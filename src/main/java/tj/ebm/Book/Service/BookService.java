package tj.ebm.Book.Service;

import java.util.List;

import tj.ebm.Book.dto.BookDto;
import tj.ebm.Commons.Service.BaseCrudService;

public interface BookService extends BaseCrudService<BookDto, Long> {

	List<BookDto> findAllBooksByAuthorsId(Long id);

}
