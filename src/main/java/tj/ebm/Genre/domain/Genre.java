package tj.ebm.Genre.domain;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    public static class GenreBuilder {
        private Long id;
        private String name;
        private String description;

        public GenreBuilder() {
        }

        public GenreBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public GenreBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public GenreBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Genre build() {
            return new Genre(id, name, description);
        }
    }


}
