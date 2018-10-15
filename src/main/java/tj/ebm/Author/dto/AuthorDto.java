package tj.ebm.Author.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tj.ebm.Book.dto.BookDto;

@Getter
@Setter
@NoArgsConstructor
public class AuthorDto {

	private Long id;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	private Set<BookDto> books = new HashSet<>();

}
