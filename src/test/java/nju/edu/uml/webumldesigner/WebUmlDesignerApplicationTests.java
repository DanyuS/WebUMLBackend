//package nju.edu.uml.webumldesigner;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = WebUmlDesignerApplication.class)
//class WebUmlDesignerApplicationTests {
//
//	private JdbcTemplate jdbcTemplate;
//	private ApplicationContext context = null;
//
//	//初始化连接池
//	{
//		context = new ClassPathXmlApplicationContext("application.properties");
//		jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
//	}
//
//	//测试是否连接数据库
//	@Test
//	public void testIsConnect() throws SQLException {
//		DataSource dataSource = context.getBean(DataSource.class);
//		System.out.println("连接成功"+dataSource.getConnection());
//	}
//
////    @Autowired
////	private UserDaoImpl userDao;
////
////	@Test
////	void contextLoads() {
////		UserGroup user = userDao.isValidLogin("sqq", "123456");
////		System.out.print(user.getGid());
////	}
//
////	@Autowired
////	private UserDao userDao = new UserDaoImpl();
////
////	@Test
////	void contextLoads() {
////		User user = userDao.isValidLogin("sqq", "123456");
////		System.out.print(user.getUid());
////	}
//
//}
