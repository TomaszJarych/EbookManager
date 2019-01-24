package tj.ebm.Author.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tj.ebm.Book.dto.BookDto;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class AuthorDto {

    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Set<BookDto> books = new HashSet<>();

    public static class AuthorDtoBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private Set<BookDto> books = new HashSet<>();

        public AuthorDtoBuilder() {
        }

        public AuthorDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public AuthorDtoBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AuthorDtoBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AuthorDtoBuilder setBooks(Set<BookDto> books) {
            this.books = books;
            return this;
        }

        public AuthorDto build() {
            return new AuthorDto(id, firstName, lastName, books);
        }
    }

}
