package nju.edu.uml.webumldesigner.controller;

import com.google.gson.Gson;
import nju.edu.uml.webumldesigner.controller.params.*;
import nju.edu.uml.webumldesigner.dao.LineDao;
import nju.edu.uml.webumldesigner.dao.NodeDao;
import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.dao.UserGroupDao;
import nju.edu.uml.webumldesigner.entity.*;
import nju.edu.uml.webumldesigner.service.EditService;
import nju.edu.uml.webumldesigner.service.GroupEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

//@RestController
@ServerEndpoint("/groupEdit/{message}")
public class GroupEditController {
    private final EditService editService;

    public GroupEditController(EditService editService) {
        this.editService = editService;
    }

    private static ConcurrentHashMap<String, ConcurrentHashMap<String, GroupEditController>> groupEditList = new ConcurrentHashMap<String, ConcurrentHashMap<String, GroupEditController>>();

    private Session session;

    private int joinFlag = 0;

    @Autowired
    private UserGroupDao userGroupDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private NodeDao nodeDao;

    @Autowired
    private LineDao lineDao;


//    @GetMapping("/createFileByGroup")
//    public Integer createFileByGroup(Integer gid, String fileName, String fileType) {
//        return groupEditService.createFileByGroup(gid, fileName, fileType);
//    }

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
        String[] idList = message.split(",");
        Integer gid = Integer.parseInt(idList[0]);
        Integer uid = Integer.parseInt(idList[1]);
        UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
        User user = userDao.findUserByUid(uid);
        if (!gid.equals(-1)) {
            if (groupEditList.get(userGroup.getGroupName()) == null) {
                createRoom(gid);
            }
            joinEdit(userGroup.getGroupName(), user.getUserName());
        }
//        if (message.contains("line")) {
//            Line line = new Gson().fromJson(message, Line.class);
//            User user = userDao.findUserByUid(line.getUid());
//            UserGroup userGroup = userGroupDao.findUserGroupByGid(line.getGid());
//            joinEdit(userGroup.getGroupName(), user.getUserName());
//        } else {
//            NodePic nodePic = new Gson().fromJson(message, NodePic.class);
//            User user = userDao.findUserByUid(nodePic.getUid());
//            UserGroup userGroup = userGroupDao.findUserGroupByGid(nodePic.getGid());
//            joinEdit(userGroup.getGroupName(), user.getUserName());
//        }
    }

    private void joinEdit(String groupName, String userName) {
        ConcurrentHashMap<String, GroupEditController> editGroup = groupEditList.get(groupName);
        if (editGroup.get(userName) != null) {
            this.joinFlag = 1;
        }
        editGroup.put(userName, this);//将此用户加入房间中
    }

    @OnMessage
    public void editAddGroup(Session session, @PathParam(value = "message") String message) throws IOException {
        //TODO 離開房間需要廣博並且全員離開需要關閉線程？？？
        //還有是添加還是修改還是刪除的問題！！！
        if (message.contains("line")) {
            LineParams lineParams = new Gson().fromJson(message, LineParams.class);
            //並且還要保存到數據庫
            Integer lid = editService.addLine(lineParams);
            Line line = lineDao.findLineByLid(lid);
            User user = userDao.findUserByUid(line.getUid());
            UserGroup userGroup = userGroupDao.findUserGroupByGid(line.getGid());
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(userGroup.getGroupName());
            if (editRoom.get(user.getUserName()).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    if (!i.equals(user.getUserName())) {
                        editRoom.get(i).sendEditMessage(new Gson().toJson(line));
                    }
                }
            } else {
                editRoom.get(user.getUserName()).sendEditMessage(new Gson().toJson(line));
            }
            editRoom.get(user.getUserName()).joinFlag = 0;

        } else {
            NewNodeParam newNodeParam = new Gson().fromJson(message, NewNodeParam.class);
            Style styles = newNodeParam.getStyles();
            NodeStyle nodeStyle = new NodeStyle();
            nodeStyle.setStyleHeight(styles.getHeight());
            nodeStyle.setStyleLeft(styles.getLeft());
            nodeStyle.setStyleTop(styles.getTop());
            nodeStyle.setStyleWidth(styles.getWidth());
            Prop props = newNodeParam.getProps();
            Properties properties = new Properties();
            properties.setInstance(props.isInstance());
            properties.setWeak(properties.isWeak());
            properties.setClassName(properties.getClassName());
            properties.setClassType(properties.getClassType());
            properties.setCompositionType(properties.getCompositionType());
            properties.setConditions(properties.getConditions());
            properties.setName(properties.getName());
            Integer nid = editService.addNode(newNodeParam.getUid(), newNodeParam.getGid(), newNodeParam.getFid(), newNodeParam.getNodeType(), nodeStyle, properties);
            NodePic nodePic = nodeDao.findNodePicByNid(nid);
            User user = userDao.findUserByUid(nodePic.getUid());
            UserGroup userGroup = userGroupDao.findUserGroupByGid(nodePic.getGid());
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(userGroup.getGroupName());
            if (editRoom.get(user.getUserName()).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    if (!i.equals(user.getUserName())) {
                        editRoom.get(i).sendEditMessage(new Gson().toJson(nodePic));
                    }
                }
            } else {
                editRoom.get(user.getUserName()).sendEditMessage(new Gson().toJson(nodePic));
            }
            editRoom.get(user.getUserName()).joinFlag = 0;
        }
    }

    @OnMessage
    public void editUpdateGroup(Session session, @PathParam(value = "message") String message) throws IOException {
        //TODO 離開房間需要廣博並且全員離開需要關閉線程？？？
        //還有是添加還是修改還是刪除的問題！！！
        if (message.contains("line")) {
            LineParams lineParams = new Gson().fromJson(message, LineParams.class);
            //並且還要保存到數據庫
            editService.updateLine(lineParams);
            Line line = lineDao.findLineByLid(lineParams.getLid());
            User user = userDao.findUserByUid(line.getUid());
            UserGroup userGroup = userGroupDao.findUserGroupByGid(line.getGid());
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(userGroup.getGroupName());
            if (editRoom.get(user.getUserName()).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    if (!i.equals(user.getUserName())) {
                        editRoom.get(i).sendEditMessage(new Gson().toJson(line));
                    }
                }
            } else {
                editRoom.get(user.getUserName()).sendEditMessage(new Gson().toJson(line));
            }
            editRoom.get(user.getUserName()).joinFlag = 0;
        } else {
            NodeParams nodeParams = new Gson().fromJson(message, NodeParams.class);
            editService.updateNode(nodeParams.getNid(), nodeParams.getNodeKey(), nodeParams.getKey(), nodeParams.getValue());

            NodePic nodePic = nodeDao.findNodePicByNid(nodeParams.getNid());
            User user = userDao.findUserByUid(nodePic.getUid());
            UserGroup userGroup = userGroupDao.findUserGroupByGid(nodePic.getGid());
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(userGroup.getGroupName());
            if (editRoom.get(user.getUserName()).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    if (!i.equals(user.getUserName())) {
                        editRoom.get(i).sendEditMessage(new Gson().toJson(nodePic));
                    }
                }
            } else {
                editRoom.get(user.getUserName()).sendEditMessage(new Gson().toJson(nodePic));
            }
            editRoom.get(user.getUserName()).joinFlag = 0;
        }
    }

    @OnMessage
    public void editDeleteGroup(Session session, @PathParam(value = "message") String message) throws IOException {
        //TODO 離開房間需要廣博並且全員離開需要關閉線程？？？
        //TODO 刪除情況可能有些特殊
        String[] idList = message.split(",");
        Integer fid = Integer.parseInt(idList[0]);
        Integer id = Integer.parseInt(idList[1]);
        if (message.contains("line")) {
            //刪除的傳參格式有待思考，暫時先定為a,b吧
            Line line = lineDao.findLineByLid(id);
            User user = userDao.findUserByUid(line.getUid());
            UserGroup userGroup = userGroupDao.findUserGroupByGid(line.getGid());
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(userGroup.getGroupName());
            if (editRoom.get(user.getUserName()).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    //TODO 傳遞待思考
                    if (!i.equals(user.getUserName())) {
                        editRoom.get(i).sendEditMessage(message);
                    }
                }
            } else {
                editRoom.get(user.getUserName()).sendEditMessage(message);
            }
            editRoom.get(user.getUserName()).joinFlag = 0;
            //並且還要保存到數據庫
            editService.delLine(fid, id);
        } else {

            NodePic nodePic = nodeDao.findNodePicByNid(id);
            User user = userDao.findUserByUid(nodePic.getUid());
            UserGroup userGroup = userGroupDao.findUserGroupByGid(nodePic.getGid());
            ConcurrentHashMap<String, GroupEditController> editRoom = groupEditList.get(userGroup.getGroupName());
            if (editRoom.get(user.getUserName()).joinFlag == 0) {
                for (String i : editRoom.keySet()) {
                    //调用方法 将消息推送
                    //TODO 傳遞待思考
                    if (!i.equals(user.getUserName())) {
                        editRoom.get(i).sendEditMessage(message);
                    }
                }
            } else {
                editRoom.get(user.getUserName()).sendEditMessage(message);
            }
            editRoom.get(user.getUserName()).joinFlag = 0;
            editService.delNode(fid, id);
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
