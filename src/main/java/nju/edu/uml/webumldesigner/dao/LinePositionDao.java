package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.entity.LinePosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinePositionDao extends JpaRepository<LinePosition, Integer> {
    LinePosition findLinePositionByLpid(Integer lpid);
}
