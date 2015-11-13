package controllers;

import dto.*;
import dao.*;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;
import javax.naming.NamingException;
/**
 *
 * @author Harry
 */
@Named
@RequestScoped
public class SourceController implements Serializable{
    private SourceDTO source = new SourceDTO();
    private SourceDTO temp = new SourceDTO();
        
    public SourceDTO getSourceName() {
        return source;
    }
    
    public SourceDTO getTemp() {
        return temp;
    }
    
    public void setTemp (SourceDTO temp) {
        this.temp = temp;
    }
    
    public void setSourceName(SourceDTO source) {
        this.source = source;
    }
    
    
    public ArrayList<SourceDTO> getAll() throws SQLException, NamingException {
        SourceDAO sourceDAO = new SourceDAO();
        ArrayList<SourceDTO> sources = new ArrayList<>();
        sources = sourceDAO.listAll();
            
        return sources;
    }
    
    public void loadSource() throws SQLException, NamingException {
        SourceDAO sourceDAO = new SourceDAO();
        source = sourceDAO.find(source.getSourceName());
    }
}