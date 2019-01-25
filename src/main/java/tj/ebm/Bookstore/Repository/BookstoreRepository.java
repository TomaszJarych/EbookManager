package tj.ebm.Bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tj.ebm.Bookstore.domain.Bookstore;

@Repository
public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {

}
