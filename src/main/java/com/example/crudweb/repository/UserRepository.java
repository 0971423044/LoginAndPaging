package com.example.crudweb.repository;

import com.example.crudweb.entity.User;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User,Long> {

    @Query("SELECT u from User u  WHERE u.username = :username")
    public User getUsersByUsername(@Param("username") String username);
}
