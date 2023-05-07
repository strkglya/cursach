package curs.library.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import curs.library.repository.UserRepository;
import curs.library.model.config.WebSecurityConfig;

@ConditionalOnBean(WebSecurityConfig.class)
@Service
public class LibraryUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public LibraryUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new LibraryUserDetails(userRepository.findByName(s)
                 .orElseThrow(IllegalArgumentException::new));
    }
}
