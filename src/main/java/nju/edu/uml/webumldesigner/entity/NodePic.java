package nju.edu.uml.webumldesigner.entity;

import javax.persistence.*;

@Entity
public class NodePic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nid;
    private String nodeId;
    private String nodeType;
    @OneToOne
    private NodeStyle nodeStyle;
    @OneToOne
    private Properties properties;

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
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

    public NodeStyle getNodeStyle() {
        return nodeStyle;
    }

    public void setNodeStyle(NodeStyle nodeStyle) {
        this.nodeStyle = nodeStyle;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
