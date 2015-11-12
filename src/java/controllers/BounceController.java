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
public class BounceController implements Serializable{
    private BounceDTO bounce = new BounceDTO();
    private BounceDTO temp = new BounceDTO();
        
    public BounceDTO getSession() {
        return bounce;
    }
    
    public BounceDTO getTemp() {
        return temp;
    }
    
    public void setTemp (BounceDTO temp) {
        this.temp = temp;
    }
    
    public void setSession(BounceDTO bounce) {
        this.bounce = bounce;
    }
    
    
    public ArrayList<BounceDTO> getAll() throws SQLException, NamingException {
        BounceDAO bounceDAO = new BounceDAO();
        ArrayList<BounceDTO> bounces = new ArrayList<>();
        bounces = bounceDAO.listAll();
            
        return bounces;
    }
    
    public void loadSession() throws SQLException, NamingException {
        BounceDAO bounceDAO = new BounceDAO();
        bounce = bounceDAO.find(bounce.getID());
    }
}