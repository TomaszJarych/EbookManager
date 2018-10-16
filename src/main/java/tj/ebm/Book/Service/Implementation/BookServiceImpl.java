package tj.ebm.Book.Service.Implementation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tj.ebm.Book.Domain.Book;
import tj.ebm.Book.Repository.BookRepository;
import tj.ebm.Book.Service.BookService;
import tj.ebm.Book.dto.BookDto;
import tj.ebm.Commons.DtoAndEntityConverter.DtoAndEntityConverter;
import tj.ebm.Genre.dto.GenreDto;

@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;
	private final DtoAndEntityConverter converter;

	@Autowired
	public BookServiceImpl(BookRepository bookRepository,
			DtoAndEntityConverter converter) {
		this.bookRepository = bookRepository;
		this.converter = converter;
	}

	@Override
	public BookDto findById(Long id) {
		return converter.toBookDto(bookRepository.getOne(id));
	}

	@Override
	public BookDto save(BookDto dto) {
		return converter
				.toBookDto(bookRepository.save(converter.toBookEntity(dto)));
	}

	@Override
	public Boolean deleteFromDb(Long id) {
		try {
			bookRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean setIsReadByBookId(Long id) {
		try {
			BookDto dto = converter.toBookDto(bookRepository.getOne(id));
			if (dto.getIsRead()) {
				dto.setIsRead(false);
			} else {
				dto.setIsRead(true);
			}
			bookRepository.save(converter.toBookEntity(dto));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean setInReaderByBookId(Long id) {
		try {
			BookDto dto = converter.toBookDto(bookRepository.getOne(id));
			if (dto.getInReader()) {
				dto.setInReader(false);
			} else {
				dto.setInReader(true);
			}
			bookRepository.save(converter.toBookEntity(dto));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<BookDto> findAllBooksByAuthorsId(Long id) {
		return toBookDtoList(bookRepository.findAllBooksByAuthorsId(id));
	}

	@Override
	public List<BookDto> getAll() {
		return toBookDtoList(bookRepository.findAll());
	}

	@Override
	public List<BookDto> findAllBooksByGenresIn(List<GenreDto> genres) {
		return toBookDtoList(bookRepository.findAllBooksByGenresIn(genres
				.stream().map(converter::toGenreEntity).filter(Objects::nonNull)
				.collect(Collectors.toList())));
	}

	@Override
	public List<BookDto> findAllBooksByGenresId(Long id) {
		return toBookDtoList(bookRepository.findAllBooksByGenresId(id));
	}

	@Override
	public List<BookDto> findAllBooksByOwnerIdOrderByTitleAsc(Long id) {
		return toBookDtoList(
				bookRepository.findAllBooksByOwnerIdOrderByTitleAsc(id));
	}

	@Override
	public List<BookDto> findAllBooksByOwnerIdOrderByCreatedDesc(Long id) {
		return toBookDtoList(
				bookRepository.findAllBooksByOwnerIdOrderByCreatedDesc(id));
	}

	@Override
	public List<BookDto> findAllBooksByOwnerId(Long id) {
		return toBookDtoList(bookRepository.findAllBooksByOwnerId(id));
	}

	@Override
	public List<BookDto> findAllBooksByTitleContains(String input, Long id) {
		return toBookDtoList(
				bookRepository.findAllBooksByTitleContains(input, id));
	}

	private List<BookDto> toBookDtoList(List<Book> list) {
		return list.stream().filter(Objects::nonNull).map(converter::toBookDto)
				.collect(Collectors.toList());
	}

}
