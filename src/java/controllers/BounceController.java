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
import org.primefaces.model.chart.BarChartModel;
/**
 *
 * @author Harry
 */
@Named
@RequestScoped
public class BounceController implements Serializable{
    private BounceDTO bounce = new BounceDTO();
    private BounceDTO temp = new BounceDTO();
    private BarChartModel model = new BarChartModel();

    public BarChartModel getModel() throws SQLException, NamingException {
        BounceDAO BounceDAO = new BounceDAO();
        this.model = BounceDAO.getModel();
        return model;
    }
    

    public void setModel(BarChartModel model) {
        this.model = model;
    }
    
     public BarChartModel getSourceModel(String source) throws SQLException, NamingException {
        BounceDAO BounceDAO = new BounceDAO();
        this.model = BounceDAO.getSourceModel(source);
        return model;
    }
        
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
    
    public ArrayList<BounceDTO> getRecords(String source) throws SQLException, NamingException {
        BounceDAO bounceDAO = new BounceDAO();
        ArrayList<BounceDTO> bounces = new ArrayList<>();
        bounces = bounceDAO.listRecords(source);
        
        return bounces;
    }
}