
package com.web.curation.dao.user;

import java.util.Optional;

import com.web.curation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, String> {
    User getUserByEmail(String email);
    User getUserByUid(String uid);
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User> findUserByEmail(String email);
    //User save(User user);
    User saveAndFlush(User user);
}
