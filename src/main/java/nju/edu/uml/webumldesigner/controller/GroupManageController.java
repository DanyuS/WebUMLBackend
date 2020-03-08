package nju.edu.uml.webumldesigner.controller;

import com.google.gson.Gson;
import nju.edu.uml.webumldesigner.entity.FilePic;
import nju.edu.uml.webumldesigner.entity.User;
import nju.edu.uml.webumldesigner.entity.UserGroup;
import nju.edu.uml.webumldesigner.service.InviteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupManageController {
    private final InviteService inviteService;

    public GroupManageController(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    @PostMapping("/createGroup")
    public Integer createGroup(String groupName, Integer uid) {
        //TODO 传参问题
        return inviteService.createGroup(groupName, uid).getGid();
    }

    @PostMapping("/inviteUser")
    public boolean inviteUser(Integer gid, List<String> userEmailList) {
        return inviteService.inviteUser(gid, userEmailList);
    }

    @PostMapping("/getAllUser")
    public List<User> getAllUser(Integer uid, Integer gid){
        return inviteService.getAllUser(uid, gid);
    }

    @PostMapping("/getAllGroupByUid")
    public List<UserGroup> getAllGroupByUid(Integer uid) {
        List<UserGroup> userGroupList = inviteService.getAllGroupByUid(uid);
        return userGroupList;
    }

    @PostMapping("/getAllFileByGid")
    public List<FilePic> getAllFileByGid(Integer gid) {
        List<FilePic> filePicList = inviteService.getAllFileByGid(gid);
        return filePicList;
    }

    @PostMapping("/getAllInvitingGroupByUid")
    public List<UserGroup> getAllInvitingGroupByUid(Integer uid) {
        List<UserGroup> userGroupList = inviteService.getAllInvitingGroupByUid(uid);
        return userGroupList;
    }

    @PostMapping("/acceptInvite")
    public boolean acceptInvite(Integer uid, Integer gid) {
        return inviteService.acceptInvite(uid, gid);
    }

    @PostMapping("/rejectInvite")
    public boolean rejectInvite(Integer uid, Integer gid) {
        return inviteService.rejectInvite(uid, gid);
    }

}
