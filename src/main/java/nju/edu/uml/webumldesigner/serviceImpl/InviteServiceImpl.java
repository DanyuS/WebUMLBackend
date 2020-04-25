package nju.edu.uml.webumldesigner.serviceImpl;

import nju.edu.uml.webumldesigner.dao.FileDao;
import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.dao.UserGroupDao;
import nju.edu.uml.webumldesigner.entity.FilePic;
import nju.edu.uml.webumldesigner.entity.User;
import nju.edu.uml.webumldesigner.entity.UserGroup;
import nju.edu.uml.webumldesigner.service.InviteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InviteServiceImpl implements InviteService {
    private final UserGroupDao userGroupDao;

    private final UserDao userDao;

    private final FileDao fileDao;

    public InviteServiceImpl(UserGroupDao userGroupDao, UserDao userDao, FileDao fileDao) {
        this.userGroupDao = userGroupDao;
        this.userDao = userDao;
        this.fileDao = fileDao;
    }

    @Override
    public UserGroup createGroup(String groupName, Integer uid) {
        List<UserGroup> userGroupList = userGroupDao.findAllBy();
        for (UserGroup userGroup : userGroupList) {
            if (userGroup.getGroupName().equals(groupName)) {
                return null;
            }
        }

        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName(groupName);

        ///ok?
        //初始化创建者加入invited列表中
        User user = userDao.findUserByUid(uid);
        List<User> userList = new ArrayList<User>();
        userList.add(user);
        userGroup.setInvitedUserList(userList);
//        List<Integer> uidList = new ArrayList<Integer>();
//        uidList.add(uid);
//        userGroup.setInvitedUidList(uidList);
//        List<String> invitedUserNameList = new ArrayList<String>();
//        invitedUserNameList.add(user.getUserName());
//        userGroup.setInvitedUserNameList(invitedUserNameList);
        //这个后期要传入！！！要修改UserGroup对象适应前后端
//        userGroup.setInvitingUidList("[]");
//        userGroup.setInvitingUserNameList("[]");
//        userGroup.setFidList("[]");
        userGroup.setCaptainId(uid);
        userGroup.setCaptainEmail(user.getUserEmail());

        userGroup.setIsDeleted("F");

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
    public boolean deleteGroup(Integer gid) {
        UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
        userGroup.setIsDeleted("T");

        //万一小组文件没有清干净
        List<Integer> fidList= userGroup.getFidList();
        if(fidList.size()!=0){
            //////////right??
            fidList.clear();
        }
        userGroup.setFidList(fidList);

        List<User> userList = userGroup.getInvitedUserList();
        for (User user : userList) {
            List<Integer> gidList = user.getGidList();
            for (int i = 0; i < gidList.size(); i++) {
                if (gidList.get(i).equals(gid)) {
                    gidList.remove(i);
                    break;
                }
            }
            user.setGidList(gidList);
            userDao.save(user);
        }

        userGroupDao.save(userGroup);

        return true;
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
            //受邀用户加入邀请中小组
            List<User> invitingUserList = userGroup.getInvitingUserList();
            invitingUserList.add(user);
            userGroup.setInvitingUserList(invitingUserList);
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
        List<User> invitedUserList = userGroup.getInvitedUserList();
        List<User> invitingUserList = userGroup.getInvitingUserList();
        invitedUserList.addAll(invitingUserList);

        //移出已经在小组里的和正在邀请中的user
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            for (User u : invitedUserList) {
                if (u.getUid().equals(user.getUid())) {
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
        user.setInvitingGidList(invitingGidList);

        List<Integer> gidList = user.getGidList();
        gidList.add(gid);
        user.setGidList(gidList);
        userDao.save(user);
        //其次将小组记录中待邀请成功移入邀请成功
        UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
        List<User> invitedUserList = userGroup.getInvitedUserList();
        invitedUserList.add(user);

        //最后inviting小组删除记录
        List<User> invitingUserList = userGroup.getInvitingUserList();
        for (int i = 0; i < invitingUserList.size(); i++) {
            if (invitingUserList.get(i).getUid().equals(uid)) {
                invitingUserList.remove(i);
                break;
            }
        }

        List<User> invitingUserList2 = new ArrayList<User>();
        for (User value : invitingUserList) {
            invitingUserList2.add(value);
        }

        userGroup.setInvitingUserList(invitedUserList);
        userGroup.setInvitingUserList(invitingUserList2);

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
        user.setInvitingGidList(invitingGidList);
        userDao.save(user);
        //其次将小组记录中移出待邀请
        UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
        List<User> invitingUserList = userGroup.getInvitingUserList();
        for (int i = 0; i < invitingUserList.size(); i++) {
            if (invitingUserList.get(i).getUid().equals(uid)) {
                invitingUserList.remove(i);
                break;
            }
        }

        List<User> invitingUserList2 = new ArrayList<User>();
        for (User value : invitingUserList) {
            invitingUserList2.add(value);
        }
        userGroup.setInvitingUserList(invitingUserList2);

        userGroupDao.save(userGroup);

        return true;
    }

    @Override
    public UserGroup getUserGroupByGid(Integer gid) {
        return userGroupDao.findUserGroupByGid(gid);
    }

    @Override
    public List<UserGroup> getAllUserGroup() {
        return userGroupDao.findAllBy();
    }
}
