package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.entity.LineSvgStyle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineSvgStyleDao extends JpaRepository<LineSvgStyle, Integer> {
    LineSvgStyle findLineSvgStyleByLssid(Integer lssid);
}
