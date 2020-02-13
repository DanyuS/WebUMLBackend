package nju.edu.uml.webumldesigner.controller;

import nju.edu.uml.webumldesigner.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

//    @Autowired
//    private UserDao userDao = null;

    private final LoginService loginService;

    @Autowired
    public HelloController(LoginService loginService) {
        this.loginService = loginService;
    }

//    @Autowired
//    public void setDataSource(DataSource dataSource) {
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
//    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {

//        String userName = loginService.isValidLogin("sqq@qq.com", "123456").getUserName();
//        if (userName != null) {
//            return userName;
//        }
        return "hello";
//        String sql = "SELECT Gid FROM usergroup WHERE gid = ?";
//
//        // 通过jdbcTemplate查询数据库
//        int mobile = (Integer)jdbcTemplate.queryForObject(
//                sql, new Object[] { 1 }, Integer.class);
//
//        return "Hello " + mobile;
    }
}
