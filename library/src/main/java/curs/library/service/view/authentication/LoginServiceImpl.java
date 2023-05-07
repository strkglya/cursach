package curs.library.service.view.authentication;

import curs.library.service.helper.directions.Pathes;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import curs.library.repository.UserRepository;
import curs.library.model.enums.Role;
import curs.library.model.pojo.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public String login(Authentication authentication, @NonNull String username, @NonNull String password) {

        if (authentication != null) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
                return "redirect:" + Pathes.USER_MAIN.getUrl();
            } else {
                return "redirect:" + Pathes.ADMIN_MAIN.getUrl();
            }
        }
        Optional<User> user = Optional.empty();
        try {
            user = userRepository.findByName(username);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (!user.isPresent()) {
            return "redirect:" + Pathes.LOGIN.getUrl();
        } else if (encoder.matches(password, user.get().getPasswordEncoded())) {
            try {
                User u = user.get();
                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpSession session = attr.getRequest().getSession(true);
                session.setAttribute("user", u);
                //LocaleContextHolder.setLocale(new Locale(u.getLanguage().name()));
                if (u.getRole() == Role.USER) {
                    return "redirect:" + Pathes.USER_MAIN.getUrl();
                } else if (u.getRole() == Role.ADMIN) {
                    return "redirect:" + Pathes.ADMIN_MAIN.getUrl();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        return "redirect:" + Pathes.LOGIN.getUrl();
    }
}
