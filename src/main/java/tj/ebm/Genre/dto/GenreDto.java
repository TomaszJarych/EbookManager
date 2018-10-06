package tj.ebm.Genre.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
