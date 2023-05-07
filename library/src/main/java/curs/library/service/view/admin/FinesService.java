package curs.library.service.view.admin;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public interface FinesService {
    String getBills(@NonNull Pageable pageable, @NonNull Model model);
    void forfeit(@NonNull long id, @NonNull long bookId);
}
