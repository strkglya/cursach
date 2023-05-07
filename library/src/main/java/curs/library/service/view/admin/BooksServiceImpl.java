package curs.library.service.view.admin;

import curs.library.repository.BookRepository;
import curs.library.model.pojo.Book;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Optional;

import static curs.library.service.helper.StringConverter.parseLong;

@Slf4j
@Controller
public class BooksServiceImpl implements BooksService {
    private final BookRepository bookRepository;

    @Autowired
    public BooksServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void delete(@NonNull String idString) {
        Optional<Long> id = parseLong(idString);
        if (id.isPresent()) {
            Optional<Book> room = bookRepository.findById(id.get());
            room.ifPresent(bookRepository::delete);
        }
    }

    @Override
    public void save(@NonNull String pictureURL, @NonNull String name, @NonNull String author, @NonNull String genre,
                     @NonNull Double price) {
        bookRepository.save(new Book(name, author, genre, pictureURL, price));
    }

    @Override
    public void paginate(@NonNull Model model, @NonNull Pageable pageable) {
        model.addAttribute("page", bookRepository.findAll(pageable));
    }
}
