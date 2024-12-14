package net.javaguides.springboot_backend.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer>{

    @Query(value = "select * from users order by user_id desc",nativeQuery = true)
    List<User> getAllUsers();
    User findByEmail(String email);
}