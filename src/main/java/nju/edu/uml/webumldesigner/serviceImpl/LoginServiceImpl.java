package nju.edu.uml.webumldesigner.serviceImpl;

import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.entity.User;
import nju.edu.uml.webumldesigner.service.LoginService;
import nju.edu.uml.webumldesigner.util.MD5Util;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {
    private final UserDao userDao;

    public LoginServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User isValidLogin(String userEmail, String userPassword) {
        String md5Str = MD5Util.getMD5(userPassword);
        return userDao.findUserByUserEmailAndUserPassword(userEmail, md5Str);
    }

    @Override
    public User getUserByUid(Integer uid) {
        return userDao.findUserByUid(uid);
    }
}
