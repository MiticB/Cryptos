package dhci.cryptos.auth;

import dhci.cryptos.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long>
{
    User findByUsername(String username);
    User findByEmail(String email);
}
