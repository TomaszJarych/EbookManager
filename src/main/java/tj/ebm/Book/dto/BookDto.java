package tj.ebm.Book.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tj.ebm.Author.dto.AuthorDto;
import tj.ebm.Bookstore.dto.BookstoreDto;
import tj.ebm.Genre.dto.GenreDto;
import tj.ebm.User.dto.UserDto;

@Getter
@Setter
@NoArgsConstructor
public class BookDto {

	private Long id;

	@NotBlank
	private String title;

	private String ISBN;

	@NotNull
	private UserDto owner;

	private BookstoreDto bookstore;

	private LocalDateTime created = LocalDateTime.now();

	private Set<GenreDto> genres = new HashSet<>();

	private Set<AuthorDto> authors = new HashSet<>();

}
