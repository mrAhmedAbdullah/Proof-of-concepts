package in.tajdar.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class User {
	private String fullName;
	private String emailId;
	private String gender;
	private LocalDate dateOfBirth;
	private Long ssn;

}
