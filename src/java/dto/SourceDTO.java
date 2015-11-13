package dto;

import java.io.Serializable;
import javax.validation.constraints.*;
/**
 *
 * @author Harry
 */
public class SourceDTO implements Serializable {
    private String sourceName;
    
    
    @Size (min=1)
    @NotNull
    public String getSourceName(){
        return sourceName;
    }
    
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    } 
       
    
}
