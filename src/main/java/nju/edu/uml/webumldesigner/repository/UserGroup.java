package nju.edu.uml.webumldesigner.repository;

import java.util.List;

public class UserGroup {
    private int gid;
    private String groupId;
    private String groupName;
    private String uidList;
    private String fidList;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUidList() {
        return uidList;
    }

    public void setUidList(String uidList) {
        this.uidList = uidList;
    }

    public String getFidList() {
        return fidList;
    }

    public void setFidList(String fidList) {
        this.fidList = fidList;
    }
}
