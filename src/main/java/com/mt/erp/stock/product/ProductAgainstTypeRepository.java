package com.mt.erp.stock.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAgainstTypeRepository extends JpaRepository<ProductAgainstType, Integer>{

}
