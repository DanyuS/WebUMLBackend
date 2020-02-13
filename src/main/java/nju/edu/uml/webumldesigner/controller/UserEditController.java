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

    @GetMapping("/addNode")
    public boolean addProperties(String propertiesId) {
        boolean result = editService.addProperties("u1-1");
        System.out.println("---------------" + result);
        return result;
    }
}
