package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.entity.NodeStyle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeStyleDao extends JpaRepository<NodeStyle, Integer> {
    NodeStyle findNodeStyleBySid(Integer sid);
}
