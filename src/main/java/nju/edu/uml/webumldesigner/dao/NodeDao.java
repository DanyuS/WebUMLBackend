package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.entity.NodePic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeDao extends JpaRepository<NodePic, Integer> {
    public NodePic findNodePicByNid(Integer nid);
}
