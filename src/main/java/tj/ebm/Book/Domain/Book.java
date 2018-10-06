package tj.ebm.Book.Domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tj.ebm.Bookstore.domain.Bookstore;
import tj.ebm.Genre.domain.Genre;
import tj.ebm.User.Domain.User;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	//TODO dodać encję Autor i zamienić na Set @ManyToMany
	private String author; 

	private String ISBN;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_id")
	private User owner;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bookstore_id")
	private Bookstore bookstore;

	private LocalDateTime created = LocalDateTime.now();

	@ManyToMany(fetch = FetchType.EAGER)
	private final Set<Genre> genres = new HashSet<>();
}
