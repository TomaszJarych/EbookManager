package tj.ebm.Bookstore.Service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tj.ebm.Bookstore.Repository.BookstoreRepository;
import tj.ebm.Bookstore.Service.BookstoreService;
import tj.ebm.Bookstore.domain.Bookstore;
import tj.ebm.Bookstore.dto.BookstoreDto;
import tj.ebm.commons.DtoAndEntityConverter.DtoAndEntityConverter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookstoreImpl implements BookstoreService {

    private final BookstoreRepository bookstoreRepository;
    private final DtoAndEntityConverter converter;

    @Autowired
    public BookstoreImpl(BookstoreRepository bookstoreRepository,
                         DtoAndEntityConverter converter) {
        this.bookstoreRepository = bookstoreRepository;
        this.converter = converter;
    }

    @Override
    public BookstoreDto findById(Long id) {
        return converter.toBookstoreDto(bookstoreRepository.getOne(id));
    }

    @Override
    public BookstoreDto save(BookstoreDto dto) {
        return converter.toBookstoreDto(
                bookstoreRepository.save(converter.toBookstoreEntity(dto)));
    }

    @Override
    public Boolean deleteFromDb(Long id) {
        try {
            bookstoreRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<BookstoreDto> getAll() {
        return toBookstoreDtoList(bookstoreRepository.findAll());
    }

    private List<BookstoreDto> toBookstoreDtoList(List<Bookstore> list) {
        return list.stream().filter(Objects::nonNull)
                .map(converter::toBookstoreDto).collect(Collectors.toList());
    }

}
