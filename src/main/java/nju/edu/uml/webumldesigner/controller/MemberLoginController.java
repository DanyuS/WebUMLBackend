package nju.edu.uml.webumldesigner.controller;

import nju.edu.uml.webumldesigner.controller.params.LoginParams;
import nju.edu.uml.webumldesigner.entity.User;
import nju.edu.uml.webumldesigner.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberLoginController {
    private final LoginService loginService;

    @Autowired
    public MemberLoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public Integer isValidLogin(@RequestBody LoginParams loginParams) {
        User user = loginService.isValidLogin(loginParams.getUserEmail(), loginParams.getUserPassword());
        if(user != null){
            return user.getUid();
        }
        return -1;
    }
}
