package nju.edu.uml.webumldesigner.controller;

import com.google.gson.Gson;
import nju.edu.uml.webumldesigner.controller.params.*;
import nju.edu.uml.webumldesigner.entity.*;
import nju.edu.uml.webumldesigner.service.EditService;
import nju.edu.uml.webumldesigner.service.InviteService;
import nju.edu.uml.webumldesigner.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

//@RestController
@ServerEndpoint("/groupEdit/{message}")
@Component
public class GroupEditController {
    private static EditService editService;
    private static LoginService loginService;
    private static InviteService inviteService;

    @Autowired
    public void setEditService(EditService editService) {
        GroupEditController.editService = editService;
    }

    @Autowired
    public void setLoginService(LoginService loginService) {
        GroupEditController.loginService = loginService;
    }

    @Autowired
    public void setInviteService(InviteService inviteService) {
        GroupEditController.inviteService = inviteService;
    }

    private static ConcurrentHashMap<String, ConcurrentHashMap<String, GroupEditController>> groupEditList = new ConcurrentHashMap<String, ConcurrentHashMap<String, GroupEditController>>();

    private Session session;

    private int joinFlag = 0;

    //////////////////////////////////////////
    //////////////////前方高能/////////////////
    //////////////////////////////////////////

    //TODO to be modified
    public boolean createRoom(Integer gid, Integer fid) {
        UserGroup userGroup = inviteService.getUserGroupByGid(gid);
        String chatRoomName = userGroup.getGroupName() + "_" + fid;
        groupEditList.put(chatRoomName, new ConcurrentHashMap<String, GroupEditController>());
        System.out.println("-----------------当前创建房间线程数" + getConnectNum());
        return true;
    }

    @OnOpen
    public void openEdit(Session session, @PathParam(value = "message") String message) {
        this.session = session;
        if (!message.contains("{")) {
            message = "{" + message + "}";
        }
        IdParams idParams = new Gson().fromJson(message, IdParams.class);
        if (!idParams.getGid().equals(-1)) {
            UserGroup userGroup = inviteService.getUserGroupByGid(idParams.getGid());
            User user = loginService.getUserByUid(idParams.getUid());
            String chatRoomName = userGroup.getGroupName() + "_" + idParams.getFid();
            if (groupEditList.get(chatRoomName) == null) {
                createRoom(idParams.getGid(), idParams.getFid());
            }
            joinEdit(chatRoomName, user.getUserName());
        }
    }

    private void joinEdit(String groupName, String userName) {
        ConcurrentHashMap<String, GroupEditController> editGroup = groupEditList.get(groupName);
        if (editGroup.get(userName) != null) {
            //表明用户是退出后重新进入的
            this.joinFlag = 1;
        }
        editGroup.put(userName, this);//将此用户加入房间中
    }

    @OnMessage
    public void editGroup(Session session, String message) throws IOException {
        //TODO 離開房間需要廣博並且全員離開需要關閉線程？？？
        if (!message.contains("{")) {
            message = "{" + message + "}";
        }
        GroupEditParams groupEditParams = new Gson().fromJson(message, GroupEditParams.class);
        String editMethod = groupEditParams.getEditMethod();
        switch (editMethod) {
            case "Add":
                editAddGroup(groupEditParams);
                break;
            case "Update":
                editUpdateGroup(groupEditParams);
                break;
            case "Delete":
                editDeleteGroup(groupEditParams);
                break;
//            case "Leave":
//                leaveRoom(groupEditParams);
//                break;
            default:
                break;
        }
        //每一次编辑都遍历所有组是否有需要清空的线程
//        leaveRoom();
    }

    public void editAddGroup(GroupEditParams groupEditParams) throws IOException {
        if (groupEditParams.getLineParams() != null) {
            LineParams lineParams = groupEditParams.getLineParams();
            //並且還要保存到數據庫
            Integer lid = editService.addLine(lineParams);
            groupEditParams.getIdParams().setLid(lid);
            Line line = editService.getLineByLid(lid);
            User user = loginService.getUserByUid(line.getUid());
            String username = user.getUserName();
            UserGroup userGroup = inviteService.getUserGroupByGid(line.getGid());
            String editGroupName = userGroup.getGroupName() + "_" + line.getFid();
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(editGroupName);
            if (editRoom.get(username).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    editRoom.get(i).sendEditMessage(new Gson().toJson(groupEditParams));
                }
            } else {
                editRoom.get(username).sendEditMessage(new Gson().toJson(groupEditParams));
            }
            editRoom.get(username).joinFlag = 0;

        } else if (groupEditParams.getVarAndFuncParams() != null) {
            VarAndFuncParams varAndFuncParams = groupEditParams.getVarAndFuncParams();
            Integer vid = editService.addVarAndFunc(varAndFuncParams.getNid(), varAndFuncParams.getModifier(), varAndFuncParams.getDataType(), varAndFuncParams.getName(), varAndFuncParams.getParams(), varAndFuncParams.getFlag());
            groupEditParams.getVarAndFuncParams().setVid(vid);
            NodePic nodePic = editService.getNodePicByNid(varAndFuncParams.getNid());
            User user = loginService.getUserByUid(nodePic.getUid());
            String username = user.getUserName();
            UserGroup userGroup = inviteService.getUserGroupByGid(nodePic.getGid());
            String editGroupName = userGroup.getGroupName() + "_" + nodePic.getFid();
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(editGroupName);
            if (editRoom.get(username).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    editRoom.get(i).sendEditMessage(new Gson().toJson(groupEditParams));
                }
            } else {
                editRoom.get(username).sendEditMessage(new Gson().toJson(groupEditParams));
            }
            editRoom.get(username).joinFlag = 0;
        } else {
            NewNodeParam newNodeParam = groupEditParams.getNewNodeParam();
            Style styles = newNodeParam.getStyles();
            NodeStyle nodeStyle = new NodeStyle();
            nodeStyle.setStyleHeight(styles.getHeight());
            nodeStyle.setStyleLeft(styles.getLeft());
            nodeStyle.setStyleTop(styles.getTop());
            nodeStyle.setStyleWidth(styles.getWidth());
            Prop props = newNodeParam.getProps();
            Properties properties = new Properties();
            properties.setInstance(props.isInstance());
            properties.setWeak(props.isWeak());
            properties.setClassName(props.getClassName());
            properties.setClassType(props.getClassType());
            properties.setCompositionType(props.getCompositionType());
            properties.setConditions(props.getConditions());
            properties.setName(props.getName());
            Integer nid = editService.addNode(newNodeParam.getUid(), newNodeParam.getGid(), newNodeParam.getFid(), newNodeParam.getNodeType(), nodeStyle, properties);
            groupEditParams.getIdParams().setNid(nid);
            NodePic nodePic = editService.getNodePicByNid(nid);
            User user = loginService.getUserByUid(nodePic.getUid());
            String username = user.getUserName();
            UserGroup userGroup = inviteService.getUserGroupByGid(nodePic.getGid());
            String editGroupName = userGroup.getGroupName() + "_" + nodePic.getFid();
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(editGroupName);
            if (editRoom.get(username).joinFlag == 0) {
                //证明用户原本就在房内
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    editRoom.get(i).sendEditMessage(new Gson().toJson(groupEditParams));
//                    if (!i.equals(user.getUserName())) {
//                        editRoom.get(i).sendEditMessage(new Gson().toJson(groupEditParams));
//                    }
                }
            } else {
                editRoom.get(username).sendEditMessage(new Gson().toJson(groupEditParams));
            }
            editRoom.get(username).joinFlag = 0;
        }

    }

    public void editUpdateGroup(GroupEditParams groupEditParams) throws IOException {
        //TODO 離開房間需要廣博並且全員離開需要關閉線程？？？
        if (groupEditParams.getLineParams() != null) {
            LineParams lineParams = groupEditParams.getLineParams();
            //並且還要保存到數據庫
            editService.updateLine(lineParams);
            Line line = editService.getLineByLid(lineParams.getLid());
            User user = loginService.getUserByUid(line.getUid());
            String username = user.getUserName();
            UserGroup userGroup = inviteService.getUserGroupByGid(line.getGid());
            String editGroupName = userGroup.getGroupName() + "_" + line.getFid();
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(editGroupName);
            if (editRoom.get(username).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    editRoom.get(i).sendEditMessage(new Gson().toJson(groupEditParams));
                }
            } else {
                editRoom.get(username).sendEditMessage(new Gson().toJson(groupEditParams));
            }
            editRoom.get(username).joinFlag = 0;
        } else if (groupEditParams.getVarAndFuncParams() != null) {
            VarAndFuncParams varAndFuncParams = groupEditParams.getVarAndFuncParams();
            editService.upDateVarAndFunc(varAndFuncParams.getNid(), varAndFuncParams.getVid(), varAndFuncParams.getModifier(), varAndFuncParams.getDataType(), varAndFuncParams.getName(), varAndFuncParams.getParams(), varAndFuncParams.getFlag());
            NodePic nodePic = editService.getNodePicByNid(varAndFuncParams.getNid());
            User user = loginService.getUserByUid(nodePic.getUid());
            String username = user.getUserName();
            UserGroup userGroup = inviteService.getUserGroupByGid(nodePic.getGid());
            String editGroupName = userGroup.getGroupName() + "_" + nodePic.getFid();
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(editGroupName);
            if (editRoom.get(username).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    editRoom.get(i).sendEditMessage(new Gson().toJson(groupEditParams));
                }
            } else {
                editRoom.get(username).sendEditMessage(new Gson().toJson(groupEditParams));
            }
            editRoom.get(username).joinFlag = 0;
        } else {
            NodeParams nodeParams = groupEditParams.getNodeParams();
            editService.updateNode(nodeParams.getNid(), nodeParams.getNodeKey(), nodeParams.getKey(), nodeParams.getValue());

            NodePic nodePic = editService.getNodePicByNid(nodeParams.getNid());
            User user = loginService.getUserByUid(nodePic.getUid());
            String username = user.getUserName();
            UserGroup userGroup = inviteService.getUserGroupByGid(nodePic.getGid());
            String editGroupName = userGroup.getGroupName() + "_" + nodePic.getFid();
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(editGroupName);
            if (editRoom.get(username).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    editRoom.get(i).sendEditMessage(new Gson().toJson(groupEditParams));
                }
            } else {
                editRoom.get(username).sendEditMessage(new Gson().toJson(groupEditParams));
            }
            editRoom.get(username).joinFlag = 0;
        }

    }

    public void editDeleteGroup(GroupEditParams groupEditParams) throws IOException {
        IdParams idParams = groupEditParams.getIdParams();
        //TODO 離開房間需要廣博並且全員離開需要關閉線程？？？
        Integer fid = idParams.getFid();
        Integer gid = idParams.getGid();
        Integer uid = idParams.getUid();
        if (!idParams.getLid().equals(-1)) {
            Integer lid = idParams.getLid();
            Line line = editService.getLineByLid(lid);
            User user = loginService.getUserByUid(uid);
            String username = user.getUserName();
            UserGroup userGroup = inviteService.getUserGroupByGid(gid);
            String editGroupName = userGroup.getGroupName() + "_" + line.getFid();
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(editGroupName);
            if (editRoom.get(username).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    //TODO 傳遞待思考
                    editRoom.get(i).sendEditMessage(new Gson().toJson(groupEditParams));
                }
            } else {
                editRoom.get(username).sendEditMessage(new Gson().toJson(groupEditParams));
            }
            editRoom.get(username).joinFlag = 0;
            //並且還要保存到數據庫
            editService.delLine(fid, lid);
        } else if (!idParams.getVid().equals(-1)) {
            Integer vid = idParams.getVid();
            Integer nid = idParams.getNid();
            NodePic nodePic = editService.getNodePicByNid(nid);
            User user = loginService.getUserByUid(nodePic.getUid());
            String username = user.getUserName();
            UserGroup userGroup = inviteService.getUserGroupByGid(nodePic.getGid());
            String editGroupName = userGroup.getGroupName() + "_" + nodePic.getFid();
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(editGroupName);
            if (editRoom.get(username).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    editRoom.get(i).sendEditMessage(new Gson().toJson(groupEditParams));
                }
            } else {
                editRoom.get(username).sendEditMessage(new Gson().toJson(groupEditParams));
            }
            editRoom.get(username).joinFlag = 0;
            editService.delVarAndFUnc(nid, vid);
        } else {
            Integer nid = idParams.getNid();
            NodePic nodePic = editService.getNodePicByNid(nid);
            User user = loginService.getUserByUid(uid);
            String username = user.getUserName();
            UserGroup userGroup = inviteService.getUserGroupByGid(gid);
            String editGroupName = userGroup.getGroupName() + "_" + nodePic.getFid();
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(editGroupName);
            if (editRoom.get(username).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    editRoom.get(i).sendEditMessage(new Gson().toJson(groupEditParams));
                }
            } else {
                editRoom.get(username).sendEditMessage(new Gson().toJson(groupEditParams));
            }
            editRoom.get(username).joinFlag = 0;
            editService.delNode(fid, nid);
        }
    }

//    private void leaveRoom() {
//        List<UserGroup> userGroupList = inviteService.getAllUserGroup();
//        List<UserGroup> deletedUserGroupList = new ArrayList<UserGroup>();
//        for (UserGroup userGroup : userGroupList) {
//            if (userGroup.getIsDeleted().equals("T")) {
//                deletedUserGroupList.add(userGroup);
//            }
//        }
//        List<String> nameList = new ArrayList<String>();
//        for (UserGroup userGroup : deletedUserGroupList) {
//            for (String name : groupEditList.keySet()) {
//                if (name.equals(userGroup.getGroupName())) {
//                    nameList.add(name);
//                }
//            }
//        }
//        for (String name : nameList) {
//            closeGroupEdit(name);
//        }
//
////        IdParams idParams = groupEditParams.getIdParams();
////        Integer gid = idParams.getGid();
////        Integer uid = idParams.getUid();
////        Integer fid = idParams.getFid();
////        UserGroup userGroup = inviteService.getUserGroupByGid(gid);
////        User user = loginService.getUserByUid(uid);
////        String editGroupName = userGroup.getGroupName() + "_" + fid;
////        ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(editGroupName);
////        editRoom.remove(user.getUserName());
////        if (editRoom.size() == 0) {
////            closeGroupEdit();
////        }
//    }

    public void sendEditMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @OnClose
    public void closeGroupEdit() {
        List<UserGroup> userGroupList = inviteService.getAllUserGroup();
        List<UserGroup> deletedUserGroupList = new ArrayList<UserGroup>();
        for (UserGroup userGroup : userGroupList) {
            if (userGroup.getIsDeleted().equals("T")) {
                deletedUserGroupList.add(userGroup);
            }
        }
        List<String> nameList = new ArrayList<String>();
        for (UserGroup userGroup : deletedUserGroupList) {
            for (String name : groupEditList.keySet()) {
                if (name.contains(userGroup.getGroupName())) {
                    nameList.add(name);
                }
            }
        }
        for (String name : nameList) {
            System.out.println(name + "--------------edit关闭");
            groupEditList.remove(name);
        }
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
