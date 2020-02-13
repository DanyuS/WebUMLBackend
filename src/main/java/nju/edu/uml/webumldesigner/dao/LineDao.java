package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.entity.Line;

public interface LineDao {
    public boolean addLine();

    public Line getLineByLid(int lid);

    public boolean delLineByLid(int lid);

    public Line upDateLine();
}
