package tj.ebm.Commons.DtoAndEntityConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tj.ebm.Author.Repository.AuthorRepository;
import tj.ebm.Book.Repository.BookRepository;
import tj.ebm.Bookstore.Repository.BookstoreRepository;
import tj.ebm.Bookstore.domain.Bookstore;
import tj.ebm.Bookstore.dto.BookstoreDto;
import tj.ebm.Genre.Repository.GenreRepository;
import tj.ebm.User.Repository.UserRepository;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DtoAndEntityBookstoreConverterTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BookstoreRepository bookstoreRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookRepository bookRepository;

    private DtoAndEntityConverter converter;

    private Long id;
    private String name;
    private String email;
    private String web;


    @BeforeEach
    void setUp() {
        converter = new DtoAndEntityConverter(userRepository, bookstoreRepository, genreRepository, authorRepository, bookRepository);
        id = 1L;
        name = "testBookstore";
        email = "bookstore@wp.pl";
        web = "https://bookstore.com";
    }

    @Test
    void toBookstoreDtoConverterTest() {
        //given
        Bookstore bookstore = new Bookstore();
        bookstore.setId(id);
        bookstore.setName(name);
        bookstore.setEmail(email);
        bookstore.setWeb(web);

        BookstoreDto result = new BookstoreDto();
        result.setId(id);
        result.setName(name);
        result.setEmail(email);
        result.setWeb(web);

        //when
        BookstoreDto actual = converter.toBookstoreDto(bookstore);

        //then

        assertAll("toBookstoreDto()MethodTest",
                () -> assertEquals(result.getId(), actual.getId()),
                () -> assertEquals(result.getEmail(), actual.getEmail()),
                () -> assertEquals(result.getName(), actual.getName()),
                () -> assertEquals(result.getWeb(), actual.getWeb())
        );
    }

    @Test
    void toBookstoreEntityConverterTestWithMockDB() {
        //given
        BookstoreDto dto = new BookstoreDto.BookstoreDtoBuilder()
                .setId(id)
                .setName(name)
                .setEmail(email)
                .setWeb(web)
                .build();

        Bookstore stub = new Bookstore.BookstoreEntityBuilder()
                .setId(id)
                .build();

        Bookstore expected = new Bookstore.BookstoreEntityBuilder()
                .setId(id)
                .setName(name)
                .setEmail(email)
                .setWeb(web)
                .build();

        when(bookstoreRepository.getOne(anyLong())).thenReturn(stub);


        //when
        Bookstore actual = converter.toBookstoreEntity(dto);

        //then
        verify(bookstoreRepository, times(1)).getOne(id);

        assertAll("toBookstoreEntity()methodTestWithDB",
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getEmail(), actual.getEmail()),
                () -> assertEquals(expected.getWeb(), actual.getWeb())
        );
    }

    @Test
    void toBookstoreEntityConverterTest() {
        //given
        BookstoreDto dto = new BookstoreDto.BookstoreDtoBuilder()
                .setId(null)
                .setName(name)
                .setEmail(email)
                .setWeb(web)
                .build();

        Bookstore expected = new Bookstore.BookstoreEntityBuilder()
                .setId(null)
                .setName(name)
                .setEmail(email)
                .setWeb(web)
                .build();


        //when
        Bookstore actual = converter.toBookstoreEntity(dto);

        //then
        assertAll("toBookstoreEntity()methodTest",
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getEmail(), actual.getEmail()),
                () -> assertEquals(expected.getWeb(), actual.getWeb())
        );
    }

    @Test
    void toBookstoreEntityConverterTestWithMockDBThrowsException() {
        //given
        BookstoreDto dto = new BookstoreDto.BookstoreDtoBuilder()
                .setId(id)
                .setName(name)
                .setEmail(email)
                .setWeb(web)
                .build();


        Bookstore expected = new Bookstore.BookstoreEntityBuilder()
                .setId(id)
                .setName(name)
                .setEmail(email)
                .setWeb(web)
                .build();

        when(bookstoreRepository.getOne(anyLong())).thenThrow(EntityNotFoundException.class);

        //then
        assertThrows(EntityNotFoundException.class, () -> converter.toBookstoreEntity(dto));
    }
}