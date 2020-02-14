package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.entity.VarAndFunc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VarAndFuncDao extends JpaRepository<VarAndFunc, Integer> {
    VarAndFunc findVarAndFuncByVid(Integer vid);
}
