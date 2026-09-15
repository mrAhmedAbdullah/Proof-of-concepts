package in.tajdar.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.tajdar.entity.UserEligibilityDetails;
import in.tajdar.repo.EligRepo;

@Component
public class AppRunnerImpl implements ApplicationRunner {
	@Autowired
	private EligRepo eligRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		UserEligibilityDetails entity1 = new UserEligibilityDetails();
		entity1.setUserId(01);
		entity1.setName("John Doe");
		entity1.setEmail("johdoe007@gmail.com");
		entity1.setGender("Male");
		entity1.setSsn(Long.valueOf("152465352458"));
		entity1.setMobileNumber(Long.valueOf("8874526431"));
		entity1.setPlanName("LIC");
		entity1.setPlanStatus("Closed");
		eligRepo.save(entity1);

		UserEligibilityDetails entity2 = new UserEligibilityDetails();
		entity2.setUserId(02);
		entity2.setName("Mariya");
		entity2.setEmail("itxmariya303@gamil.com");
		entity2.setGender("female");
		entity2.setSsn(Long.valueOf("154622897256"));
		entity2.setMobileNumber(Long.valueOf("9856874152"));
		entity2.setPlanName("Medico");
		entity2.setPlanStatus("Denied");
		eligRepo.save(entity2);

		UserEligibilityDetails entity3 = new UserEligibilityDetails();
		entity3.setUserId(03);
		entity3.setName("David");
		entity3.setEmail("davidanderson87@gmail.com");
		entity3.setGender("male");
		entity3.setSsn(Long.valueOf("254689724632"));
		entity3.setMobileNumber(Long.valueOf("8754265487"));
		entity3.setPlanName("Wealth");
		entity3.setPlanStatus("Approved");
		eligRepo.save(entity3);

	}
}
