package nju.edu.uml.webumldesigner.serviceImpl;

import nju.edu.uml.webumldesigner.dao.*;
import nju.edu.uml.webumldesigner.entity.*;
import nju.edu.uml.webumldesigner.service.GroupEditService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupEditServiceImpl implements GroupEditService {
    private final FileDao fileDao;

    private final UserGroupDao userGroupDao;

    private final LineDao lineDao;

    private final NodeDao nodeDao;

    private final PropertiesDao propertiesDao;

    public GroupEditServiceImpl(FileDao fileDao, UserGroupDao userGroupDao, LineDao lineDao, NodeDao nodeDao, PropertiesDao propertiesDao) {
        this.fileDao = fileDao;
        this.userGroupDao = userGroupDao;
        this.lineDao = lineDao;
        this.nodeDao = nodeDao;
        this.propertiesDao = propertiesDao;
    }

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

    @Override
    public boolean deleteFileByGroup(Integer gid, Integer fid) {
        UserGroup userGroup = userGroupDao.findUserGroupByGid(gid);
        List<Integer> fidList= userGroup.getFidList();
        for(int i=0;i<fidList.size();i++){
            if(fidList.get(i).equals(fid)){
                fidList.remove(i);
                break;
            }
        }
        userGroup.setFidList(fidList);
        userGroupDao.save(userGroup);
        return true;
    }

    @Override
    public String updateText(String idType, Integer id, String text) {
        //问题在于，如果是同一个人修改那就没有解决冲突的必要，但如果是两个人修改就可能存在冲突，怎么判断是多个人同时修改
        String result = "";
        if (idType.equals("node")) {
            NodePic nodePic = nodeDao.findNodePicByNid(id);
            Properties properties = nodePic.getProperties();
            //哪个存在文本框

        } else if (idType.equals("line")) {
            Line line = lineDao.findLineByLid(id);
            String lineText = line.getText();
        }
        return null;
    }

    @Override
    public FilePic revertEdit(Integer fid) {
        return null;
    }
}
