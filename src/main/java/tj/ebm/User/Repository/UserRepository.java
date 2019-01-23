package tj.ebm.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tj.ebm.User.Domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByLogin(String login);

}
