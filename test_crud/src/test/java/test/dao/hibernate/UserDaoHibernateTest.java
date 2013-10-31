package test.dao.hibernate;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import test.dao.UserDao;
import test.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Component
public class UserDaoHibernateTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private UserDao userDao;

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setUserName("ceshi");
		user.setAge(25);
		user.setPhone("10101001");
		user.setRdate(new Date());

		System.out.println(userDao.createUser(user));
	}

	@Test
	public void testDeleteUser() {
		userDao.deleteUser(8);
	}

	@Test
	public void testUpdateUser() {
		User user = new User();
		user.setUserName("111");
		user.setPhone("1");
		user.setRdate(new Date());
		userDao.updateUser(user);
	}

	@Test
	public void testQueryUser() {
		List<User> userList = userDao.queryUser();
		for (int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
			System.out.println(user.getUserName());
		}
		
	}

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
