package nju.edu.uml.webumldesigner.dao;

public interface UserGroupDao {
    public boolean inviteUser(String userEmail);

    public String getUserGroup(int gid);
}
