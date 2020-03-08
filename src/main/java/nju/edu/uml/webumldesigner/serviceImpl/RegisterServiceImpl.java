package nju.edu.uml.webumldesigner.serviceImpl;

import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.entity.User;
import nju.edu.uml.webumldesigner.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean userRegister(String userName, String userEmail, String userPassword) {
        User user = new User();
        user.setUserName(userName);
        user.setUserEmail(userEmail);
        user.setUserPassword(userPassword);
        user.setEditable("T");
        user.setFidList("[]");
//        user.setGidList("[]");
//        user.setInvitingGidList("[]");

        String num = String.valueOf(userDao.count() + 1);
        user.setUserId("u"+num);
        User result = userDao.save(user);
        if (result.getUid() > 0) {
            return true;
        }
        return false;
    }
}
