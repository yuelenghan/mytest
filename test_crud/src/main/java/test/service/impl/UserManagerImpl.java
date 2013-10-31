package test.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import test.dao.UserDao;
import test.model.User;
import test.service.UserManager;


@Component("userManager")
public class UserManagerImpl implements UserManager {

	private UserDao userDao;

	public long createUser(User user) {
		return userDao.createUser(user);
	}

	public User getUserByName(String userName) {
		List<User> resultList = userDao.getUserByName(userName);
		if (resultList != null && resultList.size() > 0) {
			return resultList.get(0);
		}
		return null;
	}

	public boolean ifUserExists(String userName) {
		User user = this.getUserByName(userName);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<User> queryUser(int start, int limit) {
		return userDao.queryUser(start, limit);
	}

	public List<User> queryUser() {
		return userDao.queryUser();
	}
	
	public long getUserCount() {
		return userDao.getUserCount();
	}
	
	public void deleteUser(String userName) {
		List<User> list = userDao.getUserByName(userName);
		if(list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i ++) {
				userDao.deleteUser(list.get(i));
			}
		}
	}
	
	public void deleteUser(User user) {
		userDao.deleteUser(user);
	}
	
	public User getUserByPhone(String phone) {
		List<User> resultList = userDao.getUserByPhone(phone);
		if (resultList != null && resultList.size() > 0) {
			return resultList.get(0);
		}
		return null;
	}
	
	public void updateUser(User user) {
		userDao.updateUser(user);
	}
	
	public User getUser(long id) {
		List<User> list = userDao.getUser(id);;
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
