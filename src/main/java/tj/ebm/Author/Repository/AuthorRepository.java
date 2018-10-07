package tj.ebm.Author.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tj.ebm.Author.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
