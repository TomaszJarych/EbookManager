package tj.ebm.Book.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tj.ebm.Book.Domain.Book;
import tj.ebm.Genre.domain.Genre;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findAllBooksByAuthorsId(Long id);

	List<Book> findAllBooksByGenresIn(List<Genre> genres);

	List<Book> findAllBooksByGenresId(Long id);

	List<Book> findAllBooksByOwnerIdOrderByTitleAsc(Long id);

	List<Book> findAllBooksByOwnerIdOrderByCreatedDesc(Long id);

	List<Book> findAllBooksByOwnerId(Long id);

}
