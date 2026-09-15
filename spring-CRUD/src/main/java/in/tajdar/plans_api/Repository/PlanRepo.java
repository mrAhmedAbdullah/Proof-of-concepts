package in.tajdar.plans_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.tajdar.plans_api.model.Plan;

public interface PlanRepo extends JpaRepository<Plan,Integer>{
    

}
