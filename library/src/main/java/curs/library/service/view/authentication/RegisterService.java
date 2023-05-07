package curs.library.service.view.authentication;

import lombok.NonNull;

public interface RegisterService {
    String register(@NonNull String name, @NonNull String email, @NonNull String pass);
}
