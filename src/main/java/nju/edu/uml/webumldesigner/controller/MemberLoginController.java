package nju.edu.uml.webumldesigner.controller;

import nju.edu.uml.webumldesigner.repository.User;
import nju.edu.uml.webumldesigner.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberLoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/users")
    public User isValidLogin(String userEmail, String userPassword) {
        return loginService.isValidLogin(userEmail, userPassword);
    }
}
