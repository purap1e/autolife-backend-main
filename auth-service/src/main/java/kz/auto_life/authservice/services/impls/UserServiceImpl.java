package kz.auto_life.authservice.services.impls;

import kz.auto_life.authservice.entities.User;
import kz.auto_life.authservice.exceptions.EmailExistsException;
import kz.auto_life.authservice.repositories.UserRepository;
import kz.auto_life.authservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        log.info("Saving new user {} to the database", user.getEmail());
        return userRepository.save(user);
    }

    private boolean usernameExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public User register(String email, String password) {
        if (usernameExist(email)) {
            throw new EmailExistsException(email);
        } else {
//            List<Role> roles = new ArrayList<>();
//            Role role = roleRepository.findByName("ROLE_USER");
//            roles.add(role);
            User user = User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password)).build();
//                    .roles(roles).build();
            return save(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.info("This email: {} does not exist", email);
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("Email {} found in the database", email);
        }
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
