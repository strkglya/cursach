package curs.library.service.view.authentication;

import curs.library.service.helper.directions.Pathes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    @Override
    public String redirectIfAuthenticated(Authentication authentication) {
        if (authentication != null) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
                return "redirect:" + Pathes.USER_MAIN.getUrl();
            } else {
                return "redirect:" + Pathes.ADMIN_MAIN.getUrl();
            }
        }
        return null;
    }
}
