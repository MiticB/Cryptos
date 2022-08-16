package dhci.cryptos.auth;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptosUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthGroupRepository authGroupRepository;

    public CryptosUserDetailsService(UserRepository userRepository, AuthGroupRepository authGroupRepository) {
        super();
        this.userRepository = userRepository;
        this.authGroupRepository = authGroupRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found:" + username);
        }
        List<AuthGroup> authGroups = this.authGroupRepository.findByUsername(username);
        return new UserPrincipal(user, authGroups);
    }
}
