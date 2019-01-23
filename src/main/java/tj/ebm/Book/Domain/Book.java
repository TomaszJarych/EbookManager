package tj.ebm.Book.Domain;

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

@Getter
@Setter
@NoArgsConstructor
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
}
