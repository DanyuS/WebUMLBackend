package nju.edu.uml.webumldesigner.serviceImpl;

import com.google.gson.Gson;
import nju.edu.uml.webumldesigner.dao.FileDao;
import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.dao.UserGroupDao;
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
        userGroup.setCaptainEmail(user.getUserEmail());

        UserGroup result = userGroupDao.save(userGroup);

        //与此同时还要创建聊天室??
        //目前对于聊天室的理解就是群名即为组名，故没必要另建组？首先去chatroom遍历gid，然后依序获取。。像那种群成员加入离开还需要不？
//        ChatRoom chatRoom = new ChatRoom();
//        chatRoom.setGid(result.getGid());

        return result;
    }

    @Override
    public boolean inviteUser(Integer gid, List<String> userEmailList) {
        for (String userEmail : userEmailList) {
            //受邀用户信息添加邀请小组，接受邀请后删除
            User user = userDao.findUserByUserEmail(userEmail);
            List<Integer> invitingGidList = transStringToList(user.getInvitingGidList());
            invitingGidList.add(gid);
            user.setInvitingGidList(new Gson().toJson(invitingGidList));
            userDao.save(user);

            UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
            //受邀用户id加入邀请中小组
            List<Integer> invitingUidList = transStringToList(userGroup.getInvitingUidList());
            invitingUidList.add(user.getUid());
            userGroup.setInvitingUidList(new Gson().toJson(invitingUidList));
            //受邀用户name加入邀请中小组
            List<String> invitingUserNameList = new Gson().fromJson(userGroup.getInvitingUserNameList(), List.class);
            invitingUserNameList.add(user.getUserName());
            userGroup.setInvitingUserNameList(new Gson().toJson(invitingUserNameList));

            userGroupDao.save(userGroup);
        }
        //发送邮件?
        //确认？
        //加入小组
        return true;
    }

    @Override
    public List<User> getAllUser(Integer uid, Integer gid) {
        List<User> userList = userDao.findAllByUidExists();

        UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
        List<Integer> uidList = transStringToList(userGroup.getInvitedUidList());
        List<Integer> invitingUidList = transStringToList(userGroup.getInvitingUidList());
        for (Integer integer : invitingUidList) {
            uidList.add(integer);
        }

        //移出已经在小组里的和正在邀请中的user
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            for (Integer integer : uidList) {
                if (integer.equals(user.getUid())) {
                    userList.remove(i);
                }
            }
        }

        return userList;
    }

    @Override
    public List<UserGroup> getAllGroupByUid(Integer uid) {
        //获取用户所在的全部团队
        User user = userDao.findUserByUid(uid);
        List<Integer> gidList = transStringToList(user.getGidList());
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
        List<Integer> fidList = transStringToList(userGroup.getFidList());
        List<FilePic> filePicList = new ArrayList<FilePic>();
        for (Integer i : fidList) {
            FilePic filePic = fileDao.findFilePicByFid(i);
            filePicList.add(filePic);
        }
        return filePicList;
    }

    @Override
    public List<UserGroup> getAllInvitingGroupByUid(Integer uid) {
        //查看邀请，获取所有尚未处理的（即处于邀请中的）小组
        User user = userDao.findUserByUid(uid);
        List<Integer> gidList = transStringToList(user.getInvitingGidList());
        List<UserGroup> userGroupList = new ArrayList<UserGroup>();
        for (Integer integer : gidList) {
            UserGroup userGroup = userGroupDao.findUserGroupByGid(integer);
            userGroupList.add(userGroup);
        }
        return userGroupList;
    }

    @Override
    public boolean acceptInvite(Integer uid, Integer gid) {
        //首先用户的邀请小组删除该条小组记录
        User user = userDao.findUserByUid(uid);
        List<Integer> invitingGidList = transStringToList(user.getInvitingGidList());
        for (int i = 0; i < invitingGidList.size(); i++) {
            if (invitingGidList.get(i).equals(gid)) {
                invitingGidList.remove(i);
                break;
            }
        }
        userDao.save(user);
        //其次将小组记录中待邀请成功移入邀请成功
        UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
        List<Integer> invitedUidList = transStringToList(userGroup.getInvitedUidList());
        for (int i = 0; i < invitedUidList.size(); i++) {
            if (invitedUidList.get(i).equals(uid)) {
                invitedUidList.remove(i);
                break;
            }
        }
        List<String> invitedUserNameList = transStringToStringList(userGroup.getInvitedUserNameList());
        for (int i = 0; i < invitedUserNameList.size(); i++) {
            if (invitedUserNameList.get(i).equals(user.getUserName())) {
                invitedUserNameList.remove(i);
                break;
            }
        }
        List<Integer> invitingUidList = transStringToList(userGroup.getInvitingUidList());
        invitingUidList.add(uid);
        List<String> invitingUserNameList = transStringToStringList(userGroup.getInvitingUserNameList());
        invitingUserNameList.add(user.getUserName());

        userGroup.setInvitedUidList(new Gson().toJson(invitedUidList));
        userGroup.setInvitedUserNameList(new Gson().toJson(invitedUserNameList));
        userGroup.setInvitingUidList(new Gson().toJson(invitingUidList));
        userGroup.setInvitingUserNameList(new Gson().toJson(invitingUserNameList));

        userGroupDao.save(userGroup);

        return true;
    }

    @Override
    public boolean rejectInvite(Integer uid, Integer gid) {
        //首先用户的邀请小组删除该条小组记录
        User user = userDao.findUserByUid(uid);
        List<Integer> invitingGidList = transStringToList(user.getInvitingGidList());
        for (int i = 0; i < invitingGidList.size(); i++) {
            if (invitingGidList.get(i).equals(gid)) {
                invitingGidList.remove(i);
                break;
            }
        }
        userDao.save(user);
        //其次将小组记录中移出待邀请
        UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
        List<Integer> invitedUidList = transStringToList(userGroup.getInvitedUidList());
        for (int i = 0; i < invitedUidList.size(); i++) {
            if (invitedUidList.get(i).equals(uid)) {
                invitedUidList.remove(i);
                break;
            }
        }
        List<String> invitedUserNameList = transStringToStringList(userGroup.getInvitedUserNameList());
        for (int i = 0; i < invitedUserNameList.size(); i++) {
            if (invitedUserNameList.get(i).equals(user.getUserName())) {
                invitedUserNameList.remove(i);
                break;
            }
        }

        userGroup.setInvitedUidList(new Gson().toJson(invitedUidList));
        userGroup.setInvitedUserNameList(new Gson().toJson(invitedUserNameList));

        userGroupDao.save(userGroup);

        return true;
    }

    private List<Integer> transStringToList(String str) {
        return new Gson().fromJson(str, List.class);
    }

    private List<String> transStringToStringList(String str) {
        return new Gson().fromJson(str, List.class);
    }

}
