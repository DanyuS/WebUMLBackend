package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    User findUserByUserEmailAndUserPassword(String userEmail, String userPassword);

    User findUserByUid(Integer uid);

    User findUserByUserEmail(String UserEmail);

    List<User> findAllByUidExists();
}
