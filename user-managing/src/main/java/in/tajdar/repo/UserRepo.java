package in.tajdar.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.tajdar.entity.UserMaster;

public interface UserRepo extends JpaRepository<UserMaster, Integer> {
	public UserMaster findByEmailId(String emialId);

	public UserMaster findByEmailIdAndPassword(String emailId, String password);

	public boolean existsByEmailId(String emailId);

}
