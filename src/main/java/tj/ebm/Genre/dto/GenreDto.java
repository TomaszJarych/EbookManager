package tj.ebm.Genre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

import static lombok.AccessLevel.PRIVATE;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class GenreDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    public static class GenreDtoBuilder {
        private Long id;
        private String name;
        private String description;

        public GenreDtoBuilder() {
        }

        public GenreDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public GenreDtoBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public GenreDtoBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public GenreDto build() {
            return new GenreDto(id, name, description);
        }
    }
}
