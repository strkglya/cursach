package curs.library.service.view.authentication;

import lombok.NonNull;
import org.springframework.security.core.Authentication;

public interface LoginService {
    String login(Authentication authentication, @NonNull String username, @NonNull String password);
}
