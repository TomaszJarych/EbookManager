package tj.ebm.Bookstore.dto;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookstoreDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @URL
    private String web;

    public static class BookstoreDtoBuilder {
        private Long id;
        private String name;
        private String email;
        private String web;

        public BookstoreDtoBuilder() {
        }

        public BookstoreDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public BookstoreDtoBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public BookstoreDtoBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public BookstoreDtoBuilder setWeb(String web) {
            this.web = web;
            return this;
        }

        public BookstoreDto build() {
            return new BookstoreDto(id, name, email, web);
        }
    }
}
