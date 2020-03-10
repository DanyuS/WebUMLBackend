package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.entity.LineStyle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineStyleDao extends JpaRepository<LineStyle, Integer> {
    LineStyle findLineStyleByLsid(Integer lsid);
}
