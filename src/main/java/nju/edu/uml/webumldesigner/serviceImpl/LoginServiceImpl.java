package nju.edu.uml.webumldesigner.serviceImpl;

import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.entity.User;
import nju.edu.uml.webumldesigner.service.LoginService;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {
    private final UserDao userDao;

    public LoginServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User isValidLogin(String userEmail, String userPassword) {
        return userDao.findUserByUserEmailAndUserPassword(userEmail, userPassword);
    }

    @Override
    public User getUserByUid(Integer uid) {
        return userDao.findUserByUid(uid);
    }
}
