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
public class SessionController implements Serializable{
    private SessionDTO session = new SessionDTO();
    private SessionDTO temp = new SessionDTO();
        
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
        ArrayList<SessionDTO> sessions = new ArrayList<>();
        sessions = sessionDAO.listAll();
            
        return sessions;
    }
    
    public void loadSession() throws SQLException, NamingException {
        SessionDAO sessionDAO = new SessionDAO();
        session = sessionDAO.find(session.getID());
    }
}