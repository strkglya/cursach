package curs.library.service.view.user;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import curs.library.service.security.LibraryUserDetails;

public interface UserFinesService {
    void pay(@NonNull String billId);

    void paginate(@NonNull LibraryUserDetails principal, @NonNull Model model, @NonNull Pageable pageable);

    void cancel(@NonNull String requestId);
}
