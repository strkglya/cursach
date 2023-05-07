package curs.library.controller.controller.user;

import curs.library.service.security.LibraryUserDetails;
import curs.library.service.helper.directions.Pathes;
import curs.library.service.view.user.UserFinesService;
import lombok.extern.slf4j.Slf4j;
//import org.apache.tomcat.helper.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class UserFinesController {

    private final UserFinesService service;

    @Autowired
    public UserFinesController(UserFinesService service) {
        this.service = service;
    }

    @GetMapping("/user/my-bills")
    public String getBills(Authentication authentication,
                           @RequestParam(value = "method", required = false) String method,
                           @RequestParam(value = "id", required = false) String billId,
                           @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                           Model model) {

        log.info("Accessing getBills method (/user/my-bills)." +
                "Parameters : method (method)='" + method +  "', "
                                  + "bill id (id)='" + billId + "'");

        if (method != null && billId != null) {
            if (method.equals("cancel")) {
                service.cancel(billId);
            } else if (method.equals("pay")) {
                service.pay(billId);
            }
        }

        if (authentication != null) {
            service.paginate((LibraryUserDetails)authentication.getPrincipal(), model, pageable);
        } else {
            log.error("Authentication object ir null: unauthorized access!");
        }

        log.info("Success -  getBills method (/user/my-bills). Returning url " + Pathes.USER_BILLS.getCropPagePath());
        return Pathes.USER_BILLS.getCropPagePath();
    }
}
