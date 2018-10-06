package tj.ebm.Genre.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tj.ebm.Genre.domain.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
