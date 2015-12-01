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
    private int sessionValue;
    private int bounceValue;
    private Timestamp sourceTimeStamp;
    
    @Size (min=1)
    @NotNull
    public String getSourceName(){
        return sourceName;
    }
    
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    } 
    
    public int getSessionValue(){
        return sessionValue;
    }
    public void setSessionValue(int sessionValue){
        this.sessionValue = sessionValue;
    }
    
    public Timestamp getSourceTimestamp(){
        return sourceTimeStamp;
    }
    
    public void setSourceTimestamp(Timestamp sourceTimeStamp){
        this.sourceTimeStamp = sourceTimeStamp;
    }
    
    public int getBounceValue(){
        return bounceValue;
    }
    
    public void setBounceValue(int bounceValue){
        this.bounceValue = bounceValue;
    }
}
