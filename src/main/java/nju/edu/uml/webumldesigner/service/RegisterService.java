package nju.edu.uml.webumldesigner.service;

public interface RegisterService {
    public boolean userRegister(String userName, String userEmail, String userPassword, String code);

    public String resetPwd(String userEmail, String userPassword, String code);
}
