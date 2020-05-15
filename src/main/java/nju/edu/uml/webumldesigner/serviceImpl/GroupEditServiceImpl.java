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

    private final VarAndFuncDao varAndFuncDao;

    private final PropertiesDao propertiesDao;

    private final RevertEditTableDao revertEditTableDao;

    public GroupEditServiceImpl(FileDao fileDao, UserGroupDao userGroupDao, LineDao lineDao, NodeDao nodeDao, VarAndFuncDao varAndFuncDao, PropertiesDao propertiesDao, RevertEditTableDao revertEditTableDao) {
        this.fileDao = fileDao;
        this.userGroupDao = userGroupDao;
        this.lineDao = lineDao;
        this.nodeDao = nodeDao;
        this.varAndFuncDao = varAndFuncDao;
        this.propertiesDao = propertiesDao;
        this.revertEditTableDao = revertEditTableDao;
    }

    @Override
    public Integer createFileByGroup(Integer gid, String fileName, String fileType) {
        FilePic filePic = new FilePic();
        filePic.setFileName(fileName);
        filePic.setFileType(fileType);
        String num = String.valueOf(fileDao.count() + 1);
        filePic.setFileId("f" + num);
        filePic.setRefreshTime(0);
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
        List<Integer> fidList = userGroup.getFidList();
        for (int i = 0; i < fidList.size(); i++) {
            if (fidList.get(i).equals(fid)) {
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
    public Object undo(Integer fid, Integer uid) {
        List<RevertEditTable> revertEditTableList = revertEditTableDao.findRevertEditTablesByFidAndUid(fid, uid);
        Object result = null;
        Object afterChange = null;
//        Class<T> clazz;
        if (revertEditTableList.size() != 0) {
            int lastUpdate = revertEditTableList.size();
            RevertEditTable revertEditTable = revertEditTableList.get(lastUpdate - 1);
            switch (revertEditTable.getComponentType()) {
                case "Line":
//                    clazz = (Class<T>) Line.class;
                    afterChange = changeEditMethod(lineDao.findLineByLid(revertEditTable.getComponentId()), "Line");
                    result = afterChange;
//                    result = clazz.cast(afterChange);
                    break;
                case "Node":
                    afterChange = changeEditMethod(nodeDao.findNodePicByNid(revertEditTable.getComponentId()), "Node");
                    result = afterChange;
                    break;
                case "VarAndFunc":
                    afterChange = changeEditMethod(varAndFuncDao.findVarAndFuncByVid(revertEditTable.getComponentId()), "VarAndFunc");
                    result = afterChange;
                    break;
            }
            revertEditTableDao.delete(revertEditTable);
        }

        return result;
    }

    @Override
    public FilePic execute(Integer fid, Integer uid) {
        return null;
    }

    private Object changeEditMethod(Object component, String componentType) {
        String editMethod;
        Object result = null;
        switch (componentType) {
            case "Line":
                Line line = (Line) component;
                editMethod = line.getEditMethod();
                if (editMethod.equals("Add")) {
                    line.setEditMethod("Delete");
                } else if (editMethod.equals("Delete")) {
                    line.setEditMethod("Add");
                }
                result = line;
                break;
            case "Node":
                NodePic nodePic = (NodePic) component;
                editMethod = nodePic.getEditMethod();
                if (editMethod.equals("Add")) {
                    nodePic.setEditMethod("Delete");
                } else if (editMethod.equals("Delete")) {
                    nodePic.setEditMethod("Add");
                }
                result = nodePic;
                break;
            case "VarAndFunc":
//                VarAndFunc varAndFunc = (VarAndFunc) component;
//                editMethod = varAndFunc.getEditMethod();
//                if (editMethod.equals("Add")) {
//                    varAndFunc.setEditMethod("Delete");
//                } else if (editMethod.equals("Delete")) {
//                    varAndFunc.setEditMethod("Add");
//                }
                break;
        }
        return result;
    }
}
