package tj.ebm.commons.DtoAndEntityConverter;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tj.ebm.Author.Repository.AuthorRepository;
import tj.ebm.Author.domain.Author;
import tj.ebm.Author.dto.AuthorDto;
import tj.ebm.Book.Domain.Book;
import tj.ebm.Book.Repository.BookRepository;
import tj.ebm.Book.dto.BookDto;
import tj.ebm.Bookstore.Repository.BookstoreRepository;
import tj.ebm.Bookstore.domain.Bookstore;
import tj.ebm.Bookstore.dto.BookstoreDto;
import tj.ebm.Genre.Repository.GenreRepository;
import tj.ebm.Genre.domain.Genre;
import tj.ebm.Genre.dto.GenreDto;
import tj.ebm.User.Domain.User;
import tj.ebm.User.Repository.UserRepository;
import tj.ebm.User.dto.UserDto;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DtoAndEntityConverter implements DtoConverter {

    private final UserRepository userRepository;
    private final BookstoreRepository bookstoreRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public DtoAndEntityConverter(UserRepository userRepository,
                                 BookstoreRepository bookstoreRepository,
                                 GenreRepository genreRepository, AuthorRepository authorRepository,
                                 BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookstoreRepository = bookstoreRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public UserDto toUserDto(User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        return dto;
    }

    @Override
    public User toUserEntity(UserDto dto) throws EntityNotFoundException {
        User user;

        if (Objects.nonNull(dto.getId())) {
            user = userRepository.getOne(dto.getId());

        } else {
            user = new User();
        }

        user.setId(dto.getId());
        user.setLogin(dto.getLogin());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        if (dto.getPassword() != null && dto.getPassword() != "") {
            user.setPassword(
                    BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
        }

        return user;
    }

    @Override
    public BookstoreDto toBookstoreDto(Bookstore store) {
        BookstoreDto dto = new BookstoreDto();

        dto.setId(store.getId());
        dto.setName(store.getName());
        dto.setEmail(store.getEmail());
        dto.setWeb(store.getWeb());

        return dto;
    }

    @Override
    public Bookstore toBookstoreEntity(BookstoreDto dto)
            throws EntityNotFoundException {
        Bookstore store;
        if (Objects.nonNull(dto.getId())) {
            store = bookstoreRepository.getOne(dto.getId());

        } else {
            store = new Bookstore();
        }

        store.setId(dto.getId());
        store.setName(dto.getName());
        store.setEmail(dto.getEmail());
        store.setWeb(dto.getWeb());

        return store;
    }

    @Override
    public GenreDto toGenreDto(Genre genre) {
        GenreDto dto = new GenreDto();

        dto.setId(genre.getId());
        dto.setName(genre.getName());
        dto.setDescription(genre.getDescription());

        return dto;
    }

    @Override
    public Genre toGenreEntity(GenreDto dto) throws EntityNotFoundException {
        Genre genre;
        if (Objects.nonNull(dto.getId())) {
            genre = genreRepository.getOne(dto.getId());
        } else {

            genre = new Genre();
        }

        genre.setId(dto.getId());
        genre.setName(dto.getName());
        genre.setDescription(dto.getDescription());

        return genre;
    }

    @Override
    public AuthorDto toAuthorDto(Author author) {
        AuthorDto dto = new AuthorDto();

        dto.setId(author.getId());
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());
        if (Objects.nonNull(author.getBooks())
                && !author.getBooks().isEmpty()) {
            dto.getBooks().clear();
            dto.setBooks(author.getBooks().stream().map(this::toSimpleBookDto)
                    .collect(Collectors.toSet()));

        }

        return dto;
    }

    @Override
    public AuthorDto toAuthorSimpleDto(Author author) {
        AuthorDto dto = new AuthorDto();

        dto.setId(author.getId());
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());

        return dto;
    }

    @Override
    public Author toAuthorEntity(AuthorDto dto) throws EntityNotFoundException {
        Author author;
        if (Objects.nonNull(dto.getId())) {
            author = authorRepository.getOne(dto.getId());
        } else {
            author = new Author();
        }

        author.setId(dto.getId());
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());

        return author;
    }

    @Override
    public BookDto toBookDto(Book book) {
        BookDto dto = new BookDto();

        dto.setId(book.getId());
        dto.setISBN(book.getISBN());
        dto.setTitle(book.getTitle());
        dto.setCreated(book.getCreated());

        dto.setIsRead(book.getIsRead());
        dto.setInReader(book.getInReader());

        if (Objects.nonNull(book.getOwner())) {
            dto.setOwner(toUserDto(book.getOwner()));
        }
        if (Objects.nonNull(book.getBookstore())) {
            dto.setBookstore(toBookstoreDto(book.getBookstore()));
        }
        if (Objects.nonNull(book.getGenres()) && !book.getGenres().isEmpty()) {
            dto.getGenres().clear();
            dto.setGenres(book.getGenres().stream().filter(Objects::nonNull)
                    .map(this::toGenreDto).collect(Collectors.toSet()));

        }
        if (Objects.nonNull(book.getAuthors())
                && !book.getAuthors().isEmpty()) {
            dto.getAuthors().clear();
            dto.setAuthors(book.getAuthors().stream().filter(Objects::nonNull)
                    .map(this::toAuthorSimpleDto).collect(Collectors.toSet()));

        }

        return dto;
    }

    @Override
    public BookDto toSimpleBookDto(Book book) {
        BookDto dto = new BookDto();

        dto.setId(book.getId());
        dto.setISBN(book.getISBN());
        dto.setTitle(book.getTitle());
        dto.setCreated(book.getCreated());
        dto.setAuthors(null);

        dto.setIsRead(book.getIsRead());
        dto.setInReader(book.getInReader());

        if (Objects.nonNull(book.getOwner())) {
            dto.setOwner(toUserDto(book.getOwner()));
        }
        if (Objects.nonNull(book.getBookstore())) {
            dto.setBookstore(toBookstoreDto(book.getBookstore()));
        }
        if (Objects.nonNull(book.getGenres()) && !book.getGenres().isEmpty()) {
            dto.getGenres().clear();
            dto.setGenres(book.getGenres().stream().filter(Objects::nonNull)
                    .map(this::toGenreDto).collect(Collectors.toSet()));

        }

        return dto;
    }

    @Override
    public Book toBookEntity(BookDto dto) throws EntityNotFoundException {
        Book book;
        if (Objects.nonNull(dto.getId())) {
            book = bookRepository.getOne(dto.getId());
        } else {
            book = new Book();
        }

        book.setId(dto.getId());
        book.setISBN(dto.getISBN());
        book.setTitle(dto.getTitle());
        book.setIsRead(dto.getIsRead());
        book.setInReader(dto.getInReader());

        if (Objects.nonNull(dto.getOwner())) {
            book.setOwner(userRepository.getOne(dto.getOwner().getId()));
        }

        if (Objects.nonNull(dto.getBookstore().getId())) {
            book.setBookstore(
                    bookstoreRepository.getOne(dto.getBookstore().getId()));
        }
        if (Objects.nonNull(dto.getGenres()) && !dto.getGenres().isEmpty()) {

            book.setGenres(dto.getGenres().stream()
                    .map(el -> genreRepository.getOne(el.getId()))
                    .collect(Collectors.toSet()));
        }

        if (Objects.nonNull(dto.getAuthors()) && !dto.getAuthors().isEmpty()) {
            book.setAuthors(dto.getAuthors().stream()
                    .map(el -> authorRepository.getOne(el.getId()))
                    .collect(Collectors.toSet()));

        }

        return book;
    }
}
