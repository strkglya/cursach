package curs.library.service.view.authentication;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    String redirectIfAuthenticated(Authentication authentication);
}
