package com.mt.erp.settings.general;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralSettingReppository extends JpaRepository<GeneralSetting, Integer> {

}
