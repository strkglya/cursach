package curs.library.controller.controller.admin;

import curs.library.service.helper.directions.Pathes;
import curs.library.service.view.admin.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import curs.library.model.enums.Role;


@Slf4j
@Controller
public class AdminUsersController {
    private final UsersService service;

    @Autowired
    public AdminUsersController(UsersService service) {
        this.service = service;
    }

    @PostMapping("/admin/users-update")
    public String userUpdatePost(@RequestParam(name = "name") String name,
                                 @RequestParam(name = "mail") String email,
                                 @RequestParam(name="pass") String pass,
                                 @RequestParam(name="role") Role role) {
        service.newUser(name, email, pass, role);
        return "redirect:" + Pathes.ADMIN_USERS.getUrl();
    }

    @GetMapping("/admin/users")
    public String getUsers(Model model,
                           @RequestParam(name = "method", required = false) String method,
                           @RequestParam(name = "id", required = false) String id,
                           @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable ) {
        if(method != null && id != null) {
            if (method.equals("remove")) {
                service.delete(id);
            } else if (method.equals("privilege_a") || method.equals("privilege_u")){
                service.changePrivilege(id, method);
            }
        }

        return service.getUsers(model, pageable);
    }
}
