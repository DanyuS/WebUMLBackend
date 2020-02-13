package nju.edu.uml.webumldesigner.serviceImpl;

import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.entity.User;
import nju.edu.uml.webumldesigner.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserDao userDao;

    @Override
    public User isValidLogin(String userEmail, String userPassword) {
        return userDao.findUserByUserEmailAndUserPassword(userEmail, userPassword);
    }
}
