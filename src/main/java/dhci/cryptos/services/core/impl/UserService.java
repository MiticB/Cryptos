package dhci.cryptos.services.core.impl;

import dhci.cryptos.auth.AuthGroup;
import dhci.cryptos.auth.User;
import dhci.cryptos.dto.UserDto;
import dhci.cryptos.auth.AuthGroupRepository;
import dhci.cryptos.auth.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthGroupRepository authGroupRepository;

    public void createUser(UserDto userDto) throws IllegalStateException {

        if (null != userRepository.findByUsername(userDto.getUsername())) {
            throw new IllegalStateException("There is already a user with the name " + userDto.getUsername());
        } else if (null != userRepository.findByEmail(userDto.getEmail())) {
            throw new IllegalStateException("There is already a user with the email " + userDto.getEmail());
        }
        String username = userDto.getUsername();
        String password = new BCryptPasswordEncoder(11).encode(userDto.getPassword());
        String name = userDto.getName();
        String surname = userDto.getSurname();
        String email = userDto.getEmail();
        log.info("Getting image");
        log.info("about to upload");
        String imgurl = userDto.getImgurl();
        LocalDate date = LocalDate.now();
        User user = new User(username, password, name, surname, email, imgurl, date);

        AuthGroup group = new AuthGroup();

        group.setUsername(userDto.getUsername());
        group.setAuthgroup("USER");

        userRepository.save(user);
        authGroupRepository.save(group);
    }

    public void update(User user) {
        User current = userRepository.findByUsername(user.getUsername());

        current.setName(user.getName());
        current.setSurname(user.getSurname());
        current.setEmail(user.getEmail());
        current.setImgurl(user.getImgurl());

        userRepository.save(current);
    }

    public void patch(User user) {
        User current = userRepository.findByUsername(user.getUsername());

        current.setDetails(user.getDetails());

        userRepository.save(current);
    }

    public User getByUsername(String username){
        return this.userRepository.findByUsername(username);
    }

    public User getById(Long id){
        return this.userRepository.findById(id).get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        AuthGroup authGroup = (AuthGroup) this.authGroupRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Stream.of(new SimpleGrantedAuthority("ROLE_"+        authGroup.getAuthgroup())).collect(Collectors.toList()));


    }
}
