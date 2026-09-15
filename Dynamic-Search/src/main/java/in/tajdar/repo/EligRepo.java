package in.tajdar.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.tajdar.entity.UserEligibilityDetails;

public interface EligRepo extends JpaRepository<UserEligibilityDetails, Integer>{

	@Query("SELECT DISTINCT e.planStatus FROM UserEligibilityDetails e")
	public List<String> findAllDistinctPlanStatuses();
	
	@Query("SELECT DISTINCT e.planName FROM UserEligibilityDetails e")
	public List<String> findAllDistinctPlanNames();
	
}
