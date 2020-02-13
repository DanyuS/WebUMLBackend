package nju.edu.uml.webumldesigner.controller;

import nju.edu.uml.webumldesigner.service.EditService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/delFile")
    public boolean delFile(Integer uid, Integer fid) {
        boolean result = editService.delFile(uid, fid);
        return result;
    }

    @GetMapping("/addNode")
    public boolean addNode(Integer fid, String nodeStyle, String nodeType){
        return editService.addNode(fid, nodeStyle, nodeType);
    }

    @GetMapping("/delNode")
    public boolean delNode(Integer fid, Integer nid){
        return editService.delNode(fid, nid);
    }

    @GetMapping("/addLine")
    public boolean addLine(Integer fid, String relationType, String fromId, String toId, String styles){
        return editService.addLine(fid, relationType, fromId, toId, styles);
    }

    @GetMapping("/delLine")
    public boolean delLine(Integer fid, Integer lid){
        return editService.delLine(fid, lid);
    }

    @GetMapping("/addProperties")
    public boolean addProperties(Integer nid) {
        return editService.addProperties(nid);
    }

    @GetMapping("/delProperties")
    public boolean delProperties(Integer nid, Integer pid){
        return editService.delProperties(nid, pid);
    }
}
