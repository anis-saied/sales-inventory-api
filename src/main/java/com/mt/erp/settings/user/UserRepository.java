package com.mt.erp.settings.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value ="select * from USER u where u.email = ?1 and u.password = ?2",nativeQuery = true)
	User findByEmailAndPassword(String email, String password);

}
