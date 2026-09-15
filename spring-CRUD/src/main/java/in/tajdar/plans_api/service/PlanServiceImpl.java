package in.tajdar.plans_api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import in.tajdar.plans_api.Repository.PlanCategoryRepo;
import in.tajdar.plans_api.Repository.PlanRepo;
import in.tajdar.plans_api.model.Plan;
import in.tajdar.plans_api.model.PlanCategory;

public class PlanServiceImpl implements PlanService {

	@Autowired
	private PlanCategoryRepo categoryRepo;
	@Autowired
	private PlanRepo planRepo;
	
    @Override
    public Map<Integer, String> getPlanCategories() {
    	Map<Integer, String> map = new HashMap<>();
    	List<PlanCategory> all = categoryRepo.findAll();
    	all.forEach(ctg -> map.put(ctg.getCategoryId(), ctg.getCategoryName()));
        return map;
    }

    @Override
    public List<Plan> getAllPlans() {
    	List<Plan> all = planRepo.findAll();
    	
        return all;
    }

    @Override
    public boolean savePlan(Plan plan) {
    	Plan save = planRepo.save(plan);
    	
        return save.getPlanId() != null && save != null;
    }

    @Override
    public boolean deletePlan(Integer planId) {
    	Plan byId = planRepo.getById(planId);
    	boolean status = false;
    	try {
    		planRepo.delete(null);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			return status;
		}
        return status;
    }

    @Override
    public boolean updatePlan(Plan plan) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean planStatusChange(Integer planId, String activeSw) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Plan getPlanById(Integer planId) {
        // TODO Auto-generated method stub
        return null;
    }

}
