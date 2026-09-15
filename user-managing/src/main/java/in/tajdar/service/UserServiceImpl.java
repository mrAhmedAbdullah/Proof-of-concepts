package in.tajdar.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.tajdar.binding.LoginBinding;
import in.tajdar.binding.User;
import in.tajdar.binding.UserActivation;
import in.tajdar.entity.UserMaster;
import in.tajdar.repo.UserRepo;
import in.tajdar.utils.MailUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo repo;
	@Autowired
	private MailUtils mailUtils;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public boolean activateUser(UserActivation userAct) {
		// tempPwd == entityPwd
		// entityPwd = <- newPwd
		// actStatus = <- Active
		UserMaster example = new UserMaster();
		example.setEmailId(userAct.getEmailId());
		example.setPassword(userAct.getTempPassword());
		List<UserMaster> all = repo.findAll(Example.of(example));

		if (!all.isEmpty()) {
			UserMaster userMaster = all.get(0);
			userMaster.setPassword(userAct.getNewpassword());
			userMaster.setAccountStatus("Active");
			repo.save(userMaster);
		} else {
			return false;
		}

		return true;
	}

	@Override
	public boolean changeActivateStatus(String status, Integer userId) {
		Optional<UserMaster> byId = repo.findById(userId);
		if (byId.isPresent()) {
			UserMaster userMaster = byId.get();
			userMaster.setAccountStatus(status);
			UserMaster save = repo.save(userMaster);
			return save.getAccountStatus().equals(status);
		} else
			return false;
	}

	@Override
	public boolean deleteUserById(Integer id) {
		boolean status = false;
		try {
			repo.deleteById(id);
			status = true;
		} catch (Exception e) {
			System.out.println("Error while deleting!");
//			e.printStackTrace();
			logger.error("Exception occured ", e);
			return status;
		}
		return status;
	}

	@Override
	public String forgotPassword(String email) {
		UserMaster byEmail = repo.findByEmailId(email);
		if (byEmail != null) {
			String password = byEmail.getPassword();
			String mailBody = readMailBody(byEmail.getFullName(), password, "FORGOT-PWD.txt");
			boolean sendMail = mailUtils.sendMail(email, "Recover Password", mailBody);
			return sendMail ? "passwrod sent to email" : "Something went wrong";
		} else {
			return "Email not registered";
		}

	}

	@Override
	public List<User> getAllUsers() {
		List<UserMaster> all = repo.findAll();
		List<User> users = new ArrayList<User>();
		for (UserMaster master : all) {
			User user = new User();
			BeanUtils.copyProperties(master, user);
			users.add(user);
		}
		return users;
	}

	@Override
	public User getUserById(Integer id) {
		Optional<UserMaster> byId = repo.findById(id);
		if (byId.isPresent()) {
			UserMaster userMaster = byId.get();
			User user = new User();
			BeanUtils.copyProperties(userMaster, user);
			return user;
		}
		return null;
	}

	@Override
	public String login(LoginBinding loginCreds) {
		String emailId = loginCreds.getEmailId();
		String password = loginCreds.getPassword();
		UserMaster record = repo.findByEmailIdAndPassword(emailId, password);
		if (record == null) {
			return "record not found";
		}
		if (!record.getAccountStatus().equals("Active")) {
			return "Account not activated";
		} else {
			return "login successful";
		}
	}

	@Override
	public boolean registerUser(User user) {
		boolean isExists = repo.existsByEmailId(user.getEmailId());
		if (isExists) {
			return false;
		} else {
			UserMaster userMaster = new UserMaster();
			BeanUtils.copyProperties(user, userMaster);
			String tempPwd = generatePassword();
			userMaster.setPassword(tempPwd);
			userMaster.setAccountStatus("In-Active");
			String mailBody = readMailBody(userMaster.getFullName(), userMaster.getPassword(), "REG-EMAIL-BODY.txt");
			boolean mailSent = mailUtils.sendMail(userMaster.getEmailId(), "Account Registered", mailBody);
			UserMaster save = repo.save(userMaster);

			boolean msg = save.getUserId() != null && mailSent ? true : false;
			return msg;
		}
	}

	private String generatePassword() {
		String chars = "ABCDEFGHIKJKLMNOPQRSTUVWXYZ" + "_" + "abcdefghikjklmnopqrstuvwxyz" + "1234567890";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			int index = random.nextInt(chars.length());
			char charAt = chars.charAt(index);
			sb.append(charAt);
		}
//6char pwd
		return sb.toString();
	}

	private String readMailBody(String fullName, String pwd, String bodyFileName) {
		// single method for forgetPwd and Registration Mail
		String mailBody = "";
		StringBuilder sb = new StringBuilder();
		try (FileReader fr = new FileReader(bodyFileName); BufferedReader br = new BufferedReader(fr)) {
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			mailBody = sb.toString();
			mailBody = mailBody.replace("FULLNAME", fullName);
			mailBody = mailBody.replace("TEMP-PWD", pwd);
			mailBody = mailBody.replace("PWD", pwd);
			// get URL from application.properties
			String url = "";
			mailBody = mailBody.replace("{URL}", url);

		} catch (Exception e) {
//			e.printStackTrace();
			logger.error("Exception occured ", e);
		}
		return mailBody;
	}

}
