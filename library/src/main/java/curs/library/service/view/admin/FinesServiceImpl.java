package curs.library.service.view.admin;

import curs.library.repository.BookRepository;
import curs.library.repository.FinesRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import curs.library.model.pojo.Bill;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static curs.library.service.helper.directions.Pages.ADMIN_BILLS_PAGE;

@Slf4j
@Controller
public class FinesServiceImpl implements FinesService {
    private final FinesRepository finesRepository;
    private final BookRepository bookRepository;
    @Autowired
    public FinesServiceImpl(FinesRepository finesRepository, BookRepository bookRepository) {
        this.finesRepository = finesRepository;
        this.bookRepository = bookRepository;
    }


    private List<Bill> getSubset(@NonNull Iterable<Bill> allBills, @NonNull Pageable pageable) {
        return StreamSupport.stream(allBills.spliterator(), false)
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
    }


    @Override
    public String getBills(@NonNull Pageable pageable, @NonNull Model model) {
        Iterable<Bill> allBills = finesRepository.findAll();

        model.addAttribute("total", StreamSupport.stream(allBills.spliterator(), false)
                .filter(Bill::isPaid)
                .mapToDouble(Bill::getSum)
                .sum());

        List<Bill> subset = getSubset(allBills, pageable);

        model.addAttribute("page", new PageImpl<>(subset,
                pageable,
                IterableUtils.size(allBills)));

        return ADMIN_BILLS_PAGE.getCropPath();
    }
    @Override
    public void forfeit(@NonNull long id, @NonNull long bookId) {
       var bill = finesRepository.findByRequestId(id);
       var book = bookRepository.findById(bookId).orElseThrow();
       bill.setPaid(false);
       bill.setSum(book.getPrice()*2);
       finesRepository.save(bill);
    }


}
