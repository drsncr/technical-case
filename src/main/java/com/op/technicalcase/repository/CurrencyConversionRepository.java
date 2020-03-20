package com.op.technicalcase.repository;

import com.op.technicalcase.model.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CurrencyConversionRepository extends PagingAndSortingRepository<Conversion, Long> {

    @Query(value = "SELECT c FROM Conversion c WHERE (c.id = ?1 or ?1 is null) AND (c.creationDate = ?2 or ?2 is null)")
    Page<Conversion> getConversionList(Long id, LocalDate creationDate, Pageable pageable);
}
