package test.service;

import java.util.List;

import test.model.User;


public interface UserManager {

	public long createUser(User user);
	public User getUserByName(String userName);
	public boolean ifUserExists(String userName);
	public List<User> queryUser();
	public List<User> queryUser(int start, int limit);
	public long getUserCount();
	public void deleteUser(String userName);
	public void deleteUser(User user);
	public void updateUser(User user);
	public User getUserByPhone(String phone);
	public User getUser(long id);
}
