package in.tajdar.plans_api.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "PLAN_CATEGORY")//table and column annotation are optional, otherwise className--tableName,  fieldName--columnName will be considered by default 
public class PlanCategory {
    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Integer categoryId;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Column(name = "ACTIVE_SW")
    private String activeSw;

    @Column(name = "CREATED_DATE", updatable = false)//updatable=false means that this column will not be updated once it is set, it will only be set when the record is created
    @CreationTimestamp//GENERATING DATES WITH THE HELP OF HIBERNATE, NO NEED TO SET THE DATE MANUALLY
    private LocalDate createdDate;

    @Column(name = "UPDATED_DATE", insertable = false)//insertable=false means that this column will not be updated once it is set, it will only be set when the record is created
    @UpdateTimestamp//GENERATING DATES WITH THE HELP OF HIBERNATE, NO NEED TO SET THE DATE MANUALLY
    private LocalDate updatedDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    
}

