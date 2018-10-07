package tj.ebm.Author.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorDto {

	private Long id;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

}
