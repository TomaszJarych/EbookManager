package tj.ebm.Book.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tj.ebm.Author.domain.Author;
import tj.ebm.Bookstore.domain.Bookstore;
import tj.ebm.Genre.domain.Genre;
import tj.ebm.User.Domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String ISBN;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookstore_id")
    private Bookstore bookstore;

    private LocalDateTime created = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Author> authors = new HashSet<>();

    private Boolean isRead = false;

    private Boolean inReader = false;

    public static class BookBuilder {

        private Long id;
        private String title;
        private String ISBN;
        private User owner;
        private Bookstore bookstore;
        private LocalDateTime created = LocalDateTime.now();
        private Set<Genre> genres = new HashSet<>();
        private Set<Author> authors = new HashSet<>();
        private Boolean isRead = false;
        private Boolean inReader = false;

        public BookBuilder() {
        }

        public BookBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public BookBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder setISBN(String ISBN) {
            this.ISBN = ISBN;
            return this;
        }

        public BookBuilder setOwner(User owner) {
            this.owner = owner;
            return this;
        }

        public BookBuilder setBookstore(Bookstore bookstore) {
            this.bookstore = bookstore;
            return this;
        }

        public BookBuilder setCreated(LocalDateTime created) {
            this.created = created;
            return this;
        }

        public BookBuilder setGenres(Set<Genre> genres) {
            this.genres = genres;
            return this;
        }

        public BookBuilder setAuthors(Set<Author> authors) {
            this.authors = authors;
            return this;
        }

        public BookBuilder setRead(Boolean read) {
            isRead = read;
            return this;
        }

        public BookBuilder setInReader(Boolean inReader) {
            this.inReader = inReader;
            return this;
        }

        public Book build() {
            return new Book(id, title, ISBN, owner, bookstore, created, genres, authors, isRead, inReader);
        }
    }
}
