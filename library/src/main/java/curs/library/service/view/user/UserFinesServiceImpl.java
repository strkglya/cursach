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
import curs.library.model.pojo.Bill;
import curs.library.model.pojo.Request;
import curs.library.service.security.LibraryUserDetails;

import java.util.Optional;

@Slf4j
@Service
public class UserFinesServiceImpl implements UserFinesService {
    private final FinesRepository finesRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public UserFinesServiceImpl(FinesRepository finesRepository, RequestRepository requestRepository) {
        this.finesRepository = finesRepository;
        this.requestRepository = requestRepository;
    }

    private Optional<Bill> findBill(@NonNull String billId) {
        log.info("Attempting to find Bill with String id=" + billId);
        Optional<Bill> billOptional = Optional.empty();
        try {
            billOptional = finesRepository.findById(Long.parseLong(billId));
        } catch (Exception e) {
            log.error("Failure: Bill with id=" + billId + " wasn't found \n" +
                    "Thrown: " + e.getMessage());
        }
        return billOptional;
    }

    @Override
    public void pay(@NonNull String billId) {
        log.info("Attempting to pay Bill with id=" + billId);
        Optional<Bill> billOptional = findBill(billId);

        if (billOptional.isPresent()) {
            Bill bill = billOptional.get();
            bill.setPaid(true);
            finesRepository.save(bill);
            log.info("Success");
        } else {
            log.error("Error: Bill hasn't been paid");
        }
    }

    @Override
    public void paginate(@NonNull LibraryUserDetails principal, @NonNull Model model, @NonNull Pageable pageable) {
        Page<Bill> page = finesRepository.findAllByRequest_UserId(principal.getUser().getId(), pageable);
        model.addAttribute("page", page);
    }

    @Override
    public void cancel(@NonNull String billId) {
        log.info("Attempting to delete Bill with id=" + billId + " and corresponding Request");
        Optional<Bill> billOptional = findBill(billId);
            if (billOptional.isPresent()) {
                Bill bill = billOptional.get();
                Request request = bill.getRequest();
                finesRepository.delete(bill);
                requestRepository.delete(request);
                log.info("Success");
            } else {
                log.error("Failure: Bill with id=" + billId + " hasn't been deleted\n");
            }
    }
}
