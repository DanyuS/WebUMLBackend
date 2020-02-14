package nju.edu.uml.webumldesigner.service;

import nju.edu.uml.webumldesigner.entity.User;
import nju.edu.uml.webumldesigner.entity.UserGroup;

public interface InviteService {
    public UserGroup createGroup(String groupName, Integer uid);

    public User inviteUser(String userEmail);
}
