package tj.ebm.Commons.DtoAndEntityConverter;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tj.ebm.Author.Repository.AuthorRepository;
import tj.ebm.Author.domain.Author;
import tj.ebm.Author.dto.AuthorDto;
import tj.ebm.Book.Domain.Book;
import tj.ebm.Book.Repository.BookRepository;
import tj.ebm.Book.dto.BookDto;
import tj.ebm.Bookstore.Repository.BookstoreRepository;
import tj.ebm.Bookstore.domain.Bookstore;
import tj.ebm.Bookstore.dto.BookstoreDto;
import tj.ebm.Commons.ENUM.UserRole;
import tj.ebm.Genre.Repository.GenreRepository;
import tj.ebm.Genre.domain.Genre;
import tj.ebm.Genre.dto.GenreDto;
import tj.ebm.User.Domain.User;
import tj.ebm.User.Repository.UserRepository;
import tj.ebm.User.dto.UserDto;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DtoAndEntityAuthorAndBookTest {
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
    private String firstName;
    private String lastName;
    private Set<Book> books;
    private Set<BookDto> booksDto;
    private LocalDateTime created;
    private String title;
    private String ISBN;
    private Bookstore bookstore;
    private BookstoreDto bookstoreDto;
    private String bookstoreName;
    private String bookstoreEmail;
    private String bookstoreWeb;
    private Set<Genre> genres;
    private Set<GenreDto> genresDto;
    private User owner;
    private UserDto ownerDto;
    private Genre genre;


    @BeforeEach
    void setUp() {
        converter = new DtoAndEntityConverter(userRepository, bookstoreRepository,
                genreRepository, authorRepository, bookRepository);
        id = 1L;
        firstName = "TestFirstName";
        lastName = "TestLastName";
        books = new HashSet<>();
        booksDto = new HashSet<>();
        created = LocalDateTime.now();
        title = "TestTitle";
        ISBN = "123345-Z";
        bookstoreName = "testBookstoreName";
        bookstoreEmail = "testBookstoreEmail@wp.pl";
        bookstoreWeb = "http://www.bookstore.pl";
        bookstore = new Bookstore.BookstoreEntityBuilder()
                .setId(id)
                .setName(bookstoreName)
                .setEmail(bookstoreEmail)
                .setWeb(bookstoreWeb)
                .build();
        bookstoreDto = new BookstoreDto.BookstoreDtoBuilder()
                .setId(id)
                .setName(bookstoreName)
                .setEmail(bookstoreEmail)
                .setWeb(bookstoreWeb)
                .build();
        genre = new Genre.GenreBuilder()
                .setId(id)
                .setName("testGenreName")
                .setDescription("testDescription")
                .build();
        GenreDto genreDto = new GenreDto.GenreDtoBuilder()
                .setId(id)
                .setName("testGenreName")
                .setDescription("testDescription")
                .build();
        genres = new HashSet<>();
        genresDto = new HashSet<>();
        genres.add(genre);
        genresDto.add(genreDto);
        owner = new User.UserBuilder()
                .id(id)
                .login("testLogin")
                .firstName("John")
                .lastName("Doe")
                .email("jd@wp.pl")
                .role(UserRole.USER)
                .password("pass")
                .build();

        ownerDto = new UserDto.UserDtoBuilder()
                .id(id)
                .login("testLogin")
                .firstName("John")
                .lastName("Doe")
                .email("jd@wp.pl")
                .role(UserRole.USER)
                .password("pass")
                .build();


        Book book1 = new Book.BookBuilder()
                .setId(id)
                .setTitle(title)
                .setISBN(ISBN)
                .setCreated(created)
                .setBookstore(bookstore)
                .setGenres(genres)
                .setInReader(true)
                .setRead(true)
                .build();

        BookDto bookDto1 = new BookDto.BookDtoBuilder()
                .setId(id)
                .setTitle(title)
                .setISBN(ISBN)
                .setCreated(created)
                .setBookstore(bookstoreDto)
                .setGenres(genresDto)
                .setInReader(true)
                .setRead(true)
                .build();

        books.add(book1);
        booksDto.add(bookDto1);
    }

    @Test
    @DisplayName("To AuthorDto converter test")
    void toAuthorDtoConverterTest() {
        // given
        Author author = new Author.AuthorBuilder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBooks(books)
                .build();

        AuthorDto expected = new AuthorDto.AuthorDtoBuilder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBooks(booksDto)
                .build();

        //when
        AuthorDto actual = converter.toAuthorDto(author);

        //then
        assertAll("assertions",
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getFirstName(), actual.getFirstName()),
                () -> assertEquals(expected.getLastName(), actual.getLastName()),
                () -> assertEquals(expected.getBooks().size(), actual.getBooks().size())
        );
    }

    @Test
    @DisplayName("toSimpleAuthorDto converter test")
    void toSimpleAuthorDtoConverterTest() {
        // given
        Author author = new Author.AuthorBuilder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBooks(null)
                .build();

        AuthorDto expected = new AuthorDto.AuthorDtoBuilder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBooks(null)
                .build();

        //when
        AuthorDto actual = converter.toAuthorSimpleDto(author);

        //then
        assertAll("assertions",
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getFirstName(), actual.getFirstName()),
                () -> assertEquals(expected.getLastName(), actual.getLastName())
        );
    }

    @Test
    @DisplayName("toAuthorEntity converter test without DB- new Author option")
    void toAuthorEntityWithoutDBTest() {
        //given
        AuthorDto dto = new AuthorDto.AuthorDtoBuilder()
                .setId(null)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBooks(null)
                .build();

        Author expected = new Author.AuthorBuilder()
                .setId(null)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBooks(new HashSet<>())
                .build();

        //when
        Author actual = converter.toAuthorEntity(dto);

        //then
        Assertions.assertNotNull(actual);
        assertAll("assertions",
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getFirstName(), actual.getFirstName()),
                () -> assertEquals(expected.getLastName(), actual.getLastName()),
                () -> assertEquals(expected.getBooks().size(), actual.getBooks().size())
        );
    }

    @Test
    @DisplayName("toAuthorEntity converter test without DB- update Author option")
    void toAuthorEntityWithDBTest() {
        //given
        AuthorDto dto = new AuthorDto.AuthorDtoBuilder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBooks(booksDto)
                .build();

        Author expected = new Author.AuthorBuilder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBooks(books)
                .build();

        Author stub = new Author.AuthorBuilder()
                .setId(id)
                .setBooks(books)
                .build();

        when(authorRepository.getOne(anyLong())).thenReturn(stub);

        //when
        Author actual = converter.toAuthorEntity(dto);

        //then
        verify(authorRepository, atLeast(1)).getOne(anyLong());
        Assertions.assertNotNull(actual);
        assertAll("assertions",
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getFirstName(), actual.getFirstName()),
                () -> assertEquals(expected.getLastName(), actual.getLastName()),
                () -> assertEquals(expected.getBooks().size(), actual.getBooks().size())
        );
    }

    @Test
    @DisplayName("toAuthorEntity() throws EntityNotFoundException")
    void toAuthorEntityConverterThrowsEntityNotFoundException() {
        //given
        AuthorDto dto = new AuthorDto.AuthorDtoBuilder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBooks(booksDto)
                .build();
        when(authorRepository.getOne(anyLong())).thenThrow(EntityNotFoundException.class);

        //then
        assertThrows(EntityNotFoundException.class,
                () -> converter.toAuthorEntity(dto));
    }

    @Test
    @DisplayName("toBookDtoConverter method test")
    void toBookDtoConverterTest() {
        //given

        Author author = new Author.AuthorBuilder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBooks(null)
                .build();
        AuthorDto authorDto = new AuthorDto.AuthorDtoBuilder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBooks(null)
                .build();
        Set<Author> authors = new HashSet<>();
        authors.add(author);
        Set<AuthorDto> authorDtos = new HashSet<>();
        authorDtos.add(authorDto);
        Book book = new Book.BookBuilder()
                .setId(id)
                .setTitle(title)
                .setCreated(created)
                .setISBN(ISBN)
                .setOwner(owner)
                .setRead(true)
                .setInReader(true)
                .setBookstore(bookstore)
                .setGenres(genres)
                .setAuthors(authors)
                .build();
        BookDto expected = new BookDto.BookDtoBuilder()
                .setId(id)
                .setTitle(title)
                .setCreated(created)
                .setISBN(ISBN)
                .setOwner(ownerDto)
                .setRead(true)
                .setInReader(true)
                .setBookstore(bookstoreDto)
                .setGenres(genresDto)
                .setAuthors(authorDtos)
                .build();


        //when
        BookDto actual = converter.toBookDto(book);

        //then
        Assertions.assertNotNull(actual);
        assertAll("toBookDto assertions",
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getISBN(), actual.getISBN()),
                () -> assertEquals(expected.getTitle(), actual.getTitle()),
                () -> assertEquals(expected.getCreated(), actual.getCreated()),
                () -> assertEquals(expected.getInReader(), actual.getInReader()),
                () -> assertEquals(expected.getIsRead(), actual.getIsRead()),
                () -> assertEquals(expected.getOwner().getId(), actual.getOwner().getId()),
                () -> assertEquals(expected.getOwner().getFirstName(), actual.getOwner().getFirstName()),
                () -> assertEquals(expected.getOwner().getLastName(), actual.getOwner().getLastName()),
                () -> assertEquals(expected.getOwner().getRole(), actual.getOwner().getRole()),
                () -> assertEquals(expected.getOwner().getEmail(), actual.getOwner().getEmail()),
                () -> assertEquals(expected.getOwner().getLogin(), actual.getOwner().getLogin()),
                () -> assertEquals(expected.getBookstore().getId(), actual.getBookstore().getId()),
                () -> assertEquals(expected.getBookstore().getName(), actual.getBookstore().getName()),
                () -> assertEquals(expected.getBookstore().getWeb(), actual.getBookstore().getWeb()),
                () -> assertEquals(expected.getBookstore().getEmail(), actual.getBookstore().getEmail()),
                () -> assertEquals(expected.getGenres().size(), actual.getGenres().size()),
                () -> assertEquals(expected.getAuthors().size(), actual.getAuthors().size())
        );
    }

    @Test
    @DisplayName("toSimpleBookDtoConverter method test")
    void toSimpleBookDtoConverterTest() {
        //given

        Book book = new Book.BookBuilder()
                .setId(id)
                .setTitle(title)
                .setCreated(created)
                .setISBN(ISBN)
                .setOwner(owner)
                .setRead(true)
                .setInReader(true)
                .setBookstore(bookstore)
                .setGenres(genres)
                .build();
        BookDto expected = new BookDto.BookDtoBuilder()
                .setId(id)
                .setTitle(title)
                .setCreated(created)
                .setISBN(ISBN)
                .setOwner(ownerDto)
                .setRead(true)
                .setInReader(true)
                .setBookstore(bookstoreDto)
                .setGenres(genresDto)
                .build();


        //when
        BookDto actual = converter.toSimpleBookDto(book);

        //then
        Assertions.assertNotNull(actual);
        assertAll("toBookDto assertions",
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getISBN(), actual.getISBN()),
                () -> assertEquals(expected.getTitle(), actual.getTitle()),
                () -> assertEquals(expected.getCreated(), actual.getCreated()),
                () -> assertEquals(expected.getInReader(), actual.getInReader()),
                () -> assertEquals(expected.getIsRead(), actual.getIsRead()),
                () -> assertEquals(expected.getOwner().getId(), actual.getOwner().getId()),
                () -> assertEquals(expected.getOwner().getFirstName(), actual.getOwner().getFirstName()),
                () -> assertEquals(expected.getOwner().getLastName(), actual.getOwner().getLastName()),
                () -> assertEquals(expected.getOwner().getRole(), actual.getOwner().getRole()),
                () -> assertEquals(expected.getOwner().getEmail(), actual.getOwner().getEmail()),
                () -> assertEquals(expected.getOwner().getLogin(), actual.getOwner().getLogin()),
                () -> assertEquals(expected.getBookstore().getId(), actual.getBookstore().getId()),
                () -> assertEquals(expected.getBookstore().getName(), actual.getBookstore().getName()),
                () -> assertEquals(expected.getBookstore().getWeb(), actual.getBookstore().getWeb()),
                () -> assertEquals(expected.getBookstore().getEmail(), actual.getBookstore().getEmail()),
                () -> assertEquals(expected.getGenres().size(), actual.getGenres().size())
        );
    }

    @Test
    @DisplayName("toBookEntity converter method - new Book option")
    void toBookEntityWithoutDB() {

        Author author = new Author.AuthorBuilder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBooks(new HashSet<>())
                .build();
        AuthorDto authorDto = new AuthorDto.AuthorDtoBuilder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBooks(new HashSet<>())
                .build();
        Set<Author> authors = new HashSet<>();
        authors.add(author);
        Set<AuthorDto> authorDtos = new HashSet<>();
        authorDtos.add(authorDto);
        Book expected = new Book.BookBuilder()
                .setId(null)
                .setTitle(title)
                .setCreated(created)
                .setISBN(ISBN)
                .setOwner(owner)
                .setRead(true)
                .setInReader(true)
                .setBookstore(bookstore)
                .setGenres(genres)
                .setAuthors(authors)
                .build();
        BookDto dto = new BookDto.BookDtoBuilder()
                .setId(null)
                .setTitle(title)
                .setCreated(created)
                .setISBN(ISBN)
                .setOwner(ownerDto)
                .setRead(true)
                .setInReader(true)
                .setBookstore(bookstoreDto)
                .setGenres(genresDto)
                .setAuthors(authorDtos)
                .build();
        when(userRepository.getOne(anyLong())).thenReturn(owner);
        when(authorRepository.getOne(anyLong())).thenReturn(author);
        when(genreRepository.getOne(anyLong())).thenReturn(genre);
        when(bookstoreRepository.getOne(anyLong())).thenReturn(bookstore);
        //when
        Book actual = converter.toBookEntity(dto);
        actual.setCreated(created);

        //then
        Assertions.assertNotNull(actual);
        assertAll("toBookEnttity assertions",
                () -> assertEquals(expected.getISBN(), actual.getISBN()),
                () -> assertEquals(expected.getTitle(), actual.getTitle()),
                () -> assertEquals(expected.getCreated(), actual.getCreated()),
                () -> assertEquals(expected.getInReader(), actual.getInReader()),
                () -> assertEquals(expected.getIsRead(), actual.getIsRead()),
                () -> assertEquals(expected.getOwner().getId(), actual.getOwner().getId()),
                () -> assertEquals(expected.getOwner().getFirstName(), actual.getOwner().getFirstName()),
                () -> assertEquals(expected.getOwner().getLastName(), actual.getOwner().getLastName()),
                () -> assertEquals(expected.getOwner().getRole(), actual.getOwner().getRole()),
                () -> assertEquals(expected.getOwner().getEmail(), actual.getOwner().getEmail()),
                () -> assertEquals(expected.getOwner().getLogin(), actual.getOwner().getLogin()),
                () -> assertEquals(expected.getBookstore().getId(), actual.getBookstore().getId()),
                () -> assertEquals(expected.getBookstore().getName(), actual.getBookstore().getName()),
                () -> assertEquals(expected.getBookstore().getWeb(), actual.getBookstore().getWeb()),
                () -> assertEquals(expected.getBookstore().getEmail(), actual.getBookstore().getEmail()),
                () -> assertEquals(expected.getGenres().size(), actual.getGenres().size())
        );
    }

    @Test
    @DisplayName("toBookEntity converter method")
    void toBookEntityWithDB() {

        Author author = new Author.AuthorBuilder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBooks(new HashSet<>())
                .build();
        AuthorDto authorDto = new AuthorDto.AuthorDtoBuilder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBooks(new HashSet<>())
                .build();
        Set<Author> authors = new HashSet<>();
        authors.add(author);
        Set<AuthorDto> authorDtos = new HashSet<>();
        authorDtos.add(authorDto);
        Book expected = new Book.BookBuilder()
                .setId(id)
                .setTitle(title)
                .setCreated(created)
                .setISBN(ISBN)
                .setOwner(owner)
                .setRead(true)
                .setInReader(true)
                .setBookstore(bookstore)
                .setGenres(genres)
                .setAuthors(authors)
                .build();
        BookDto dto = new BookDto.BookDtoBuilder()
                .setId(id)
                .setTitle(title)
                .setCreated(created)
                .setISBN(ISBN)
                .setOwner(ownerDto)
                .setRead(true)
                .setInReader(true)
                .setBookstore(bookstoreDto)
                .setGenres(genresDto)
                .setAuthors(authorDtos)
                .build();
        Book stub = new Book.BookBuilder()
                .setId(id)
                .build();
        when(userRepository.getOne(anyLong())).thenReturn(owner);
        when(authorRepository.getOne(anyLong())).thenReturn(author);
        when(genreRepository.getOne(anyLong())).thenReturn(genre);
        when(bookstoreRepository.getOne(anyLong())).thenReturn(bookstore);
        when(bookRepository.getOne(anyLong())).thenReturn(stub);
        //when
        Book actual = converter.toBookEntity(dto);
        actual.setCreated(created);

        //then
        Assertions.assertNotNull(actual);
        assertAll("toBookEnttity assertions",
                () -> assertEquals(expected.getISBN(), actual.getISBN()),
                () -> assertEquals(expected.getTitle(), actual.getTitle()),
                () -> assertEquals(expected.getCreated(), actual.getCreated()),
                () -> assertEquals(expected.getInReader(), actual.getInReader()),
                () -> assertEquals(expected.getIsRead(), actual.getIsRead()),
                () -> assertEquals(expected.getOwner().getId(), actual.getOwner().getId()),
                () -> assertEquals(expected.getOwner().getFirstName(), actual.getOwner().getFirstName()),
                () -> assertEquals(expected.getOwner().getLastName(), actual.getOwner().getLastName()),
                () -> assertEquals(expected.getOwner().getRole(), actual.getOwner().getRole()),
                () -> assertEquals(expected.getOwner().getEmail(), actual.getOwner().getEmail()),
                () -> assertEquals(expected.getOwner().getLogin(), actual.getOwner().getLogin()),
                () -> assertEquals(expected.getBookstore().getId(), actual.getBookstore().getId()),
                () -> assertEquals(expected.getBookstore().getName(), actual.getBookstore().getName()),
                () -> assertEquals(expected.getBookstore().getWeb(), actual.getBookstore().getWeb()),
                () -> assertEquals(expected.getBookstore().getEmail(), actual.getBookstore().getEmail()),
                () -> assertEquals(expected.getGenres().size(), actual.getGenres().size())
        );
    }

    @Test
    @DisplayName("toBookEntity throws EntityNotFoundException")
    void toBookEntityThrowsEntityNotFoundExceptionTest() {
        //given
        BookDto stub = new BookDto.BookDtoBuilder()
                .setId(id)
                .build();

        when(bookRepository.getOne(anyLong())).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class,
                () -> converter.toBookEntity(stub)
        );

    }
}