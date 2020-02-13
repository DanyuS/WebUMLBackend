package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.entity.FilePic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDao extends JpaRepository<FilePic, Integer> {
//    public String findFidListByUid(int uid);
//
//    public String findFidListByGid(int gid);
//
//    public FilePic findFilePicByFid(int fid);
//
//    public String findNidListByFid(int fid);
}
