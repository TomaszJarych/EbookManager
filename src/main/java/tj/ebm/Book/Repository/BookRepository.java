package tj.ebm.Book.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tj.ebm.Book.Domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
