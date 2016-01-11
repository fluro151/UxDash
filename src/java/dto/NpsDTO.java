package dto;

import java.io.Serializable;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.sql.Date;
import org.primefaces.model.chart.BarChartModel;
/**
 *
 * @author Harry
 */
public class NpsDTO implements Serializable {
    private String ID;
    private String taskTitle;
    private String taskDescription;
    private int taskPromoters;
    private int taskDetractors;
    private int taskPassives;
    private int npsWhole;
    private Date taskDate;
    private BarChartModel model;

  
    

    public int getNpsWhole() {
        return npsWhole;
    }

    public void setNpsWhole(int NpsWhole) {
        this.npsWhole = NpsWhole;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTaskPromoters() {
        return taskPromoters;
    }

    public void setTaskPromoters(int taskPromoters) {
        this.taskPromoters = taskPromoters;
    }

    public int getTaskDetractors() {
        return taskDetractors;
    }

    public void setTaskDetractors(int taskDetractors) {
        this.taskDetractors = taskDetractors;
    }

    public int getTaskPassives() {
        return taskPassives;
    }

    public void setTaskPassives(int taskPassives) {
        this.taskPassives = taskPassives;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public BarChartModel getModel() {
        return model;
    }

    public void setModel(BarChartModel model) {
        this.model = model;
    }
    
    @Size (min=1)
    @NotNull
    public String getID(){
        return ID;
    }
    
    public void setID(String id) {
        this.ID = id;
    }
    
    
    
       
    
}
