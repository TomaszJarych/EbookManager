package tj.ebm.Author.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tj.ebm.Book.Domain.Book;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();

    public static class AuthorBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private Set<Book> books = new HashSet<>();

        public AuthorBuilder() {
        }

        public AuthorBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public AuthorBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AuthorBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AuthorBuilder setBooks(Set<Book> books) {
            this.books = books;
            return this;
        }

        public Author build() {
            return new Author(id, firstName, lastName, books);
        }


    }

}
