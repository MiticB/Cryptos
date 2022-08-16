package dhci.cryptos.repositories;

import dhci.cryptos.auth.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserGroupRepository extends JpaRepository<AuthGroup, Long> {
}
