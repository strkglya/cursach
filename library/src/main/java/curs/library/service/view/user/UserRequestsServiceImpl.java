package curs.library.service.view.user;

import curs.library.repository.FinesRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import curs.library.repository.RequestRepository;
import curs.library.repository.UserRepository;
import curs.library.model.enums.Status;
import curs.library.model.exceptions.DateFormatException;
import curs.library.model.pojo.Bill;
import curs.library.model.pojo.Request;
import curs.library.model.pojo.User;
import curs.library.service.security.LibraryUserDetails;
import curs.library.service.helper.StringConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserRequestsServiceImpl implements UserRequestsService{
    private final RequestRepository requestRepository;
    private final FinesRepository finesRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserRequestsServiceImpl(@NonNull RequestRepository requestDAO, @NonNull FinesRepository billDAO, UserRepository userRepository) {
        this.requestRepository = requestDAO;
        this.finesRepository = billDAO;
        this.userRepository = userRepository;
    }

    @Override
    public void paginate(@NonNull LibraryUserDetails principal, @NonNull Model model, @NonNull Pageable pageable) {
        Page<Request> page = requestRepository.findAllByUser(principal.getUser(), pageable);
        model.addAttribute("page", page);
    }

    @Override
    public void cancel(@NonNull String requestId) {
        Optional<Long> id = StringConverter.parseLong(requestId);
        if (id.isPresent()) {
            Bill relatedBill = finesRepository.findByRequestId(id.get());
            if (relatedBill != null) {
                finesRepository.delete(relatedBill);
            }
            requestRepository.deleteById(id.get());
        }
    }


    @Override
    public void newRequest(@NonNull LibraryUserDetails principal, @NonNull long bookID, @NonNull String bookName) {
        try {

            Optional<User> name = userRepository.findByName(principal.getUsername());
            if (name.isPresent()) {
                Request newRequest = new Request(name.get(),
                        bookID,
                        bookName,
                        Status.waitingForApproval);
                requestRepository.save(newRequest);
            }
        } catch (Exception e) {
            log.error("Impossible to create request object: wrong input format;");
            log.error(e.getMessage());
        }
    }

    private List<String> parseDates(@NonNull String daterange) throws DateFormatException, ParseException {
        List<String> result = new ArrayList<>();
        String[] dates = daterange.split(" - ");

        if (dates.length != 2) {
            throw new DateFormatException("Error parsing date string: " + daterange);
        }

        for (String date : dates) {
            formatTester(date);
            result.add(date);
        }
        return result;
    }

    private void formatTester(@NonNull String s) throws ParseException {
        new SimpleDateFormat("mm/dd/yyyy").parse(s);
    }
}
