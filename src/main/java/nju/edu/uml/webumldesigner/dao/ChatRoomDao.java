package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomDao extends JpaRepository<ChatRoom, Integer> {
    ChatRoom findChatRoomByCid(Integer cid);
}
