package nju.edu.uml.webumldesigner.controller;

import nju.edu.uml.webumldesigner.controller.params.InvitationParams;
import nju.edu.uml.webumldesigner.entity.FilePic;
import nju.edu.uml.webumldesigner.entity.User;
import nju.edu.uml.webumldesigner.entity.UserGroup;
import nju.edu.uml.webumldesigner.service.GroupEditService;
import nju.edu.uml.webumldesigner.service.InviteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupManageController {
    private final InviteService inviteService;
    private final GroupEditService groupEditService;

//    private ChatController chatController;

    public GroupManageController(InviteService inviteService, GroupEditService groupEditService) {
        this.inviteService = inviteService;
        this.groupEditService = groupEditService;
    }

    @GetMapping("/createGroup")
    public Integer createGroup(String groupName, Integer uid) {
        //TODO 传参问题
        UserGroup userGroup = inviteService.createGroup(groupName, uid);
        if (userGroup == null) {
            return -1;
        }
        Integer gid = userGroup.getGid();
        //创建房间
//        chatController.createRoom(gid);
        return gid;
    }

    @PostMapping("/inviteUser")
    public boolean inviteUser(@RequestBody InvitationParams invitationParams) {
        return inviteService.inviteUser(invitationParams.getGid(), invitationParams.getUserEmailList());
    }

    @GetMapping("/getAllUser")
    public List<User> getAllUser(Integer uid, Integer gid) {
        System.out.println(uid + " " + gid);
        return inviteService.getAllUser(uid, gid);
    }

    @GetMapping("/getAllGroupByUid")
    public List<UserGroup> getAllGroupByUid(Integer uid) {
        System.out.println(uid);
        List<UserGroup> userGroupList = inviteService.getAllGroupByUid(uid);
        return userGroupList;
    }

    @GetMapping("/getAllFileByGid")
    public List<FilePic> getAllFileByGid(Integer gid) {
        System.out.println(gid);
        List<FilePic> filePicList = inviteService.getAllFileByGid(gid);
        return filePicList;
    }

    @GetMapping("/getAllInvitingGroupByUid")
    public List<UserGroup> getAllInvitingGroupByUid(Integer uid) {
        System.out.println(uid);
        List<UserGroup> userGroupList = inviteService.getAllInvitingGroupByUid(uid);
        return userGroupList;
    }

    @GetMapping("/acceptInvite")
    public boolean acceptInvite(Integer uid, Integer gid) {
        System.out.println(uid + " " + gid);
        return inviteService.acceptInvite(uid, gid);
    }

    @GetMapping("/rejectInvite")
    public boolean rejectInvite(Integer uid, Integer gid) {
        System.out.println(uid + " " + gid);
        return inviteService.rejectInvite(uid, gid);
    }

    @GetMapping("/createFileByGroup")
    public Integer createFileByGroup(Integer gid, String fileName, String fileType) {
        return groupEditService.createFileByGroup(gid, fileName, fileType);
    }

    @GetMapping("/deleteFileByGroup")
    public boolean deleteFileByGroup(Integer gid, Integer fid) {
        return groupEditService.deleteFileByGroup(gid, fid);
    }

    @GetMapping("/deleteGroup")
    public boolean deleteGroup(Integer gid) {
        return inviteService.deleteGroup(gid);
    }

}
