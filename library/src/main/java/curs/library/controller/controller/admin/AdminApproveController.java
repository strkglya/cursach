package curs.library.controller.controller.admin;

import curs.library.service.view.admin.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import curs.library.model.pojo.Request;


@Controller
public class AdminApproveController {
    private final RequestService service;

    @Autowired
    public AdminApproveController(RequestService service) {
        this.service = service;
    }

    @GetMapping("/admin/")
    public String getMain(Model model,
                          @RequestParam(name ="method", required = false) String method,
                          @RequestParam(name = "id", required = false) String id,
                          @RequestParam(name = "bookId", required = false) String bookId,
                          @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
       if(method != null && id != null) {
            if (method.equals("approve")) {
                Request selected = service.validSelected(id);
                if (selected != null){
                    service.approve(id, bookId);
                }
            } else if (method.equals("cancel")) {
                service.cancel(id);
            }
        }

        return service.getRequests(model, pageable);
    }
}
