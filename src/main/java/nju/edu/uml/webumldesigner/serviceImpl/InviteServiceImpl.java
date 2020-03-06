package nju.edu.uml.webumldesigner.serviceImpl;

import com.google.gson.Gson;
import nju.edu.uml.webumldesigner.dao.FileDao;
import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.dao.UserGroupDao;
import nju.edu.uml.webumldesigner.entity.ChatRoom;
import nju.edu.uml.webumldesigner.entity.FilePic;
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

    @Autowired
    private FileDao fileDao;

    @Override
    public UserGroup createGroup(String groupName, Integer uid) {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName(groupName);

        ///ok?
        //初始化创建者加入invited列表中
        User user = userDao.findUserByUid(uid);
        List<String> uidList = new ArrayList<String>();
        uidList.add(String.valueOf(uid));
        userGroup.setInvitedUidList(new Gson().toJson(uidList));
        userGroup.setInvitedUserNameList(new Gson().toJson(user.getUserName()));
        //这个后期要传入！！！要修改UserGroup对象适应前后端
        userGroup.setInvitingUidList("[]");
        userGroup.setInvitingUserNameList("[]");
        userGroup.setFidList("[]");
        userGroup.setCaptainId(uid);

        UserGroup result = userGroupDao.save(userGroup);

        //与此同时还要创建聊天室??
        //目前对于聊天室的理解就是群名即为组名，故没必要另建组？首先去chatroom遍历gid，然后依序获取。。像那种群成员加入离开还需要不？
//        ChatRoom chatRoom = new ChatRoom();
//        chatRoom.setGid(result.getGid());

        return result;
    }

    @Override
    public boolean inviteUser(Integer gid, String userEmail) {
        //受邀用户信息添加邀请小组，接受邀请后删除
        User user = userDao.findUserByUserEmail(userEmail);
        List<Integer> invitingGidList = new Gson().fromJson(user.getInvitingGidList(), List.class);
        invitingGidList.add(gid);
        user.setInvitingGidList(new Gson().toJson(invitingGidList));
        userDao.save(user);

        UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
        //受邀用户id加入邀请中小组
        List<Integer> invitingUidList = new Gson().fromJson(userGroup.getInvitingUidList(), List.class);
        invitingUidList.add(user.getUid());
        userGroup.setInvitingUidList(new Gson().toJson(invitingUidList));
        //受邀用户name加入邀请中小组
        List<String> invitingUserNameList = new Gson().fromJson(userGroup.getInvitingUserNameList(), List.class);
        invitingUserNameList.add(user.getUserName());
        userGroup.setInvitingUserNameList(new Gson().toJson(invitingUserNameList));

        userGroupDao.save(userGroup);

        //发送邮件?
        //确认？
        //加入小组
        return true;
    }

    @Override
    public List<UserGroup> getAllGroupByUid(Integer uid) {
        //获取用户所在的全部团队
        User user = userDao.findUserByUid(uid);
        List<Integer> gidList = new Gson().fromJson(user.getGidList(), List.class);
        List<UserGroup> userGroupList = new ArrayList<UserGroup>();
        for (Integer integer : gidList) {
            UserGroup userGroup = userGroupDao.findUserGroupByGid(integer);
            userGroupList.add(userGroup);
        }
        return userGroupList;
    }

    @Override
    public List<FilePic> getAllFileByGid(Integer gid) {
        //获取全部团队文件
        UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
        List<Integer> fidList = new Gson().fromJson(userGroup.getFidList(), List.class);
        List<FilePic> filePicList = new ArrayList<FilePic>();
        for(Integer i : fidList){
            FilePic filePic = fileDao.findFilePicByFid(i);
            filePicList.add(filePic);
        }
        return filePicList;
    }

    @Override
    public List<UserGroup> getAllInvitingGroupByUid(Integer uid) {
        //查看邀请，获取所有尚未处理的（即处于邀请中的）小组
        User user = userDao.findUserByUid(uid);
        List<Integer> gidList = new Gson().fromJson(user.getInvitingGidList(), List.class);
        List<UserGroup> userGroupList = new ArrayList<UserGroup>();
        for (Integer integer : gidList) {
            UserGroup userGroup = userGroupDao.findUserGroupByGid(integer);
            userGroupList.add(userGroup);
        }
        return userGroupList;
    }
}
