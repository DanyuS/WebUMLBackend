package nju.edu.uml.webumldesigner.util;

import com.google.gson.Gson;
import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.dao.UserGroupDao;
import nju.edu.uml.webumldesigner.entity.ChatRoom;
import nju.edu.uml.webumldesigner.entity.User;
import nju.edu.uml.webumldesigner.entity.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint("/websocket/{info}")
public class WebSocketUtil {
    //房间集合
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, WebSocketUtil>> chatRoomList = new ConcurrentHashMap<String, ConcurrentHashMap<String, WebSocketUtil>>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

    private int joinFlag = 0;

    @Autowired
    private UserGroupDao userGroupDao;

    @Autowired
    private UserDao userDao;

    /*
     * 只要有一个用户进入就会新建一个房间
     * 全部用户离开就删除这个线程
     * */
    public boolean createRoom(Integer gid) {
        UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
        String chatRoomName = userGroup.getGroupName();
        chatRoomList.put(chatRoomName, new ConcurrentHashMap<String, WebSocketUtil>());
        return true;
    }

    @OnOpen
    public void onOpen(Session session, String userName, int flag) {
        this.session = session;
    }

    public void joinChatRoom(String memberName, String userName) {
        ConcurrentHashMap<String, WebSocketUtil> chatRoom = chatRoomList.get(memberName);
        if (chatRoom.get(userName) != null) {        //该用户有没有出
            this.joinFlag = 1;
        }
        chatRoom.put(userName, this);//将此用户加入房间中
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        ChatRoom chatRoom = new Gson().fromJson(message, ChatRoom.class);
        if (chatRoom.getChatContent().equals("exit")) {
            //用户退出房间
            User user = userDao.findUserByUid(chatRoom.getUid());
            chatRoomList.get(String.valueOf(chatRoom.getGid())).remove(user.getUserName());
            if (chatRoomList.get(String.valueOf(chatRoom.getGid())).size() != 0) {
                String content = user.getUserName() + " 退出了房间";
                chatRoom.setChatContent(content);
                ConcurrentHashMap<String, WebSocketUtil> r = chatRoomList.get(String.valueOf(chatRoom.getGid()));
                List<String> usernameList = new ArrayList<String>();
                for (String userName : r.keySet()) {
                    usernameList.add(userName);
                }
//                obj.put("usernameList", usernameList.toArray());
                for (String i : r.keySet()) {  //遍历该房间
                    r.get(i).sendMessage(new Gson().toJson(chatRoom));//调用方法 将消息推送
                }
            }
        } else {
            //就是普通发送信息
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            chatRoom.setChatTime(df.format(date));
            User user = userDao.findUserByUid(chatRoom.getUid());
            String username = user.getUserName();
            //从房间列表中定位到该房间
            ConcurrentHashMap<String, WebSocketUtil> r =chatRoomList.get(String.valueOf(chatRoom.getGid()));
            List<String> userName = new ArrayList<String>();
            for(String u:r.keySet()){
                userName.add(u);
            }
//            obj.put("uname", uname.toArray());
            if(r.get(username).joinFlag == 0){			//证明不是退出重连
                for(String i:r.keySet()){  //遍历该房间
//                    obj.put("isSelf", username.equals(i));//设置消息是否为自己的
                    r.get(i).sendMessage(new Gson().toJson(chatRoom));//调用方法 将消息推送
                }
            }else{
//                obj.put("isSelf", true);
                r.get(username).sendMessage(new Gson().toJson(chatRoom));
            }
            r.get(username).joinFlag = 0;
        }

    }

    public void broadcast(String userName, int flag) throws IOException {
        if (flag == 0) {
            String content = "用户" + userName + "退出房间";
            /////////////////////////////right?
            sendMessage(content);
        } else {
            String content = "用户" + userName + "加入房间";
            /////////////////////////////right?
            sendMessage(content);
        }
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @OnClose
    public void onClose(Session session) {

    }
}
