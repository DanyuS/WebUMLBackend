package nju.edu.uml.webumldesigner.controller;

import nju.edu.uml.webumldesigner.entity.*;
import nju.edu.uml.webumldesigner.service.EditService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserEditController {
    private final EditService editService;

    public UserEditController(EditService editService) {
        this.editService = editService;
    }

    @GetMapping("/createFile")
    public boolean createFile(Integer uid, String fileName, String fileType) {
        boolean result = editService.createFile(uid, fileName, fileType);
        return result;
    }

    @GetMapping("/updateFile")
    public boolean updateFile(Integer fid, String fileName, String fileType) {
        return editService.updateFile(fid, fileName, fileType);
    }

    @GetMapping("/delFile")
    public boolean delFile(Integer uid, Integer fid) {
        boolean result = editService.delFile(uid, fid);
        return result;
    }

    @GetMapping("/addNode")
    public boolean addNode(Integer fid, String nodeType, NodeStyle nodeStyle, Properties properties) {
        return editService.addNode(fid, nodeType, nodeStyle, properties);
    }

    @GetMapping("/updateNode")
    public boolean updateNode(Integer nid, String nodeKey, List<String> key, List<String> value) {
        return editService.updateNode(nid, nodeKey, key, value);
    }

    @GetMapping("/delNode")
    public boolean delNode(Integer fid, Integer nid) {
        return editService.delNode(fid, nid);
    }

    @GetMapping("/addLine")
    public boolean addLine(Integer fid, String relationType, String fromId, String toId, String styles) {
        return editService.addLine(fid, relationType, fromId, toId, styles);
    }

    @GetMapping("/updateLine")
    public boolean updateLine(Integer lid, String relationType, String fromId, String toId, String styles) {
        return editService.updateLine(lid, relationType, fromId, toId, styles);
    }

    @GetMapping("/delLine")
    public boolean delLine(Integer fid, Integer lid) {
        return editService.delLine(fid, lid);
    }

//    @GetMapping("/addProperties")
//    public boolean addProperties(Integer nid) {
//        return editService.addProperties(nid);
//    }
//
//    @GetMapping("/updateProperties")
//    public boolean updateProperties(Integer pid) {
//        return editService.updateProperties(pid);
//    }
//
//    @GetMapping("/delProperties")
//    public boolean delProperties(Integer nid, Integer pid) {
//        return editService.delProperties(nid, pid);
//    }

    @GetMapping("/addVarAndFunc")
    public boolean addVarAndFunc(Integer pid, String modifier, String dataType, String name, String params, String propId, Integer flag) {
        return editService.addVarAndFunc(pid, modifier, dataType, name, params, propId, flag);
    }

    @GetMapping("/delVarAndFUnc")
    public boolean delVarAndFUnc(Integer pid, Integer vid) {
        return editService.delVarAndFUnc(pid, vid);
    }

    @GetMapping("/upDateVarAndFunc")
    public boolean upDateVarAndFunc(Integer pid, Integer vid, String modifier, String dataType, String name, String params, String propId, Integer flag) {
        return editService.upDateVarAndFunc(pid, vid, modifier, dataType, name, params, propId, flag);
    }

    @GetMapping("/getAllFilePicByUid")
    public List<FilePic> getAllFilePicByUid(Integer uid) {
        return editService.getAllFileByUid(uid);
    }

    @GetMapping("/getAllNodeByFid")
    public List<NodePic> getAllNodeByFid(Integer fid) {
        return editService.getAllNodeByFid(fid);
    }

    @GetMapping("/getAllLineByFid")
    public List<Line> getAllLineByFid(Integer fid) {
        return editService.getAllLineByFid(fid);
    }

//    @GetMapping("/getAllPropertiesByNid")
//    public List<Properties> getAllPropertiesByNid(Integer nid) {
//        return editService.getAllPropertiesByNid(nid);
//    }
}
