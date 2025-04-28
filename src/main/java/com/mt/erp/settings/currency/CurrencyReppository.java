package com.mt.erp.settings.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyReppository extends JpaRepository<Currency, Integer> {

}
