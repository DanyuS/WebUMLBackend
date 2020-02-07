package nju.edu.uml.webumldesigner.service;

import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.daoImpl.UserDaoImpl;
import nju.edu.uml.webumldesigner.repository.User;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private UserDao userDao = new UserDaoImpl();

    public User isValidLogin(String userName, String userPassword) {
        return userDao.isValidLogin(userName, userPassword);
    }
}
