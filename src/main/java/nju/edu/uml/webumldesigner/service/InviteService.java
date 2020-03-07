package nju.edu.uml.webumldesigner.service;

import nju.edu.uml.webumldesigner.entity.FilePic;
import nju.edu.uml.webumldesigner.entity.User;
import nju.edu.uml.webumldesigner.entity.UserGroup;

import java.util.List;

public interface InviteService {
    public UserGroup createGroup(String groupName, Integer uid);

    public boolean inviteUser(Integer gid, List<String> userEmailList);

    public List<User> getAllUser(Integer uid, Integer gid);

    public List<UserGroup> getAllGroupByUid(Integer uid);

    public List<FilePic> getAllFileByGid(Integer gid);

    public List<UserGroup> getAllInvitingGroupByUid(Integer uid);

    public boolean acceptInvite(Integer uid, Integer gid);

    public boolean rejectInvite(Integer uid, Integer gid);
}
