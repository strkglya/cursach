package curs.library.service.view.admin;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public interface BooksService {
    void delete(@NonNull String id);

    public void save(@NonNull String pictureURL, @NonNull String name, @NonNull String author, @NonNull String genre,
                     @NonNull Double price);

    void paginate(@NonNull Model model, @NonNull Pageable pageable);
}
