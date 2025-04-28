package com.mt.erp.settings.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockSettingRepository extends JpaRepository<StockSetting, Integer>{
	StockSetting findByAppliedByDefaultTrue();
}
