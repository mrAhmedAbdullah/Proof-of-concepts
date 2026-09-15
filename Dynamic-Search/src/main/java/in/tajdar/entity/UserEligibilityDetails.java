package in.tajdar.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserEligibilityDetails {

	@Id 
	private Integer userId;
	
	private String name;
	
	private Long mobileNumber;
	
	private String email;
	
	private Long ssn;
	
	private String gender;
	
	private String planName;
	
	private String planStatus;
	
	private LocalDate planStartDate;
	
	private LocalDate planEndDate;

	private String createdBy;
	
	private String updatedBy;
	
	private LocalDate createdDate;
	
	private LocalDate updatedDate;
	
	
}
