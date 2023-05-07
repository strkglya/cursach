package curs.library.controller.controller.user;

import curs.library.service.security.LibraryUserDetails;
import curs.library.service.helper.directions.Pathes;
import curs.library.service.view.user.UserRequestsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
public class UserRequestsController {
    private final UserRequestsService service;

    @Autowired
    public UserRequestsController(UserRequestsService service) { this.service = service; }

    @GetMapping("/user/my-requests")
    public String getRequests(Authentication authentication,
                              @RequestParam(value = "method", required = false) String method,
                              @RequestParam(value = "req_id", required = false) String id,
                              @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                              Model model) {
        if (method != null && id != null && method.equals("cancel")) {
                service.cancel(id);
        }

        if (authentication != null) {
            service.paginate((LibraryUserDetails)authentication.getPrincipal(), model, pageable);
        } else {
            log.error("Authentication object ir null: unauthorized access!");
        }
        return Pathes.USER_REQUESTS.getCropPagePath();
    }

    @PostMapping("/user/make-request")
    public String makeRequest(Authentication authentication,
                              @RequestParam("bookID") long bookID,
                              @RequestParam("bookName") String bookName
                              ) {
        log.info("Retrieving data from request...");
        if (authentication != null) {
            service.newRequest((LibraryUserDetails)authentication.getPrincipal(), bookID, bookName);
        } else {
            log.error("Authentication object ir null: unauthorized access!");
        }


        return "redirect:" + Pathes.USER_MAIN.getUrl();
    }

}