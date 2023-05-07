package curs.library.service.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import curs.library.model.config.WebSecurityConfig;

@ConditionalOnBean(WebSecurityConfig.class)
public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String name, String password );
}
