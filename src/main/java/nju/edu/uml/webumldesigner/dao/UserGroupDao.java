package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupDao extends JpaRepository<UserGroup, Integer> {
    UserGroup findUserGroupByGid(Integer gid);
    List<UserGroup> findAllBy();
//    public boolean inviteUser(String userEmail);
//
//    public String getUserGroup(int gid);

}
