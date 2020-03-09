package nju.edu.uml.webumldesigner.controller.params;

import java.util.ArrayList;
import java.util.List;

public class NodeParams {
    Integer nid;
    String nodeKey;
    List<String> key = new ArrayList<String>();
    List<String> value = new ArrayList<String>();

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public List<String> getKey() {
        return key;
    }

    public void setKey(List<String> key) {
        this.key = key;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
