package in.tajdar.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserMaster {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	private String fullName;

	private Long mobile;

	private String emailId;

	private Long ssn;

	private String gender;

	private String password;

	private LocalDate dateOfBirth;

	private String accountStatus;

	private String createdBy;

	private String updatedBy;
	@UpdateTimestamp
	@Column(updatable = false)
	private LocalDate createdDate;
	
	@CreationTimestamp
	@Column(insertable = false)
	private LocalDate updatedDate;

}
