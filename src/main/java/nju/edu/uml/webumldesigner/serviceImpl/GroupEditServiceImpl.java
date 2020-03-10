package nju.edu.uml.webumldesigner.serviceImpl;

import nju.edu.uml.webumldesigner.dao.FileDao;
import nju.edu.uml.webumldesigner.dao.UserGroupDao;
import nju.edu.uml.webumldesigner.entity.FilePic;
import nju.edu.uml.webumldesigner.entity.UserGroup;
import nju.edu.uml.webumldesigner.service.GroupEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupEditServiceImpl implements GroupEditService {
    @Autowired
    private FileDao fileDao;

    @Autowired
    private UserGroupDao userGroupDao;

    @Override
    public Integer createFileByGroup(Integer gid, String fileName, String fileType) {
        FilePic filePic = new FilePic();
        filePic.setFileName(fileName);
        filePic.setFileType(fileType);
        String num = String.valueOf(fileDao.count() + 1);
        filePic.setFileId("f" + num);
        FilePic result = fileDao.save(filePic);
        if (result.getFid() > 0) {
            //加入userGroup的fidList中
            UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
            List<Integer> fidList = userGroup.getFidList();
            fidList.add(result.getFid());
            userGroup.setFidList(fidList);
            userGroupDao.save(userGroup);
            return result.getFid();
        }
        return -1;
    }
}
