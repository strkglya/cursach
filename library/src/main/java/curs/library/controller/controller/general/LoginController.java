package curs.library.controller.controller.general;

import curs.library.service.helper.directions.Pages;
import curs.library.service.view.authentication.AuthenticationService;
import curs.library.service.view.authentication.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
public class LoginController {
    private final LoginService service;
    private final AuthenticationService authenticationService;

    public LoginController(LoginService service, AuthenticationService authenticationService) {
        this.service = service;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    public String loginGet(Authentication authentication) {
        String redirect = authenticationService.redirectIfAuthenticated(authentication);
        return (redirect != null) ? redirect : Pages.LOGIN_PAGE.getCropPath();
    }

    @PostMapping("/login")
    public String loginPost(Authentication authentication,
                            @RequestParam(name = "username") String username,
                            @RequestParam(name = "password") String password) {
        return service.login(authentication, username, password);
    }
}
