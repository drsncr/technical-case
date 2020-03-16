package com.op.technicalcase.repository;

import com.op.technicalcase.model.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyConversionRepository extends JpaRepository<Conversion, Long> {
}
