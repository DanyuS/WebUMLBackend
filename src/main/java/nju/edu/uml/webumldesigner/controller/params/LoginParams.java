package nju.edu.uml.webumldesigner.controller.params;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2020/3/8
 */
public class LoginParams {
    String userEmail;
    String userPassword;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
