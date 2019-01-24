package tj.ebm.Bookstore.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "book_store")
public class Bookstore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String web;

    public static class BookstoreEntityBuilder {
        private Long id;
        private String name;
        private String email;
        private String web;

        public BookstoreEntityBuilder() {
        }

        public BookstoreEntityBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public BookstoreEntityBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public BookstoreEntityBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public BookstoreEntityBuilder setWeb(String web) {
            this.web = web;
            return this;
        }

        public Bookstore build() {
            return new Bookstore(id, name, email, web);
        }
    }
}
