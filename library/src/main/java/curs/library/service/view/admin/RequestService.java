package curs.library.service.view.admin;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import curs.library.model.pojo.Request;

public interface RequestService {
    boolean approve(@NonNull String requestId, @NonNull String roomId);
    String getRequests(@NonNull Model model, @NonNull Pageable pageable);

    void cancel(@NonNull String id);
    Request validSelected(@NonNull String id);


    /*

    String showApprove(@NonNull Pageable pageable, @NonNull Model model, @NonNull Request selected);*/
}
