package tj.ebm.Book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tj.ebm.Author.dto.AuthorDto;
import tj.ebm.Bookstore.dto.BookstoreDto;
import tj.ebm.Genre.dto.GenreDto;
import tj.ebm.User.dto.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class BookDto {

    private Long id;

    @NotBlank
    private String title;

    private String ISBN;

    @NotNull
    private UserDto owner;

    private BookstoreDto bookstore;

    private LocalDateTime created = LocalDateTime.now();

    private Set<GenreDto> genres = new HashSet<>();

    private Set<AuthorDto> authors = new HashSet<>();

    private Boolean isRead = false;

    private Boolean inReader = false;


    public static class BookDtoBuilder {
        private Long id;

        private String title;
        private String ISBN;
        private UserDto owner;
        private BookstoreDto bookstore;
        private LocalDateTime created = LocalDateTime.now();
        private Set<GenreDto> genres = new HashSet<>();
        private Set<AuthorDto> authors = new HashSet<>();
        private Boolean isRead = false;
        private Boolean inReader = false;

        public BookDtoBuilder() {
        }

        public BookDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public BookDtoBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public BookDtoBuilder setISBN(String ISBN) {
            this.ISBN = ISBN;
            return this;
        }

        public BookDtoBuilder setOwner(UserDto owner) {
            this.owner = owner;
            return this;
        }

        public BookDtoBuilder setBookstore(BookstoreDto bookstore) {
            this.bookstore = bookstore;
            return this;
        }

        public BookDtoBuilder setCreated(LocalDateTime created) {
            this.created = created;
            return this;
        }

        public BookDtoBuilder setGenres(Set<GenreDto> genres) {
            this.genres = genres;
            return this;
        }

        public BookDtoBuilder setAuthors(Set<AuthorDto> authors) {
            this.authors = authors;
            return this;
        }

        public BookDtoBuilder setRead(Boolean read) {
            isRead = read;
            return this;
        }

        public BookDtoBuilder setInReader(Boolean inReader) {
            this.inReader = inReader;
            return this;
        }

        public BookDto build() {
            return new BookDto(id, title, ISBN, owner, bookstore, created, genres, authors, isRead, inReader);
        }
    }

}
