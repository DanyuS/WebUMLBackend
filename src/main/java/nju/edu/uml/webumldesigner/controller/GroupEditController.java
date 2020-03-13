package nju.edu.uml.webumldesigner.controller;

import com.google.gson.Gson;
import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.dao.UserGroupDao;
import nju.edu.uml.webumldesigner.entity.*;
import nju.edu.uml.webumldesigner.service.GroupEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class GroupEditController {

    private final GroupEditService groupEditService;

    public GroupEditController(GroupEditService groupEditService) {
        this.groupEditService = groupEditService;
    }

    private static ConcurrentHashMap<String, ConcurrentHashMap<String, GroupEditController>> groupEditList = new ConcurrentHashMap<String, ConcurrentHashMap<String, GroupEditController>>();

    private Session session;

    private int joinFlag = 0;

    @Autowired
    private UserGroupDao userGroupDao;

    @Autowired
    private UserDao userDao;


    @GetMapping("/createFileByGroup")
    public Integer createFileByGroup(Integer gid, String fileName, String fileType) {
        return groupEditService.createFileByGroup(gid, fileName, fileType);
    }

    //////////////////////////////////////////
    //////////////////前方高能/////////////////
    //////////////////////////////////////////

    //TODO to be modified
    public boolean createRoom(Integer gid) {
        UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
        String chatRoomName = userGroup.getGroupName();
        groupEditList.put(chatRoomName, new ConcurrentHashMap<String, GroupEditController>());
        System.out.println("-----------------当前创建房间线程数" + getConnectNum());
        return true;
    }

    @OnOpen
    public void openEdit(Session session, @PathParam(value = "message") String message) {
        this.session = session;
        if (message.contains("line")) {
            Line line = new Gson().fromJson(message, Line.class);
            User user = userDao.findUserByUid(line.getUid());
            UserGroup userGroup = userGroupDao.findUserGroupByGid(line.getGid());
            joinEdit(userGroup.getGroupName(), user.getUserName());
        } else {
            NodePic nodePic = new Gson().fromJson(message, NodePic.class);
            User user = userDao.findUserByUid(nodePic.getUid());
            UserGroup userGroup = userGroupDao.findUserGroupByGid(nodePic.getGid());
            joinEdit(userGroup.getGroupName(), user.getUserName());
        }
    }

    private void joinEdit(String groupName, String userName) {
        ConcurrentHashMap<String, GroupEditController> editGroup = groupEditList.get(groupName);
        if (editGroup.get(userName) != null) {
            this.joinFlag = 1;
        }
        editGroup.put(userName, this);//将此用户加入房间中
    }

    @OnMessage
    public void editGroup(Session session, @PathParam(value = "message") String message) throws IOException {
        //TODO 離開房間需要廣博並且全員離開需要關閉線程？？？
        if (message.contains("line")) {
            Line line = new Gson().fromJson(message, Line.class);
            User user = userDao.findUserByUid(line.getUid());
            UserGroup userGroup = userGroupDao.findUserGroupByGid(line.getGid());
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(userGroup.getGroupName());
            if (editRoom.get(user.getUserName()).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    editRoom.get(i).sendEditMessage(new Gson().toJson(line));
                }
            } else {
                editRoom.get(user.getUserName()).sendEditMessage(new Gson().toJson(line));
            }
            editRoom.get(user.getUserName()).joinFlag = 0;
        } else {
            NodePic nodePic = new Gson().fromJson(message, NodePic.class);
            User user = userDao.findUserByUid(nodePic.getUid());
            UserGroup userGroup = userGroupDao.findUserGroupByGid(nodePic.getGid());
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(userGroup.getGroupName());
            if (editRoom.get(user.getUserName()).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    editRoom.get(i).sendEditMessage(new Gson().toJson(nodePic));
                }
            } else {
                editRoom.get(user.getUserName()).sendEditMessage(new Gson().toJson(nodePic));
            }
            editRoom.get(user.getUserName()).joinFlag = 0;
        }
    }

    public void sendEditMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @OnClose
    public void closeGroupEdit() {

    }

    @OnError
    public void OnError(Throwable e) {
        System.out.println("----------------出现异常" + e);
        e.printStackTrace();
    }

    private int getConnectNum() {
        return groupEditList.size();
    }

}
