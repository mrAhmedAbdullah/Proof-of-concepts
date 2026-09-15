package in.tajdar.plans_api.Repository;

import in.tajdar.plans_api.model.PlanCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanCategoryRepo  extends JpaRepository<PlanCategory, Integer>   {

}
