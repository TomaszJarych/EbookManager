package tj.ebm.Author.dto;

import java.util.Set;

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
	private Long firstName;

	@NotBlank
	private Long lastName;

}
