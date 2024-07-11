package com.evaza.etwitch.db;

import com.evaza.etwitch.db.entity.UserEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface UserRepository extends ListCrudRepository<UserEntity, Long> {

    // SELECT * FROM users WHERE last_name = ?
    List<UserEntity> findByLastName(String lastName);

    // SELECT * FROM users WHERE first_name = ?
    List<UserEntity> findByFirstName(String firstName);

    // SELECT * FROM users WHERE username = ?
    UserEntity findByUsername(String username);

    @Modifying
    @Query("UPDATE users SET first_name = :firstName, last_name = :lastName WHERE username = :username")
    void updateNameByUsername(String username, String firstName, String lastName);
}
