package nju.edu.uml.webumldesigner.serviceImpl;

import com.google.gson.Gson;
import nju.edu.uml.webumldesigner.controller.params.LineParams;
import nju.edu.uml.webumldesigner.dao.*;
import nju.edu.uml.webumldesigner.entity.*;
import nju.edu.uml.webumldesigner.service.EditService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EditServiceImpl implements EditService {

    private final UserDao userDao;

    private final FileDao fileDao;

    private final NodeDao nodeDao;

    private final LineDao lineDao;

    private final LinePositionDao linePositionDao;

    private final LineStyleDao lineStyleDao;

    private final PropertiesDao propertiesDao;

    private final VarAndFuncDao varAndFuncDao;

    private final NodeStyleDao nodeStyleDao;

    private final RevertEditTableDao revertEditTableDao;

    public EditServiceImpl(UserDao userDao, FileDao fileDao, NodeDao nodeDao, LineDao lineDao, LinePositionDao linePositionDao, LineStyleDao lineStyleDao, PropertiesDao propertiesDao, VarAndFuncDao varAndFuncDao, NodeStyleDao nodeStyleDao, RevertEditTableDao revertEditTableDao) {
        this.userDao = userDao;
        this.fileDao = fileDao;
        this.nodeDao = nodeDao;
        this.lineDao = lineDao;
        this.linePositionDao = linePositionDao;
        this.lineStyleDao = lineStyleDao;
        this.propertiesDao = propertiesDao;
        this.varAndFuncDao = varAndFuncDao;
        this.nodeStyleDao = nodeStyleDao;
        this.revertEditTableDao = revertEditTableDao;
    }


    @Override
    public Properties getPropertiesByPid(Integer pid) {
        return null;
    }

    @Override
    public Integer createFile(Integer uid, String fileName, String fileType) {
        FilePic filePic = new FilePic();
        filePic.setFileName(fileName);
        filePic.setFileType(fileType);
        String num = String.valueOf(fileDao.count() + 1);
        filePic.setFileId("f" + num);
        filePic.setRefreshTime(0);
//        filePic.setNidList("[]");
        FilePic result = fileDao.save(filePic);
        if (result.getFid() > 0) {
            //加入user的fidList中
            Integer fid = result.getFid();
            addFidToUser(uid, fid);
            //修改fileId
            FilePic file = fileDao.findFilePicByFid(result.getFid());
            file.setFileId("f" + result.getFid());
            fileDao.save(file);
            return result.getFid();
        }
        return -1;
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
    public Integer addNode(Integer uid, Integer gid, Integer fid, String nodeType, NodeStyle nodeStyle, Properties properties) {
//        nodeStyleDao.save(nodeStyle);
//        propertiesDao.save(properties);

        NodePic nodePic = new NodePic();
        nodePic.setNodeStyle(nodeStyle);
        nodePic.setNodeType(nodeType);
        nodePic.setProperties(properties);
        nodePic.setUid(uid);
        nodePic.setGid(gid);
        nodePic.setFid(fid);
        //日后要改成用户id加啥啥还是别的怎么用法
        String num = String.valueOf(nodeDao.count() + 1);
        nodePic.setNodeId("n" + num);

        nodePic.setEditMethod("Add");
        nodePic.setIsDeleted("F");

        nodeStyleDao.save(nodeStyle);
        propertiesDao.save(properties);

        NodePic result = nodeDao.save(nodePic);
        if (result.getNid() > 0) {
            Integer nid = Integer.parseInt(num);
            addNidToFile(fid, nid);

            RevertEditTable revertEditTable = new RevertEditTable();
            revertEditTable.setFid(fid);
            revertEditTable.setUid(uid);
            revertEditTable.setComponentType("Node");
            revertEditTable.setComponentId(result.getNid());
            revertEditTableDao.save(revertEditTable);

            return result.getNid();
        }
        return -1;
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
                }
            }
            nodePic.setProperties(properties);
            nodePic.setEditMethod("Update");
            propertiesDao.save(properties);
        } else {
            NodeStyle nodeStyle = nodePic.getNodeStyle();
            for (int i = 0; i < key.size(); i++) {
                switch (key.get(i)) {
                    case "width":
                        nodeStyle.setStyleWidth(Double.parseDouble(value.get(i)));
                        break;
                    case "height":
                        nodeStyle.setStyleHeight(Double.parseDouble(value.get(i)));
                        break;
                    case "left":
                        nodeStyle.setStyleLeft(Double.parseDouble(value.get(i)));
                        break;
                    case "top":
                        nodeStyle.setStyleTop(Double.parseDouble(value.get(i)));
                        break;
                }
            }
            nodePic.setNodeStyle(nodeStyle);
            nodePic.setEditMethod("Update");
            nodeStyleDao.save(nodeStyle);
        }
        nodeDao.save(nodePic);

        RevertEditTable revertEditTable = new RevertEditTable();
        revertEditTable.setFid(nodePic.getFid());
        revertEditTable.setUid(nodePic.getUid());
        revertEditTable.setComponentType("Node");
        revertEditTable.setComponentId(nid);
        revertEditTableDao.save(revertEditTable);

        return true;
    }

    @Override
    public boolean delNode(Integer fid, Integer nid) {
        NodePic nodePic = nodeDao.findNodePicByNid(nid);
        nodePic.setEditMethod("Delete");
        nodePic.setIsDeleted("T");
        nodeDao.save(nodePic);
//        NodeStyle nodeStyle = nodePic.getNodeStyle();
//        nodeStyleDao.delete(nodeStyle);
//        Properties properties = nodePic.getProperties();
//        propertiesDao.delete(properties);
//        nodeDao.delete(nodePic);
//        removeNidFromFile(fid, nid);
        RevertEditTable revertEditTable = new RevertEditTable();
        revertEditTable.setFid(fid);
        revertEditTable.setUid(nodePic.getUid());
        revertEditTable.setComponentType("Node");
        revertEditTable.setComponentId(nid);
        revertEditTableDao.save(revertEditTable);
        return true;
    }

    @Override
    public Integer addLine(LineParams lineParams) {
        //TODO 各个daosave的位置问题，包括上面的addNode
        LinePosition startPosition = new LinePosition();
        startPosition.setLpLeft(lineParams.getStartPosition().getLeft());
        startPosition.setLpTop(lineParams.getStartPosition().getTop());

//        linePositionDao.save(startPosition);

        LinePosition endPosition = new LinePosition();
        endPosition.setLpLeft(lineParams.getEndPosition().getLeft());
        endPosition.setLpTop(lineParams.getEndPosition().getTop());

//        linePositionDao.save(endPosition);

        LineStyle lineStyle = new LineStyle();
        lineStyle.setStroke(lineParams.getLineStyle().getStroke());
        lineStyle.setStrokeDasharray(lineParams.getLineStyle().getStrokeDasharray());
        lineStyle.setStrokeWidth(lineParams.getLineStyle().getStrokeWidth());
        lineStyle.setFill(lineParams.getLineStyle().getFill());

//        lineStyleDao.save(lineStyle);

//        lineSvgStyleDao.save(lineSvgStyle);

        Line line = new Line();
        line.setLineId(lineParams.getLineId());
        line.setRelationType(lineParams.getRelationType());
        line.setFromId(lineParams.getFromId());
        line.setToId(lineParams.getToId());
        line.setText(lineParams.getText());
        line.setMarkerStart(lineParams.getMarkerStart());
        line.setMarkerEnd(lineParams.getMarkerEnd());
        line.setStartPosition(startPosition);
        line.setEndPosition(endPosition);
        line.setLineStyle(lineStyle);
        line.setUid(lineParams.getUid());
        line.setGid(lineParams.getGid());
        line.setFid(lineParams.getFid());

        line.setEditMethod("Add");
        line.setIsDeleted("F");

        line.setPath(lineParams.getPath());

        line.setStartArrow(lineParams.getStartArrow());
        line.setEndArrow(lineParams.getEndArrow());
        line.setStartArrowStyle(lineParams.getStartArrowStyle());
        line.setEndArrowStyle(lineParams.getEndArrowStyle());

        linePositionDao.save(startPosition);
        linePositionDao.save(endPosition);
        lineStyleDao.save(lineStyle);


        Line result = lineDao.save(line);

        if (result.getLid() > 0) {
            addLidToFile(lineParams.getFid(), result.getLid());

        }

        RevertEditTable revertEditTable = new RevertEditTable();
        revertEditTable.setFid(line.getFid());
        revertEditTable.setUid(line.getUid());
        revertEditTable.setComponentType("Line");
        revertEditTable.setComponentId(result.getLid());
        revertEditTableDao.save(revertEditTable);

        return result.getLid();
    }

    @Override
    public boolean updateLine(LineParams lineParams) {
        Integer lid = lineParams.getLid();
        Line line = lineDao.findLineByLid(lid);

        ///////////////////////////= linePositionDao.find...(line.getStartPosition().getId())???????
        LinePosition startPosition = line.getStartPosition();
        startPosition.setLpLeft(lineParams.getStartPosition().getLeft());
        startPosition.setLpTop(lineParams.getStartPosition().getTop());

        linePositionDao.save(startPosition);

        LinePosition endPosition = line.getEndPosition();
        endPosition.setLpLeft(lineParams.getEndPosition().getLeft());
        endPosition.setLpTop(lineParams.getEndPosition().getTop());

        linePositionDao.save(endPosition);

        LineStyle lineStyle = line.getLineStyle();
        lineStyle.setStroke(lineParams.getLineStyle().getStroke());
        lineStyle.setStrokeDasharray(lineParams.getLineStyle().getStrokeDasharray());
        lineStyle.setStrokeWidth(lineParams.getLineStyle().getStrokeWidth());
        lineStyle.setFill(lineParams.getLineStyle().getFill());

        lineStyleDao.save(lineStyle);

        line.setLineId(lineParams.getLineId());
        line.setRelationType(lineParams.getRelationType());
        line.setFromId(lineParams.getFromId());
        line.setToId(lineParams.getToId());
        line.setText(lineParams.getText());
        line.setMarkerStart(lineParams.getMarkerStart());
        line.setMarkerEnd(lineParams.getMarkerEnd());
        line.setStartPosition(startPosition);
        line.setEndPosition(endPosition);
        line.setLineStyle(lineStyle);
        line.setEditMethod("Update");
        line.setPath(lineParams.getPath());

        line.setStartArrow(lineParams.getStartArrow());
        line.setEndArrow(lineParams.getEndArrow());
        line.setStartArrowStyle(lineParams.getStartArrowStyle());
        line.setEndArrowStyle(lineParams.getEndArrowStyle());
//        line.setUid(lineParams.getUid());
//        line.setGid(lineParams.getGid());
        lineDao.save(line);

        RevertEditTable revertEditTable = new RevertEditTable();
        revertEditTable.setFid(line.getFid());
        revertEditTable.setUid(line.getUid());
        revertEditTable.setComponentType("Line");
        revertEditTable.setComponentId(lid);
        revertEditTableDao.save(revertEditTable);
        return true;
    }

    @Override
    public boolean delLine(Integer fid, Integer lid) {
        Line line = lineDao.findLineByLid(lid);
        line.setEditMethod("Delete");
        line.setIsDeleted("T");
        lineDao.save(line);
//        lineDao.delete(line);
//        removeLidFromFile(fid, lid);

        RevertEditTable revertEditTable = new RevertEditTable();
        revertEditTable.setFid(line.getFid());
        revertEditTable.setUid(line.getUid());
        revertEditTable.setComponentType("Line");
        revertEditTable.setComponentId(lid);
        revertEditTableDao.save(revertEditTable);
        return true;
    }

    @Override
    public Integer addVarAndFunc(Integer nid, String modifier, String dataType, String name, String params, Integer flag) {
        VarAndFunc varAndFunc = new VarAndFunc();
        varAndFunc.setModifier(modifier);
        varAndFunc.setDataType(dataType);
        varAndFunc.setName(name);
        varAndFunc.setParams(params);
        varAndFunc.setFlag(flag);
        varAndFunc.setIsDeleted("F");
        VarAndFunc result = varAndFuncDao.save(varAndFunc);
        NodePic nodePic = nodeDao.findNodePicByNid(nid);
        Properties properties = nodePic.getProperties();
        if (flag == 0) {
            List<VarAndFunc> varAndFuncList = properties.getVariables();
            varAndFuncList.add(varAndFunc);
            properties.setVariables(varAndFuncList);
        } else {
            List<VarAndFunc> varAndFuncList = properties.getFunctions();
            varAndFuncList.add(varAndFunc);
            properties.setFunctions(varAndFuncList);
        }
        propertiesDao.save(properties);
        nodePic.setProperties(properties);
        nodeDao.save(nodePic);
        RevertEditTable revertEditTable = new RevertEditTable();
        revertEditTable.setFid(nodePic.getFid());
        revertEditTable.setUid(nodePic.getUid());
        revertEditTable.setComponentType("VarAndFunc");
        revertEditTable.setComponentId(result.getVid());
        revertEditTableDao.save(revertEditTable);
        return result.getVid();
    }

    @Override
    public boolean delVarAndFUnc(Integer nid, Integer vid) {
        NodePic nodePic = nodeDao.findNodePicByNid(nid);
        Properties properties = nodePic.getProperties();
        VarAndFunc varAndFunc = varAndFuncDao.findVarAndFuncByVid(vid);
        varAndFunc.setIsDeleted("T");
        varAndFuncDao.save(varAndFunc);
        if (varAndFunc.getFlag() == 0) {
            List<VarAndFunc> varAndFuncList = properties.getVariables();
            for (int i = 0; i < varAndFuncList.size(); i++) {
                if (varAndFuncList.get(i).getVid().equals(varAndFunc.getVid())) {
                    varAndFuncList.remove(i);
                    break;
                }
            }
            properties.setVariables(varAndFuncList);
        } else if (varAndFunc.getFlag() == 1) {
            List<VarAndFunc> varAndFuncList = properties.getFunctions();
            for (int i = 0; i < varAndFuncList.size(); i++) {
                if (varAndFuncList.get(i).getVid().equals(varAndFunc.getVid())) {
                    varAndFuncList.remove(i);
                    break;
                }
            }
            properties.setFunctions(varAndFuncList);
        }
        propertiesDao.save(properties);
        nodePic.setProperties(properties);
        nodeDao.save(nodePic);
        RevertEditTable revertEditTable = new RevertEditTable();
        revertEditTable.setFid(nodePic.getFid());
        revertEditTable.setUid(nodePic.getUid());
        revertEditTable.setComponentType("VarAndFunc");
        revertEditTable.setComponentId(vid);
        revertEditTableDao.save(revertEditTable);
        return true;
    }

    @Override
    public boolean upDateVarAndFunc(Integer nid, Integer vid, String modifier, String dataType, String name, String params, Integer flag) {
        VarAndFunc varAndFunc = varAndFuncDao.findVarAndFuncByVid(vid);
        Integer originalFlag = varAndFunc.getFlag();
        boolean isEqual = originalFlag == flag;
        varAndFunc.setModifier(modifier);
        varAndFunc.setDataType(dataType);
        varAndFunc.setName(name);
        varAndFunc.setParams(params);
        varAndFunc.setFlag(flag);
        varAndFuncDao.save(varAndFunc);
        NodePic nodePic = nodeDao.findNodePicByNid(nid);
        Properties properties = nodePic.getProperties();
        if (isEqual) {
            if (flag == 0) {
                List<VarAndFunc> varAndFuncList = properties.getVariables();
                for (int i = 0; i < varAndFuncList.size(); i++) {
                    if (varAndFuncList.get(i).getVid().equals(varAndFunc.getVid())) {
                        varAndFuncList.remove(i);
                        varAndFuncList.add(varAndFunc);
                        break;
                    }
                }
                properties.setVariables(varAndFuncList);
            } else {
                List<VarAndFunc> varAndFuncList = properties.getFunctions();
                for (int i = 0; i < varAndFuncList.size(); i++) {
                    if (varAndFuncList.get(i).getVid().equals(varAndFunc.getVid())) {
                        varAndFuncList.remove(i);
                        varAndFuncList.add(varAndFunc);
                        break;
                    }
                }
                properties.setFunctions(varAndFuncList);
            }
        } else {
            if (flag == 0) {
                List<VarAndFunc> varList = properties.getVariables();
                varList.add(varAndFunc);
                properties.setVariables(varList);

                List<VarAndFunc> funcList = properties.getFunctions();
                for (int i = 0; i < funcList.size(); i++) {
                    if (funcList.get(i).getVid().equals(varAndFunc.getVid())) {
                        funcList.remove(i);
                        break;
                    }
                }
                properties.setFunctions(funcList);
            } else {
                List<VarAndFunc> funcList = properties.getFunctions();
                funcList.add(varAndFunc);
                properties.setFunctions(funcList);

                List<VarAndFunc> varList = properties.getVariables();
                for (int i = 0; i < varList.size(); i++) {
                    if (varList.get(i).getVid().equals(varAndFunc.getVid())) {
                        varList.remove(i);
                        break;
                    }
                }
                properties.setVariables(varList);
            }
        }
        propertiesDao.save(properties);
        nodePic.setProperties(properties);
        nodeDao.save(nodePic);

        RevertEditTable revertEditTable = new RevertEditTable();
        revertEditTable.setFid(nodePic.getFid());
        revertEditTable.setUid(nodePic.getUid());
        revertEditTable.setComponentType("VarAndFunc");
        revertEditTable.setComponentId(vid);
        revertEditTableDao.save(revertEditTable);

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
        List<Integer> nidList = filePic.getNidList();
        List<NodePic> result = new ArrayList<NodePic>();
        for (int i = 0; i < nidList.size(); i++) {
            NodePic nodePic = nodeDao.findNodePicByNid(nidList.get(i));
            if (nodePic.getIsDeleted().equals("F")) {
                result.add(nodePic);
            }
//            result.add(nodePic);
        }
        return result;
    }

    @Override
    public List<Line> getAllLineByFid(Integer fid) {
        FilePic filePic = fileDao.findFilePicByFid(fid);
        List<Integer> lidList = filePic.getLidList();
        List<Line> result = new ArrayList<Line>();
        for (int i = 0; i < lidList.size(); i++) {
            Line line = lineDao.findLineByLid(lidList.get(i));
            if (line.getIsDeleted().equals("F")) {
                result.add(line);
            }
        }
        return result;
    }

    @Override
    public FilePic getFilePicByFid(Integer fid) {
        return fileDao.findFilePicByFid(fid);
    }

    @Override
    public NodePic getNodePicByNid(Integer nid) {
        return nodeDao.findNodePicByNid(nid);
    }

    @Override
    public Line getLineByLid(Integer lid) {
        return lineDao.findLineByLid(lid);
    }

    @Override
    public Integer getRefreshTime(Integer fid) {
        FilePic filePic = fileDao.findFilePicByFid(fid);
        Integer refreshTime = filePic.getRefreshTime();
        refreshTime++;
        filePic.setRefreshTime(refreshTime);
        fileDao.save(filePic);
        return refreshTime;
    }

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
        //lazy=false
//        Hibernate.initialize(filePic.getNidList());
        List<Integer> nidList = filePic.getNidList();
        nidList.add(nid);
        filePic.setNidList(nidList);
        fileDao.save(filePic);
    }

    //将nid從file的nidList中移除
    private void removeNidFromFile(Integer fid, Integer nid) {
        FilePic filePic = fileDao.findFilePicByFid(fid);
        List<Integer> nidList = filePic.getNidList();
        for (int i = 0; i < nidList.size(); i++) {
            if (nidList.get(i).equals(nid)) {
                nidList.remove(i);
                break;
            }
        }
        filePic.setNidList(nidList);
        fileDao.save(filePic);
    }

    //将Lid加入file的nidList中
    private void addLidToFile(Integer fid, Integer lid) {
        FilePic filePic = fileDao.findFilePicByFid(fid);
        List<Integer> lidList = filePic.getLidList();
        lidList.add(lid);
        filePic.setLidList(lidList);
        fileDao.save(filePic);
    }

    //将lid從file的lidList中移除
    private void removeLidFromFile(Integer fid, Integer lid) {
        FilePic filePic = fileDao.findFilePicByFid(fid);
        List<Integer> lidList = filePic.getLidList();
        for (int i = 0; i < lidList.size(); i++) {
            if (lidList.get(i).equals(lid)) {
                lidList.remove(i);
                break;
            }
        }
        filePic.setLidList(lidList);
        fileDao.save(filePic);
    }

    private List<Integer> transStringToList(String listStr) {
        List<String> list = new Gson().fromJson(listStr, List.class);
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            result.add(Integer.parseInt(list.get(i)));
        }
        return result;
    }

}
