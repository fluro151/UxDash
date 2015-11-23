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
        
    public SourceDTO getSourceName() {
        return source;
    }
    
    public String getTemp() {
        return temp;
    }
    
    public void setTemp (String temp) throws SQLException, NamingException {
        SourceDAO sDAO = new SourceDAO();
        SourceDTO sDTO = sDAO.find(temp);
        this.temp = sDTO.getSourceName();
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
    
    public void loadSource(String source) throws SQLException, NamingException {
        SourceDAO sourceDao = new SourceDAO();
        this.source = sourceDao.find(source);
    }
    
    public ArrayList<SourceDTO> getSelectedSource(String source) throws SQLException, NamingException {
        SourceDAO sourceDAO = new SourceDAO ();
        ArrayList<SourceDTO> sDTO = sourceDAO.getSource(source);
        return sDTO;
    }
}