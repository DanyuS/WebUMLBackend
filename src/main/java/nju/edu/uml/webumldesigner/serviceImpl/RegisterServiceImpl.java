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
    public boolean userRegister(String userName, String userEmail, String userPassword, String code) {
        User user = userDao.findUserByUserEmail(userEmail);
        //验证码错误
        if(code==null || !code.equals(user.getCode())){
            return false;
        }
        //已注册
        if(user.getUserPassword()!=null){
            return true;
        }
        //初始化用户
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setEditable("T");
        user.setFidList("[]");
//        user.setGidList("[]");
//        user.setInvitingGidList("[]");
        User result = userDao.save(user);
        if (result.getUid() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String resetPwd(String userEmail, String userPassword, String code) {
        User user = userDao.findUserByUserEmail(userEmail);
        //验证码错误
        if(user== null || user.getUserPassword()==null){
            return "用户未注册";
        }
        else if(code==null || !code.equals(user.getFindPwdCode())){
            return "验证码错误";
        }
        else if(!user.getPwdCodeValid()){
            return "验证码过期";
        }
        else {
            user.setPwdCodeValid(false);
            user.setUserPassword(userPassword);
            userDao.save(user);
            return "密码已重置";
        }
    }
}
