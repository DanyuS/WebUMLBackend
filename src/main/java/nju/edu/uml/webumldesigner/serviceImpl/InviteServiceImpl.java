package nju.edu.uml.webumldesigner.serviceImpl;

import com.google.gson.Gson;
import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.dao.UserGroupDao;
import nju.edu.uml.webumldesigner.entity.User;
import nju.edu.uml.webumldesigner.entity.UserGroup;
import nju.edu.uml.webumldesigner.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InviteServiceImpl implements InviteService {
    @Autowired
    private UserGroupDao userGroupDao;

    @Autowired
    private UserDao userDao;

    @Override
    public UserGroup createGroup(String groupName, Integer uid) {
        UserGroup userGroup =new UserGroup();
        userGroup.setGroupName(groupName);
        List<String> uidList = new ArrayList<String>();
        uidList.add(String.valueOf(uid));
        String uList = new Gson().toJson(uidList);
        ///ok?
        userGroup.setUidList(uList);
        userGroup.setFidList("[]");
        return userGroupDao.save(userGroup);
    }

    @Override
    public User inviteUser(String userEmail) {
        User user = userDao.findUserByUserEmail(userEmail);
        //发送邮件
        //确认？
        //加入小组
        return null;
    }
}
