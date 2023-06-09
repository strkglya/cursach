package curs.library.controller.controller.admin;

import curs.library.service.view.admin.FinesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@Controller
public class AdminFinesController {
    private FinesService service;

    @Autowired
    public AdminFinesController(FinesService service) {
        this.service = service;
    }

    @GetMapping("/admin/bills")
    public String getBills(Model model,
                           @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return service.getBills(pageable, model);
    }

    @PostMapping("/admin/forfeit/{reqId}/{bookId}")
    public String forfeit(@PathVariable(value = "reqId") long id,
                          @PathVariable(value = "bookId") long bookId
                          ) {
        service.forfeit(id, bookId);
        return "redirect:/admin/bills";
    }

}
