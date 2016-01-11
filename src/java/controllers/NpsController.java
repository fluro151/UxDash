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
import org.primefaces.model.chart.MeterGaugeChartModel;
/**
 *
 * @author Harry
 */
@Named
@RequestScoped
public class NpsController implements Serializable{
    private NpsDTO nps = new NpsDTO();
    private NpsDTO temp = new NpsDTO();
    private MeterGaugeChartModel model = new MeterGaugeChartModel();

    public void setModel(MeterGaugeChartModel model) {
        this.model = model;
    }
    
    public MeterGaugeChartModel getModel() throws SQLException, NamingException {
        NpsDAO npsDAO = new NpsDAO();
        
        this.model = npsDAO.getModel();
        return model;
    }
        
    private float npsWhole(){
        float pPromoters = (nps.getTaskPromoters()/(nps.getTaskDetractors() +nps.getTaskPassives()+nps.getTaskPromoters()))*100;
        float pDetractors = (nps.getTaskDetractors()/(nps.getTaskDetractors() +nps.getTaskPassives()+nps.getTaskPromoters()))*100;
        return (pPromoters - pDetractors);
    }
        
    public NpsDTO getNps() {
        return nps;
    }
    
    public NpsDTO getTemp() {
        return temp;
    }
    
    public void setTemp (NpsDTO temp) {
        this.temp = temp;
    }
    
    public void setNps(NpsDTO session) {
        this.nps = session;
    }
    
    
    public NpsDTO getAll() throws SQLException, NamingException {
        NpsDAO npsDAO = new NpsDAO();
        nps = npsDAO.loadNps();
        //float npsW = npsWhole();
        //nps.setNpsWhole(npsW);
        return nps;
    }
    
}