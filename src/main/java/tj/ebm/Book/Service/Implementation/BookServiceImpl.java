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
	public List<BookDto> getAll() {
		return toBookDtoList(bookRepository.findAll());
	}

	private List<BookDto> toBookDtoList(List<Book> list) {
		return list.stream().filter(Objects::nonNull).map(converter::toBookDto)
				.collect(Collectors.toList());
	}

}
