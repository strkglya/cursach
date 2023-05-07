package curs.library.service.view.admin;

import curs.library.repository.BookRepository;
import curs.library.model.pojo.Book;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import curs.library.repository.FinesRepository;
import curs.library.repository.RequestRepository;
import curs.library.model.enums.Status;
import curs.library.model.pojo.Bill;
import curs.library.model.pojo.Request;

import java.util.Optional;

import static curs.library.service.helper.directions.Pages.ADMIN_MAIN_PAGE;

@Slf4j
@Service
public class RequestServiceImpl implements RequestService {
    private final BookRepository bookRepository;
    private final RequestRepository requestRepository;
    private final FinesRepository finesRepository;

    public RequestServiceImpl(BookRepository bookRepository, RequestRepository requestRepository, FinesRepository finesRepository) {
        this.bookRepository = bookRepository;
        this.requestRepository = requestRepository;
        this.finesRepository = finesRepository;
    }

    @Override
    public boolean approve(@NonNull String requestId, @NonNull String roomId) {
        Optional<Book> selectedRoomOp = Optional.empty();
        Optional<Request> relatedRequestOp = Optional.empty();
        boolean result;
        try {
            selectedRoomOp = bookRepository.findById(Long.valueOf(roomId));
            relatedRequestOp = requestRepository.findById(Long.valueOf(requestId));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (selectedRoomOp.isPresent() && relatedRequestOp.isPresent()) {
            Book selectedBook = selectedRoomOp.get();
            Request relatedRequest = relatedRequestOp.get();

            relatedRequest.setStatus(Status.approved);
            requestRepository.save(relatedRequest);

            Bill bill = new Bill(/*selectedBook.getPrice()*/0, true, relatedRequest, selectedBook,relatedRequest.getUser().getId());
            finesRepository.save(bill);
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    @Override
    public void cancel(@NonNull String id) {
        try {
            Optional<Request> selectedRequestOptional = requestRepository.findById(Long.valueOf(id));
            if (selectedRequestOptional.isPresent()) {
                Request selected = selectedRequestOptional.get();
                selected.setStatus(Status.cancelled);
                requestRepository.save(selected);
            }
        } catch (NumberFormatException e) {
            log.error("Impossible to cancel request with id " + id  + "\n" +
                    "Error thrown: " + e.getMessage());
        }
    }

    @Override
    public Request validSelected(@NonNull String id) {
        Request selected = null;
        try {
            Optional<Request> selectedRequestOptional = requestRepository.findById(Long.valueOf(id));
            if (selectedRequestOptional.isPresent()) {
                selected = selectedRequestOptional.get();
            }
        } catch (NumberFormatException e) {
            log.error("Impossible to select request with id " + id  + "\n" +
                    "Error thrown: " + e.getMessage());
        }
        return selected;
    }

    @Override
    public String getRequests(@NonNull Model model, @NonNull Pageable pageable) {
        Page<Request> page = requestRepository.findAll(pageable);
        model.addAttribute("page", page);
        return ADMIN_MAIN_PAGE.getCropPath();
    }

/*
    @Override
    public String showApprove(@NonNull Pageable pageable, @NonNull Model model, @NonNull Request selected) {

        model.addAttribute("selected_request", selected);

        List<Book> content = findMatchingRooms(selected);
        Page<Book> page = new PageImpl<>(content);
        model.addAttribute("page", page);

        return ADMIN_REQUEST_APPROVE_PAGE.getCropPath();
    }

    private boolean withinDateRange(@NonNull Book r, @NonNull Request selected) {
        List<Bill> bills = Lists.newArrayList(finesRepository.findAll());

        for (Bill bill : bills) {
            if (Objects.equals(bill.getBook().getId(), r.getId())) {
                if (datesOverlap(bill, selected)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean datesOverlap(@NonNull Bill b, @NonNull Request selected) {

        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");

        try {
            Date selectedStart = new Date(sdf1.parse(selected.getStartDate()).getTime());
            Date selectedEnd = new Date(sdf1.parse(selected.getEndDate()).getTime());
            Date reqStart = new Date(sdf1.parse(b.getRequest().getStartDate()).getTime());
            Date reqEnd = new Date(sdf1.parse(b.getRequest().getEndDate()).getTime());

            return !(selectedStart.after(reqEnd) || reqStart.after(selectedEnd));
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    private List<Book> findMatchingRooms(@NonNull Request selected) {
        List<Book> matching = new ArrayList<>();

        List<Book> allBooks =  Lists.newArrayList(bookRepository.findAll());

        for (Book book : allBooks) {
            if (book.getRoomClass() == selected.getRoomClass()) {
                if (book.getPlaces() >= selected.getPlaces()) {
                    if (withinDateRange(book, selected)) {
                        matching.add(book);
                    }
                }
            }

        }
        return matching;
    }*/

}
