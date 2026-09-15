package in.tajdar.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.tajdar.binding.LoginBinding;
import in.tajdar.binding.User;
import in.tajdar.binding.UserActivation;
import in.tajdar.service.UserService;

@RestController
public class ReportRestController {
	@Autowired

	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) {
		boolean registerUser = userService.registerUser(user);

		if (registerUser) {
			return new ResponseEntity<>("User Reegistered successfully", HttpStatus.CREATED);
		} else {

			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
		User userById = userService.getUserById(userId);
		return new ResponseEntity<User>(userById, HttpStatus.OK);

	}

	@PostMapping("/activate")
	public ResponseEntity<String> activateUser(@RequestBody UserActivation activate) {
		boolean isActivated = userService.activateUser(activate);
		if (isActivated) {
			return new ResponseEntity<String>("account activated ", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Invalid temporary password", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/forgetPassword/{email}")
	public ResponseEntity<String> forgetPwd(@PathVariable String email) {
		String forgotPassword = userService.forgotPassword(email);

		return new ResponseEntity<>(forgotPassword, HttpStatus.OK);
	}

	@PutMapping("/status-change/{id}/{status}")
	public ResponseEntity<String> statusChange(@PathVariable Integer id, @PathVariable String status) {
		boolean isChanged = userService.changeActivateStatus(status, id);
		String msg = isChanged ? "status Changed Successfully" : "Something went wrong";
		return isChanged ? new ResponseEntity<String>(msg, HttpStatus.OK)
				: new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		boolean isDeleted = userService.deleteUserById(id);
		String msg = isDeleted ? "User deleted Successfully" : "Something went wrong";
		if (isDeleted) {

			return new ResponseEntity<String>(msg, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> allUsers = userService.getAllUsers();
		return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginBinding loginUser) {
		String login = userService.login(loginUser);
		return new ResponseEntity<String>(login, HttpStatus.OK);
	}
}
