package nju.edu.uml.webumldesigner.dao;

import nju.edu.uml.webumldesigner.repository.User;

//@Mapper
public interface UserDao {
    public User isValidLogin(String userEmail, String userPassword);
//    public User isValidLogin(@Param("userName")String userName, @Param("userPassword")String userPassword);
}
