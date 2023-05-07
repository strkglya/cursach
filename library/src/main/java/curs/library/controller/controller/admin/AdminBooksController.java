package curs.library.controller.controller.admin;

import curs.library.repository.BookRepository;
import curs.library.model.pojo.Book;
import curs.library.service.helper.directions.Pages;
import curs.library.service.helper.directions.Pathes;
import curs.library.service.view.admin.BooksService;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Controller
public class AdminBooksController {
    private BooksService service;
    private final BookRepository bookRepository;
    @Autowired
    public AdminBooksController(BooksService service, BookRepository bookRepository) {
        this.service = service;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/admin/rooms")
    public String getRooms(Model model,
                           @RequestParam(name = "method", required = false) String method,
                           @RequestParam(name = "id", required = false) String id,
                           @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        if (method != null && id != null && method.equals("delete")) {
            service.delete(id);
        }
        service.paginate(model, pageable);
        return Pages.ADMIN_ROOMS_PAGE.getCropPath();
    }

    @PostMapping("/admin/update-rooms")
    public String tablesAddRoom( @RequestParam("picture") String pictureURL,
                                @RequestParam("name") String name,
                                @RequestParam("author") String author,
                                 @RequestParam("genre") String genre,
                                @RequestParam("price") Double price) {
        service.save(pictureURL, name, author, genre, price);
        return "redirect:" + Pathes.ADMIN_ROOMS.getUrl();
    }


    @GetMapping("/admin/book/{id}/edit")
    public String phoneEdit(Model model, @PathVariable(value = "id") long id) {
        if(!bookRepository.existsById(id))
        {
            return "redirect:/admin/rooms";
        }
        Optional<Book> room=Optional.ofNullable(bookRepository.findById(id).orElseThrow());
        ArrayList<Book> res=new ArrayList<>();
        room.ifPresent(res::add);
        model.addAttribute("book", res);

        return Pages.ADMIN_ROOM_EDIT.getCropPath();
    }

    @PostMapping ("/admin/book/{id}/edit")
    public String phoneUpdate( @PathVariable(value = "id") long id,
                               @RequestParam("picture") String pictureURL,
                               @RequestParam("name") String name,
                               @RequestParam("author") String author,
                               @RequestParam("genre") String genre,
                               @RequestParam("price") Double price
    ) {

        Book book = bookRepository.findById(id).orElseThrow();
        book.setName(name);
        book.setPrice(price);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPicURL(pictureURL);

        bookRepository.save(book);
        return "redirect:/admin/rooms";
    }


}
