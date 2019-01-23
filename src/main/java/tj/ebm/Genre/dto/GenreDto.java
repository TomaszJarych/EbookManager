package tj.ebm.Genre.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class GenreDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

}
