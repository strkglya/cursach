package curs.library.controller.controller.user;

import curs.library.repository.BookRepository;
import curs.library.model.pojo.Book;
import curs.library.service.security.LibraryUserDetails;
import curs.library.service.helper.directions.Pages;
import curs.library.service.helper.directions.Pathes;
import curs.library.service.view.admin.BooksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Controller
public class UserMainController {
    private BooksService service;
    private final BookRepository bookRepository;
    @Autowired
    public UserMainController(BooksService service, BookRepository bookRepository) {
        this.service = service;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/user/")
    public String getMain(Model model, Authentication authentication) {
        log.info("Accessing GET main controller");
        if (authentication != null) {
            LibraryUserDetails userDetails = (LibraryUserDetails)authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("books", bookRepository.findAll());
        }
        return Pathes.USER_MAIN.getCropPagePath();
    }

    @GetMapping("/user/rooms")
    public String getRooms(Model model,
                           @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {

        service.paginate(model, pageable);
        return Pages.USER_ROOMS_PAGE.getCropPath();
    }

    @GetMapping("/user/rooms/book/{id}")
    public String phoneEdit(Model model, @PathVariable(value = "id") long id) {
        if(!bookRepository.existsById(id))
        {
            return "redirect:/user/rooms";
        }
        Optional<Book> room=Optional.ofNullable(bookRepository.findById(id).orElseThrow());
        ArrayList<Book> res=new ArrayList<>();
        room.ifPresent(res::add);
        model.addAttribute("book", res);

        return Pages.USER_ROOM.getCropPath();
    }

}
