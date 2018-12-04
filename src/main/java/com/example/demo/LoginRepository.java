package com.example.demo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import com.example.demo.Login;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface LoginRepository extends CrudRepository<Login, Integer> {
@Transactional
@Modifying
@Query("select count(*) from Login where fbid=:userID")
public int findCount(@Param("userID") String userID);

}
