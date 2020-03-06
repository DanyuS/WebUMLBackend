package nju.edu.uml.webumldesigner.controller;

import nju.edu.uml.webumldesigner.service.RegisterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public boolean register(String userName, String userEmail, String userPassword){
        boolean result = registerService.userRegister(userName, userEmail, userPassword);
//        boolean result = registerService.userRegister("userName1", "userEmail1", "userPassword1");
        return result;
    }
}
