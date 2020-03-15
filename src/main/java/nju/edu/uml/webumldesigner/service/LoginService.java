package nju.edu.uml.webumldesigner.service;

import nju.edu.uml.webumldesigner.entity.User;

public interface LoginService {
    public User isValidLogin(String userEmail, String userPassword);

    public User getUserByUid(Integer uid);
}
