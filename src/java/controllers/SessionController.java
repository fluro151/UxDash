package controllers;

import csvConverter.*;
import dto.*;
import dao.*;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;
import javax.naming.NamingException;
import org.primefaces.model.chart.BarChartModel;
/**
 *
 * @author Harry
 */
@Named
@RequestScoped
public class SessionController implements Serializable{
    private SessionDTO session = new SessionDTO();
    private SessionDTO temp = new SessionDTO();
    private BarChartModel model = new BarChartModel();

    public void setModel(BarChartModel model) {
        this.model = model;
    }
    
    public BarChartModel getModel() throws SQLException, NamingException {
        SessionDAO sessionDAO = new SessionDAO();
        Main.main();
        this.model = sessionDAO.getModel();
        return model;
    }
    
    public BarChartModel getSourceModel(String source) throws SQLException, NamingException {
        SessionDAO sessionDAO = new SessionDAO();
        this.model = sessionDAO.getSourceModel(source);
        return model;
    }
    
    
        
    public SessionDTO getSession() {
        return session;
    }
    
    public SessionDTO getTemp() {
        return temp;
    }
    
    public void setTemp (SessionDTO temp) {
        this.temp = temp;
    }
    
    public void setSession(SessionDTO session) {
        this.session = session;
    }
    
    
    public ArrayList<SessionDTO> getAll() throws SQLException, NamingException {
        SessionDAO sessionDAO = new SessionDAO();
        Main.main();
        ArrayList<SessionDTO> sessions = new ArrayList<>();
        sessions = sessionDAO.listAll();
        
            
        return sessions;
    }
    
    public void loadSession() throws SQLException, NamingException {
        SessionDAO sessionDAO = new SessionDAO();
        session = sessionDAO.find(session.getID());
    }
    
    public ArrayList<SessionDTO> getRecords (String source) throws SQLException, NamingException {
        SessionDAO sessionDAO = new SessionDAO();
        ArrayList<SessionDTO> sessions = new ArrayList<>();
        sessions = sessionDAO.listRecords(source);
        
        return sessions;
    }
}