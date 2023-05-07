package curs.library.repository;

import curs.library.model.pojo.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FinesRepository extends PagingAndSortingRepository<Bill, Long> {
    Bill findByRequestId(Long request_id);
    Page<Bill> findAllByRequest_UserId(Long id, Pageable pageable);
}
