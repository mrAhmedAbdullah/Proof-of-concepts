package in.tajdar.plans_api.service;

import java.util.List;
import java.util.Map;

import in.tajdar.plans_api.model.Plan;

public interface PlanService {
    //1
    public Map<Integer, String> getPlanCategories();
// 2
    public List <Plan> getAllPlans();
// 3
    public boolean savePlan(Plan plan);
//  4
    public boolean deletePlan(Integer planId);
// 5
    public boolean updatePlan(Plan plan);
// 6
    public boolean planStatusChange(Integer planId, String activeSw);
// 7
    public Plan getPlanById(Integer planId);

    
} 