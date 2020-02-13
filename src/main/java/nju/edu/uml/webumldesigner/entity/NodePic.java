package nju.edu.uml.webumldesigner.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NodePic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nid;
    private String nodeId;
    private String nodeType;
    private String nodeStyle;
    private String pidList;

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeStyle() {
        return nodeStyle;
    }

    public void setNodeStyle(String nodeStyle) {
        this.nodeStyle = nodeStyle;
    }

    public String getPidList() {
        return pidList;
    }

    public void setPidList(String pidList) {
        this.pidList = pidList;
    }
}
