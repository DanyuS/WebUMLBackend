package nju.edu.uml.webumldesigner.serviceImpl;

import com.google.gson.Gson;
import nju.edu.uml.webumldesigner.dao.*;
import nju.edu.uml.webumldesigner.entity.*;
import nju.edu.uml.webumldesigner.service.EditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EditServiceImpl implements EditService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private NodeDao nodeDao;

    @Autowired
    private LineDao lineDao;

    @Autowired
    private PropertiesDao propertiesDao;

    @Autowired
    private VarAndFuncDao varAndFuncDao;

    @Override
    public Properties getPropertiesByPid(Integer pid) {
        return null;
    }

    @Override
    public boolean createFile(Integer uid, String fileName, String fileType) {
        FilePic filePic = new FilePic();
        filePic.setFileName(fileName);
        filePic.setFileType(fileType);
        String num = String.valueOf(fileDao.count() + 1);
        filePic.setFileId("f" + num);
        filePic.setNidList("[]");
        filePic.setNidList("[]");
        FilePic result = fileDao.save(filePic);
        if (result.getFid() > 0) {
            //加入user的fidList中
            Integer fid = Integer.parseInt(num);
            addFidToUser(uid, fid);
            return true;
        }

        return false;
    }

    @Override
    public boolean updateFile(Integer fid, String fileName, String fileType) {
        FilePic filePic = fileDao.findFilePicByFid(fid);
        filePic.setFileName(fileName);
        filePic.setFileType(fileType);
        fileDao.save(filePic);
        return true;
    }

    @Override
    public boolean delFile(Integer uid, Integer fid) {
        FilePic filePic = fileDao.findFilePicByFid(fid);
        fileDao.delete(filePic);
        removeFidFromUser(uid, fid);
        /////////////////////////////////////
        //注意刪除的時候还要刪除节点之类的其他表中的全部信息
        return true;
    }

    @Override
    public boolean addNode(Integer fid, String nodeType, NodeStyle nodeStyle, Properties properties) {
        NodePic nodePic = new NodePic();
        nodePic.setNodeStyle(nodeStyle);
        nodePic.setNodeType(nodeType);
        nodePic.setProperties(properties);
        //日后要改成用户id加啥啥还是别的怎么用法
        String num = String.valueOf(nodeDao.count() + 1);
        nodePic.setNodeId("n" + num);

        NodePic result = nodeDao.save(nodePic);
        if (result.getNid() > 0) {
            Integer nid = Integer.parseInt(num);
            addNidToFile(fid, nid);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateNode(Integer nid, String nodeKey, List<String> key, List<String> value) {
        NodePic nodePic = nodeDao.findNodePicByNid(nid);
        if (nodeKey.equals("properties")) {
            Properties properties = nodePic.getProperties();
            for (int i = 0; i < key.size(); i++) {
                switch (key.get(i)) {
                    case "className":
                        properties.setClassName(value.get(i));
                        break;
                    case "classType":
                        properties.setClassType(value.get(i));
                        break;
                    case "name":
                        properties.setName(value.get(i));
                        break;
                    case "isInstance":
                        if (value.get(i).equals("true")) {
                            properties.setInstance(true);
                        } else {
                            properties.setInstance(false);
                        }
                        break;
                    case "isWeak":
                        if (value.get(i).equals("true")) {
                            properties.setWeak(true);
                        } else {
                            properties.setWeak(false);
                        }
                        break;
                    case "compositionType":
                        properties.setCompositionType(value.get(i));
                        break;
                    case "conditions":
                        properties.setConditions(value.get(i));
                        break;
                    case "variables": {
                        VarAndFunc varAndFunc = new Gson().fromJson(value.get(i), VarAndFunc.class);
                        properties.setVariables(varAndFunc);
                        break;
                    }
                    case "functions": {
                        VarAndFunc varAndFunc = new Gson().fromJson(value.get(i), VarAndFunc.class);
                        properties.setFunctions(varAndFunc);
                        break;
                    }
                }
            }
            nodePic.setProperties(properties);
        } else {
            NodeStyle nodeStyle = nodePic.getNodeStyle();
            for (int i = 0; i < key.size(); i++) {
                switch (key.get(i)) {
                    case "width":
                        nodeStyle.setStyleWidth(Integer.parseInt(value.get(i)));
                        break;
                    case "height":
                        nodeStyle.setStyleHeight(Integer.parseInt(value.get(i)));
                        break;
                    case "left":
                        nodeStyle.setStyleLeft(Integer.parseInt(value.get(i)));
                        break;
                    case "top":
                        nodeStyle.setStyleTop(Integer.parseInt(value.get(i)));
                        break;
                }
            }
            nodePic.setNodeStyle(nodeStyle);
        }
        nodeDao.save(nodePic);
        return true;
    }

    @Override
    public boolean delNode(Integer fid, Integer nid) {
        NodePic nodePic = nodeDao.findNodePicByNid(nid);
        nodeDao.delete(nodePic);
        removeNidFromFile(fid, nid);
        return true;
    }

    @Override
    public boolean addLine(Integer fid, String relationType, String fromId, String toId, String styles) {
        Line line = new Line();
        line.setRelationType(relationType);
        line.setFromId(fromId);
        line.setToId(toId);
        line.setStyles(styles);

        String num = String.valueOf(lineDao.count() + 1);
        line.setLineId("l" + num);

        Line result = lineDao.save(line);
        if (result.getLid() > 0) {
            Integer lid = result.getLid();
            addLidToFile(fid, lid);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateLine(Integer lid, String relationType, String fromId, String toId, String styles) {
        Line line = lineDao.findLineByLid(lid);
        line.setRelationType(relationType);
        line.setFromId(fromId);
        line.setToId(toId);
        line.setStyles(styles);
        lineDao.save(line);
        return true;
    }

    @Override
    public boolean delLine(Integer fid, Integer lid) {
        Line line = lineDao.findLineByLid(lid);
        lineDao.delete(line);
        removeLidFromFile(fid, lid);
        return true;
    }

//    @Override
//    public boolean addProperties(Integer nid) {
//
//        //存入数据库
//        Properties properties = new Properties();
////        properties.setPropertiesId(propertiesId);
//
//        String num = String.valueOf(propertiesDao.count() + 1);
//        properties.setPropertiesId("p" + num);
//        Properties result = propertiesDao.save(properties);
//
//        if (result.getPid() > 0) {
////            Integer pid = Integer.parseInt(num);
////            addPidToNode(nid, pid);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean updateProperties(Integer pid) {
//
//        return true;
//    }
//
//    @Override
//    public boolean delProperties(Integer nid, Integer pid) {
//        Properties properties = propertiesDao.findPropertiesByPid(pid);
//        propertiesDao.delete(properties);
////        removePidFromNode(nid, pid);
//        return true;
//    }

    @Override
    public boolean addVarAndFunc(Integer pid, String modifier, String dataType, String name, String params, String propId, Integer flag) {
        VarAndFunc varAndFunc = new VarAndFunc();
        varAndFunc.setModifier(modifier);
        varAndFunc.setDataType(dataType);
        varAndFunc.setName(name);
        varAndFunc.setParams(params);
        varAndFunc.setPropId(propId);
        varAndFunc.setFlag(flag);
        VarAndFunc result = varAndFuncDao.save(varAndFunc);
        if (result.getVid() > 0) {
            Properties properties = propertiesDao.findPropertiesByPid(pid);
            if (flag == 0) {
                properties.setVariables(varAndFunc);
            } else {
                properties.setFunctions(varAndFunc);
            }
            propertiesDao.save(properties);
            return true;
        }

        return false;
    }

    @Override
    public boolean delVarAndFUnc(Integer pid, Integer vid) {
        Properties properties = propertiesDao.findPropertiesByPid(pid);
        VarAndFunc varAndFunc = varAndFuncDao.findVarAndFuncByVid(vid);
        if (varAndFunc.getFlag() == 0) {
            properties.setVariables(null);
        } else if (varAndFunc.getFlag() == 1) {
            properties.setFunctions(null);
        }
        propertiesDao.save(properties);
        varAndFuncDao.delete(varAndFunc);
        return true;
    }

    @Override
    public boolean upDateVarAndFunc(Integer pid, Integer vid, String modifier, String dataType, String name, String params, String propId, Integer flag) {
        VarAndFunc varAndFunc = varAndFuncDao.findVarAndFuncByVid(vid);
        varAndFunc.setModifier(modifier);
        varAndFunc.setDataType(dataType);
        varAndFunc.setName(name);
        varAndFunc.setParams(params);
        varAndFunc.setPropId(propId);
        varAndFunc.setFlag(flag);
        varAndFuncDao.save(varAndFunc);
        Properties properties = propertiesDao.findPropertiesByPid(pid);
        if (flag == 0) {
            properties.setVariables(varAndFunc);
        } else {
            properties.setFunctions(varAndFunc);
        }
        propertiesDao.save(properties);

        return true;
    }

    @Override
    public boolean importFile(Integer fid, Integer id) {
        //TODO
        return false;
    }

    @Override
    public List<FilePic> getAllFileByUid(Integer uid) {
        User user = userDao.findUserByUid(uid);
        List<Integer> fidList = transStringToList(user.getFidList());
        List<FilePic> result = new ArrayList<FilePic>();
        for (int i = 0; i < fidList.size(); i++) {
            FilePic filePic = fileDao.findFilePicByFid(fidList.get(i));
            result.add(filePic);
        }
        return result;
    }

    @Override
    public List<NodePic> getAllNodeByFid(Integer fid) {
        FilePic filePic = fileDao.findFilePicByFid(fid);
        List<Integer> nidList = transStringToList(filePic.getNidList());
        List<NodePic> result = new ArrayList<NodePic>();
        for (int i = 0; i < nidList.size(); i++) {
            NodePic nodePic = nodeDao.findNodePicByNid(nidList.get(i));
            result.add(nodePic);
        }
        return result;
    }

    @Override
    public List<Line> getAllLineByFid(Integer fid) {
        FilePic filePic = fileDao.findFilePicByFid(fid);
        List<Integer> lidList = transStringToList(filePic.getLidList());
        List<Line> result = new ArrayList<Line>();
        for (int i = 0; i < lidList.size(); i++) {
            Line line = lineDao.findLineByLid(lidList.get(i));
            result.add(line);
        }
        return result;
    }

//    @Override
//    public List<Properties> getAllPropertiesByNid(Integer nid) {
//        NodePic nodePic = nodeDao.findNodePicByNid(nid);
//        List<Integer> pidList = transStringToList(nodePic.getPidList());
//        List<Properties> result = new ArrayList<Properties>();
//        for (int i = 0; i < pidList.size(); i++) {
//            Properties properties = propertiesDao.findPropertiesByPid(pidList.get(i));
//            result.add(properties);
//        }
//        return result;
//    }

    //将fid加入user的fidList中
    private void addFidToUser(Integer uid, Integer fid) {
        User user = userDao.findUserByUid(uid);
        String fidList = user.getFidList();
        List<String> fList = new Gson().fromJson(fidList, List.class);
        fList.add(String.valueOf(fid));
        user.setFidList(new Gson().toJson(fList));
        userDao.save(user);
    }

    //将fid從user的fidList中移除
    private void removeFidFromUser(Integer uid, Integer fid) {
        User user = userDao.findUserByUid(uid);
        String fidList = user.getFidList();
        List<String> fList = new Gson().fromJson(fidList, List.class);
        for (int i = 0; i < fList.size(); i++) {
            if (fList.get(i).equals(String.valueOf(fid))) {
                fList.remove(i);
                break;
            }
        }
        user.setFidList(new Gson().toJson(fList));
        userDao.save(user);
    }

    //将nid加入file的nidList中
    private void addNidToFile(Integer fid, Integer nid) {
        FilePic filePic = fileDao.findFilePicByFid(fid);
        String nidList = filePic.getNidList();
        List<String> nList = new Gson().fromJson(nidList, List.class);
        nList.add(String.valueOf(nid));
        filePic.setNidList(new Gson().toJson(nList));
        fileDao.save(filePic);
    }

    //将nid從file的nidList中移除
    private void removeNidFromFile(Integer fid, Integer nid) {
        FilePic filePic = fileDao.findFilePicByFid(fid);
        String nidList = filePic.getNidList();
        List<String> nList = new Gson().fromJson(nidList, List.class);
        for (int i = 0; i < nList.size(); i++) {
            if (nList.get(i).equals(String.valueOf(nid))) {
                nList.remove(i);
                break;
            }
        }
        filePic.setNidList(new Gson().toJson(nList));
        fileDao.save(filePic);
    }

    //将Lid加入file的nidList中
    private void addLidToFile(Integer fid, Integer lid) {
        FilePic filePic = fileDao.findFilePicByFid(fid);
        String lidList = filePic.getLidList();
        List<String> lList = new Gson().fromJson(lidList, List.class);
        lList.add(String.valueOf(lid));
        filePic.setLidList(new Gson().toJson(lList));
        fileDao.save(filePic);
    }

    //将lid從file的lidList中移除
    private void removeLidFromFile(Integer fid, Integer lid) {
        FilePic filePic = fileDao.findFilePicByFid(fid);
        String lidList = filePic.getLidList();
        List<String> lList = new Gson().fromJson(lidList, List.class);
        for (int i = 0; i < lList.size(); i++) {
            if (lList.get(i).equals(String.valueOf(lid))) {
                lList.remove(i);
                break;
            }
        }
        filePic.setLidList(new Gson().toJson(lList));
        fileDao.save(filePic);
    }

//    //将pid加入node的pidList中
//    private void addPidToNode(Integer nid, Integer pid) {
//        NodePic nodePic = nodeDao.findNodePicByNid(nid);
//        String pidList = nodePic.getPidList();
//        List<String> pList = new Gson().fromJson(pidList, List.class);
//        pList.add(String.valueOf(pid));
//        nodePic.setPidList(new Gson().toJson(pList));
//        nodeDao.save(nodePic);
//    }

//    //将pid從node的pidList中移除
//    private void removePidFromNode(Integer nid, Integer pid) {
//        NodePic nodePic = nodeDao.findNodePicByNid(nid);
//        String pidList = nodePic.getPidList();
//        List<String> pList = new Gson().fromJson(pidList, List.class);
//        for (int i = 0; i < pList.size(); i++) {
//            if (pList.get(i).equals(String.valueOf(pid))) {
//                pList.remove(i);
//                break;
//            }
//        }
//        nodePic.setPidList(new Gson().toJson(pList));
//        nodeDao.save(nodePic);
//    }

    private List<Integer> transStringToList(String listStr) {
        List<String> list = new Gson().fromJson(listStr, List.class);
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            result.add(Integer.parseInt(list.get(i)));
        }
        return result;
    }
}
