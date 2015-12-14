package dto;

import java.io.Serializable;
import java.sql.Date;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import org.primefaces.model.chart.BarChartModel;
/**
 *
 * @author Harry
 */
public class BounceDTO implements Serializable {
    private String id;
    private String value;
    private  Timestamp timeStamp; 
    private String sourceName;
    private BarChartModel model;
    private Date bounceDate;

    public Date getDate() {
        return bounceDate;
    }

    public void setDate(Date bounceDate) {
        this.bounceDate = bounceDate;
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
        return id;
    }
    
    public void setID(String id) {
        this.id = id;
    }
    
    public String getValue(){
        return value;
    }
    
    public void setValue(String value){
        this.value = value;
    }
    
    public Timestamp getTimeStamp(){
        return timeStamp;
    }
    
    public void setTimeStamp(Timestamp timeStamp){
        this.timeStamp = timeStamp;
    }
    
    public String getSourceName(){
        return sourceName;
    }
    
    public void setSourceName(String sourceName){
        this.sourceName = sourceName;
    }
    
       
    
}
