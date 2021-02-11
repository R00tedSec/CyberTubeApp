package com.cybertube.web.Users;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByUsernameIgnoreCase(String username);

    User findByEmail(String email);

    List<User> findByUsernameContaining(String author);

    User findByEmailIgnoreCase(String emailId);

}
