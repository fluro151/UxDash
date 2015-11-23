package dto;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.validation.constraints.*;
/**
 *
 * @author Harry
 */
public class SourceDTO implements Serializable {
    private String sourceName;
    private String sessions;
    private String bounces;
    private Timestamp timeStamp;
    
    @Size (min=1)
    @NotNull
    public String getSourceName(){
        return sourceName;
    }
    
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    } 
    
    public String getSessions(){
        return sessions;
    }
    public void setSessions(String sessions){
        this.sessions = sessions;
    }
    
    public Timestamp getTimeStamp(){
        return timeStamp;
    }
    
    public void setTimeStamp(Timestamp timeStamp){
        this.timeStamp = timeStamp;
    }
    
    public String getBounces(){
        return bounces;
    }
    
    public void setBounces(String bounces){
        this.bounces = bounces;
    }
}
