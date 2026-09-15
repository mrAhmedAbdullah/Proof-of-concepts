package in.tajdar.service;

import java.util.List;

import in.tajdar.binding.LoginBinding;
import in.tajdar.binding.User;
import in.tajdar.binding.UserActivation;

public interface UserService {
	boolean registerUser(User user);

	boolean activateUser(UserActivation userAct);

	User getUserById(Integer id);

	boolean changeActivateStatus(String status, Integer userId);

	boolean deleteUserById(Integer id);

	List<User> getAllUsers();

	String forgotPassword(String email);

	String login(LoginBinding loginCreds);
}
