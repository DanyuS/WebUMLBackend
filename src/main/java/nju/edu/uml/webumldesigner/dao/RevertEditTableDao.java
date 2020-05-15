package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.entity.RevertEditTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RevertEditTableDao extends JpaRepository<RevertEditTable, Integer> {
    RevertEditTable findRevertEditTableByRid(Integer rid);

    List<RevertEditTable> findRevertEditTablesByFidAndUid(Integer fid, Integer uid);
}
