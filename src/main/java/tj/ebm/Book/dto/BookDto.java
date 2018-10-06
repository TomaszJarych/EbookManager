package tj.ebm.Book.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tj.ebm.Bookstore.domain.Bookstore;
import tj.ebm.Genre.domain.Genre;
import tj.ebm.User.Domain.User;

@Getter
@Setter
@NoArgsConstructor
public class BookDto {

	private Long id;

	@NotBlank
	private String title;

	private String ISBN;

	@NotBlank
	private String author;

	@NotNull
	private User owner;

	private Bookstore bookstore;

	private LocalDateTime created = LocalDateTime.now();

	private final Set<Genre> genres = new HashSet<>();
}
