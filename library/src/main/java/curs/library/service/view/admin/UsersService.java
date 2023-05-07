package curs.library.service.view.admin;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import curs.library.model.enums.Role;

public interface UsersService {

    void newUser(@NonNull String name, @NonNull String email, @NonNull String pass,
                 @NonNull Role role);

    void delete(@NonNull String id);

    void changePrivilege(@NonNull String id, String method);

    String getUsers(@NonNull Model model, @NonNull Pageable pageable);
}
