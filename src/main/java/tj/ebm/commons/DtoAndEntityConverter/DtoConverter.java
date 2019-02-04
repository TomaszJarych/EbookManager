package tj.ebm.commons.DtoAndEntityConverter;

import tj.ebm.Author.domain.Author;
import tj.ebm.Author.dto.AuthorDto;
import tj.ebm.Book.Domain.Book;
import tj.ebm.Book.dto.BookDto;
import tj.ebm.Bookstore.domain.Bookstore;
import tj.ebm.Bookstore.dto.BookstoreDto;
import tj.ebm.Genre.domain.Genre;
import tj.ebm.Genre.dto.GenreDto;
import tj.ebm.User.Domain.User;
import tj.ebm.User.dto.UserDto;

public interface DtoConverter {

    UserDto toUserDto(User user);

    User toUserEntity(UserDto dto);

    BookstoreDto toBookstoreDto(Bookstore store);

    Bookstore toBookstoreEntity(BookstoreDto dto);

    GenreDto toGenreDto(Genre genre);

    Genre toGenreEntity(GenreDto dto);

    AuthorDto toAuthorDto(Author author);

    AuthorDto toAuthorSimpleDto(Author author);

    Author toAuthorEntity(AuthorDto dto);

    BookDto toBookDto(Book book);

    BookDto toSimpleBookDto(Book book);

    Book toBookEntity(BookDto dto);
}
