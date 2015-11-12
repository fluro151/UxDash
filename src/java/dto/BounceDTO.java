package dto;

import java.io.Serializable;
import javax.validation.constraints.*;
import java.sql.Timestamp;
/**
 *
 * @author Harry
 */
public class BounceDTO implements Serializable {
    private String id;
    private String value;
    private  Timestamp timeStamp; 
    
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
    
    
    
    
       
    
}
