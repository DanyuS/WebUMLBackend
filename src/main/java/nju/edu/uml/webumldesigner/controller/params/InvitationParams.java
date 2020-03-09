package nju.edu.uml.webumldesigner.controller.params;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2020/3/9
 */
public class InvitationParams {
    Integer gid;
    List<String> userEmailList = new ArrayList<>();

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public List<String> getUserEmailList() {
        return userEmailList;
    }

    public void setUserEmailList(List<String> userEmailList) {
        this.userEmailList = userEmailList;
    }
}
