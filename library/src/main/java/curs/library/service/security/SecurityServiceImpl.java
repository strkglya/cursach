package curs.library.service.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import curs.library.model.config.WebSecurityConfig;

@ConditionalOnBean(WebSecurityConfig.class)
@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public String findLoggedInUsername() {
        return null;
    }

    @Override
    public void autoLogin(String name, String password) {

    }
}
