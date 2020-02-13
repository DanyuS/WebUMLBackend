package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.entity.NodePic;

public interface NodeDao {
    public boolean addNode();

    public NodePic getNodeByNid(int nid);

    public boolean delNodeByNid(int nid);

    public NodePic updateNode();
}
