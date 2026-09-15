package in.tajdar.binding;

import lombok.Data;

@Data
public class UserActivation {
	private String emailId;
	private String tempPassword;
	private String newpassword;
}
