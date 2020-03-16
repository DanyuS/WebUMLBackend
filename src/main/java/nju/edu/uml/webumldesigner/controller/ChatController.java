package nju.edu.uml.webumldesigner.controller;

import com.google.gson.Gson;
import nju.edu.uml.webumldesigner.controller.params.IdParams;
import nju.edu.uml.webumldesigner.entity.ChatRoom;
import nju.edu.uml.webumldesigner.entity.User;
import nju.edu.uml.webumldesigner.entity.UserGroup;
import nju.edu.uml.webumldesigner.service.InviteService;
import nju.edu.uml.webumldesigner.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{message}")
@Component
public class ChatController {
    //房间集合
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ChatController>> chatRoomList = new ConcurrentHashMap<String, ConcurrentHashMap<String, ChatController>>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

    private int joinFlag = 0;

    private static LoginService loginService;
    private static InviteService inviteService;

    @Autowired
    public void setLoginService(LoginService loginService) {
        ChatController.loginService = loginService;
    }

    @Autowired
    public void setInviteService(InviteService inviteService) {
        ChatController.inviteService = inviteService;
    }

    /*
     * 只要有一个用户进入就会新建一个房间
     * 全部用户离开就删除这个线程
     * */
    public boolean createRoom(Integer gid, Integer fid) {
        //房間名格式為GroupName_fid
        UserGroup userGroup = inviteService.getUserGroupByGid(gid);
        String chatRoomName = userGroup.getGroupName() + "_" + fid;
        chatRoomList.put(chatRoomName, new ConcurrentHashMap<String, ChatController>());
        System.out.println("-----------------创建房间" + chatRoomName);
        System.out.println("-----------------当前创建房间线程数" + getConnectNum());
        return true;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "message") String message) {
        //将用户加入聊天室
        //傳遞內容"gid,uid"
        this.session = session;
        IdParams idParams = new Gson().fromJson(message, IdParams.class);
        if (!idParams.getGid().equals(-1)) {
            UserGroup userGroup = inviteService.getUserGroupByGid(idParams.getGid());
            User user = loginService.getUserByUid(idParams.getUid());
            if (chatRoomList.get(userGroup.getGroupName()) == null) {
                createRoom(idParams.getGid(), idParams.getFid());
            }
            joinChatRoom(userGroup.getGroupName(), user.getUserName());
        }
//        ChatRoom chatRoom = new Gson().fromJson(message, ChatRoom.class);
//        UserGroup userGroup = userGroupDao.findUserGroupByGid(chatRoom.getGid());
//        User user = userDao.findUserByUid(chatRoom.getUid());
//        if (chatRoom.getChatContent().equals("joinIn")) {
//            joinChatRoom(userGroup.getGroupName(), user.getUserName());
//            System.out.println("---------------" + user.getUserName() + "加入房间了");
//        }
    }

    public void joinChatRoom(String groupName, String userName) {
        ConcurrentHashMap<String, ChatController> chatRoom = chatRoomList.get(groupName);
        //判断用户是否已经在房间里
        if (chatRoom.get(userName) != null) {
            this.joinFlag = 1;
        }
        chatRoom.put(userName, this);//将此用户加入房间中
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        ChatRoom chatRoom = new Gson().fromJson(message, ChatRoom.class);
        if (chatRoom.getChatContent().equals("exit")) {
            //用户退出房间
            User user = loginService.getUserByUid(chatRoom.getUid());
            UserGroup userGroup = inviteService.getUserGroupByGid(chatRoom.getUid());
            String chatRoomName = userGroup.getGroupName() + "_" + chatRoom.getFid();
            ConcurrentHashMap<String, ChatController> room = chatRoomList.get(chatRoomName);
            room.remove(user.getUserName());
            if (room.size() != 0) {
                String content = user.getUserName() + " 退出了房间";
                chatRoom.setChatContent(content);
                for (String i : room.keySet()) {
                    room.get(i).sendMessage(new Gson().toJson(chatRoom));
                }
            }
            //房间成员统统没了关闭连接
//            onClose(session);
        } else {
            //就是普通发送信息
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            chatRoom.setChatTime(df.format(date));
            User user = loginService.getUserByUid(chatRoom.getUid());
            UserGroup userGroup = inviteService.getUserGroupByGid(chatRoom.getUid());
            String chatRoomName = userGroup.getGroupName() + "_" + chatRoom.getFid();
            String username = user.getUserName();
            //从房间列表中定位到该房间
            ConcurrentHashMap<String, ChatController> room = chatRoomList.get(chatRoomName);
            if (room.get(username).joinFlag == 0) {
                for (String i : room.keySet()) {
                    //调用方法 将消息推送
                    room.get(i).sendMessage(new Gson().toJson(chatRoom));
                }
            } else {
                room.get(username).sendMessage(new Gson().toJson(chatRoom));
            }
            room.get(username).joinFlag = 0;
        }

    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //session.getAsyncRemote().sendText("连接上WebSocket");
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("--------------房间关闭");
        chatRoomList.remove(this);
    }

    @OnError
    public void OnError(Throwable e) {
        System.out.println("----------------出现异常");
        e.printStackTrace();
    }

    public int getConnectNum() {
        return chatRoomList.size();
    }


//    private static ConcurrentHashMap<String, ChatController> chatRoomList = new ConcurrentHashMap<>();
//    //与某个客户端的连接会话，需要通过它来给客户端发送数据
//    private Session session;
//
//    private String name;

//    @Autowired
//    private UserDao userDao;

//    @OnOpen
//    public void onOpen(@PathParam("uid") Integer uid, Session session) {
//        User user = userDao.findUserByUid(uid);
//        String userName = user.getUserName();
//        chatRoomList.put(userName, this);
//        System.out.println(userName + "连接服务器成功");
//        System.out.println("客户端连接个数:" + getConnectNum());
//
//        this.session = session;
//        this.name = userName;
//    }
//
//    @OnClose
//    public void onClose() {
//        chatRoomList.remove(name);
//        System.out.println(name + "断开了服务器连接");
//    }
//
//    @OnError
//    public void onError(Throwable error) {
//        error.printStackTrace();
//        System.out.println(name + "出现了异常");
//    }
//
//    @OnMessage
//    public void getMessage(String message) throws IOException {
//        System.out.println("收到" + name + ":" + message);
//        System.out.println("客户端连接个数:" + getConnectNum());
//
//        Set<Map.Entry<String, ChatController>> entries = chatRoomList.entrySet();
//        for (Map.Entry<String, ChatController> entry : entries) {
//            if (!entry.getKey().equals(name)) {//将消息转发到其他非自身客户端
//                entry.getValue().sendMessage(message);
//            }
//        }
//    }
//
//    public void sendMessage(String message) throws IOException {
//        if (session.isOpen()) {
//            session.getBasicRemote().sendText(message);
//        }
//    }
//
//    public int getConnectNum() {
//        return chatRoomList.size();
//    }

}
