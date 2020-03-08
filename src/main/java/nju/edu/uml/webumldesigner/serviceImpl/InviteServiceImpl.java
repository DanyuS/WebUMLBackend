package nju.edu.uml.webumldesigner.serviceImpl;

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
        List<Integer> uidList = new ArrayList<Integer>();
        uidList.add(uid);
        userGroup.setInvitedUidList(uidList);
        List<String> invitedUserNameList = new ArrayList<String>();
        invitedUserNameList.add(user.getUserName());
        userGroup.setInvitedUserNameList(invitedUserNameList);
        //这个后期要传入！！！要修改UserGroup对象适应前后端
//        userGroup.setInvitingUidList("[]");
//        userGroup.setInvitingUserNameList("[]");
//        userGroup.setFidList("[]");
        userGroup.setCaptainId(uid);
        userGroup.setCaptainEmail(user.getUserEmail());

        UserGroup result = userGroupDao.save(userGroup);

        //user的gidList存入gid
        List<Integer> gidList = user.getGidList();
        gidList.add(result.getGid());
        user.setGidList(gidList);
        userDao.save(user);
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
            List<Integer> invitingGidList = user.getInvitingGidList();
            invitingGidList.add(gid);
            user.setInvitingGidList(invitingGidList);
            userDao.save(user);

            UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
            //受邀用户id加入邀请中小组
            List<Integer> invitingUidList = userGroup.getInvitingUidList();
            invitingUidList.add(user.getUid());
            userGroup.setInvitingUidList(invitingUidList);
            //受邀用户name加入邀请中小组
            List<String> invitingUserNameList = userGroup.getInvitingUserNameList();
            invitingUserNameList.add(user.getUserName());
            userGroup.setInvitingUserNameList(invitingUserNameList);

            userGroupDao.save(userGroup);
        }
        //发送邮件?
        //确认？
        //加入小组
        return true;
    }

    @Override
    public List<User> getAllUser(Integer uid, Integer gid) {
        List<User> userList = userDao.findAllByEditableEquals("T");

        UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
        List<Integer> uidList = userGroup.getInvitedUidList();
        List<Integer> invitingUidList = userGroup.getInvitingUidList();
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
        List<Integer> gidList = user.getGidList();
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
        List<Integer> fidList = userGroup.getFidList();
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
        List<Integer> gidList = user.getInvitingGidList();
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
        List<Integer> invitingGidList = user.getInvitingGidList();
        for (int i = 0; i < invitingGidList.size(); i++) {
            if (invitingGidList.get(i).equals(gid)) {
                invitingGidList.remove(i);
                break;
            }
        }
        userDao.save(user);
        //其次将小组记录中待邀请成功移入邀请成功
        UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
        List<Integer> invitedUidList = userGroup.getInvitedUidList();
        for (int i = 0; i < invitedUidList.size(); i++) {
            if (invitedUidList.get(i).equals(uid)) {
                invitedUidList.remove(i);
                break;
            }
        }
        List<String> invitedUserNameList = userGroup.getInvitedUserNameList();
        for (int i = 0; i < invitedUserNameList.size(); i++) {
            if (invitedUserNameList.get(i).equals(user.getUserName())) {
                invitedUserNameList.remove(i);
                break;
            }
        }
        List<Integer> invitingUidList = userGroup.getInvitingUidList();
        invitingUidList.add(uid);
        List<String> invitingUserNameList = userGroup.getInvitingUserNameList();
        invitingUserNameList.add(user.getUserName());

        userGroup.setInvitedUidList(invitedUidList);
        userGroup.setInvitedUserNameList(invitedUserNameList);
        userGroup.setInvitingUidList(invitingUidList);
        userGroup.setInvitingUserNameList(invitingUserNameList);

        userGroupDao.save(userGroup);

        return true;
    }

    @Override
    public boolean rejectInvite(Integer uid, Integer gid) {
        //首先用户的邀请小组删除该条小组记录
        User user = userDao.findUserByUid(uid);
        List<Integer> invitingGidList = user.getInvitingGidList();
        for (int i = 0; i < invitingGidList.size(); i++) {
            if (invitingGidList.get(i).equals(gid)) {
                invitingGidList.remove(i);
                break;
            }
        }
        userDao.save(user);
        //其次将小组记录中移出待邀请
        UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
        List<Integer> invitedUidList = userGroup.getInvitedUidList();
        for (int i = 0; i < invitedUidList.size(); i++) {
            if (invitedUidList.get(i).equals(uid)) {
                invitedUidList.remove(i);
                break;
            }
        }
        List<String> invitedUserNameList = userGroup.getInvitedUserNameList();
        for (int i = 0; i < invitedUserNameList.size(); i++) {
            if (invitedUserNameList.get(i).equals(user.getUserName())) {
                invitedUserNameList.remove(i);
                break;
            }
        }

        userGroup.setInvitedUidList(invitedUidList);
        userGroup.setInvitedUserNameList(invitedUserNameList);

        userGroupDao.save(userGroup);

        return true;
    }
}
