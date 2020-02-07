package nju.edu.uml.webumldesigner.daoImpl;

import nju.edu.uml.webumldesigner.dao.UserDao;
import nju.edu.uml.webumldesigner.repository.User;
import nju.edu.uml.webumldesigner.repository.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User isValidLogin(String userEmail, String userPassword) {
        String sql = "SELECT * FROM user where userEmail = ? and userPassword = ?";
        List<User> userList = jdbcTemplate.query(sql, new Object[]{userEmail, userPassword}, new BeanPropertyRowMapper(User.class));
        if (userList.size() != 0) {
            return userList.get(0);
        }
//        String sql = "SELECT * FROM user WHERE userName = ? and userPassword = ?";
//        user=jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class));
//        System.out.print("-----------" + user.getUserEmail());
        //new BeanPropertyRowMapper<>(User.class)
//        DataSource dataSource = jdbcTemplate.getDataSource();
//        Connection con = DataSourceUtils.getConnection(dataSource);
//        PreparedStatement pst = con.prepareStatement(sql);
//        pst.setString(1,userName);
//        pst.setString(2,userPassword);
//        ResultSet resultSet = pst.executeQuery();
//        System.out.print("-----------");
//        user = jdbcTemplate.query(sql, new Object[]{userName, userPassword});
//        PreparedStatement pst;
        return userList.get(0);
    }
}
