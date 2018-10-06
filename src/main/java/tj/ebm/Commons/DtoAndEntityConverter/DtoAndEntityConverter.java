package tj.ebm.Commons.DtoAndEntityConverter;

import java.util.Objects;

import javax.persistence.EntityNotFoundException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tj.ebm.Bookstore.Repository.BookstoreRepository;
import tj.ebm.Bookstore.domain.Bookstore;
import tj.ebm.Bookstore.dto.BookstoreDto;
import tj.ebm.Genre.Repository.GenreRepository;
import tj.ebm.Genre.domain.Genre;
import tj.ebm.Genre.dto.GenreDto;
import tj.ebm.User.Domain.User;
import tj.ebm.User.Repository.UserRepository;
import tj.ebm.User.dto.UserDto;

@Component
public class DtoAndEntityConverter {

	private final UserRepository userRepository;
	private final BookstoreRepository bookstoreRepository;
	private final GenreRepository genreRepository;

	@Autowired
	public DtoAndEntityConverter(UserRepository userRepository,
			BookstoreRepository bookstoreRepository,
			GenreRepository genreRepository) {
		this.userRepository = userRepository;
		this.bookstoreRepository = bookstoreRepository;
		this.genreRepository = genreRepository;
	}

	public UserDto toUserDto(User user) {
		UserDto dto = new UserDto();

		dto.setId(user.getId());
		dto.setLogin(user.getLogin());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		dto.setRole(user.getRole());

		return dto;
	}

	public User toUserEntity(UserDto dto) throws EntityNotFoundException {
		User user;

		if (Objects.nonNull(dto.getId())) {
			user = userRepository.getOne(dto.getId());

		} else {
			user = new User();
		}

		user.setId(dto.getId());
		user.setLogin(dto.getLogin());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setRole(dto.getRole());

		if (dto.getPassword() != null && dto.getPassword() != "") {
			user.setPassword(
					BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
		}

		return user;
	}

	public BookstoreDto toBookstoreDto(Bookstore store) {
		BookstoreDto dto = new BookstoreDto();

		dto.setId(store.getId());
		dto.setName(store.getName());
		dto.setEmail(store.getEmail());
		dto.setWeb(store.getWeb());

		return dto;
	}

	public Bookstore toBookstoreEntity(BookstoreDto dto)
			throws EntityNotFoundException {
		Bookstore store;
		if (Objects.nonNull(dto.getId())) {
			store = bookstoreRepository.getOne(dto.getId());

		} else {
			store = new Bookstore();
		}

		store.setId(dto.getId());
		store.setName(dto.getName());
		store.setEmail(dto.getEmail());
		store.setWeb(dto.getWeb());

		return store;
	}

	public GenreDto toGenreDto(Genre genre) {
		GenreDto dto = new GenreDto();

		dto.setId(genre.getId());
		dto.setName(genre.getName());
		dto.setDescription(genre.getDescription());

		return dto;
	}

	public Genre toGenreEntity(GenreDto dto) throws EntityNotFoundException {
		Genre genre;
		if (Objects.nonNull(dto.getId())) {
			genre = genreRepository.getOne(dto.getId());
		} else {

			genre = new Genre();
		}

		genre.setId(dto.getId());
		genre.setName(dto.getName());
		genre.setDescription(dto.getDescription());

		return genre;
	}

}
