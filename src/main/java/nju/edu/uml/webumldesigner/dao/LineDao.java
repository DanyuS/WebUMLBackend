package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.entity.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineDao extends JpaRepository<Line, Integer> {
//    public boolean addLine();

    public Line findLineByLid(Integer lid);
//
//    public boolean delLineByLid(int lid);
//
//    public Line upDateLine();
}
