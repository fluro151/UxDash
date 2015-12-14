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
@SessionScoped
public class SourceController implements Serializable{
    private SourceDTO source = new SourceDTO();
    private String temp = "";
    
    
    public SourceDTO getSource() {
        return source;
    }

    public void setSource(SourceDTO source) {
        this.source = source;
    }
    
    public String getTemp() {
        return temp;
    }
    
    public void setTemp (String temp) throws SQLException, NamingException {
        SourceDAO sDAO = new SourceDAO();
        SourceDTO sDTO = sDAO.find(temp);
        this.temp = sDTO.getSourceName();
    }
    
    public void setSourceName(String source) {
        this.source.setSourceName(source);
    }
    
    
    public ArrayList<SourceDTO> getAll() throws SQLException, NamingException {
        SourceDAO sourceDAO = new SourceDAO();
        ArrayList<SourceDTO> sources = new ArrayList<>();
        sources = sourceDAO.listAll();
            
        return sources;
    }
    
    
    public void loadSource(String source) throws SQLException, NamingException {
        SourceDAO sourceDAO = new SourceDAO();
        this.source = sourceDAO.getSource(source);
        
    }
    
//    public ArrayList<SourceDTO> getSourceSessions(String source) throws SQLException, NamingException {
//        SourceDAO sourceDAO = new SourceDAO();
//        ArrayList<SourceDTO> sDTO = sourceDAO.getSourceSessions(source);
//        return sDTO;
//    }
    
//    public void updateSessions() throws SQLException, NamingException {
//        SourceDAO sourceDAO = new SourceDAO();
//        sourceDAO.updateSessions(source);
//        
//    }
}