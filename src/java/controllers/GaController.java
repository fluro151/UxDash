/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;
/**
 *
 * @author Harrison
 */
@Named
@RequestScoped
public class GaController implements Serializable {
    private GaDTO analytics = new GaDTO();
    private BarChartModel barModel = new BarChartModel();
    private LineChartModel lineModel = new LineChartModel();
    private PieChartModel deviceModel = new PieChartModel();

    public PieChartModel getDeviceModel() throws SQLException, NamingException {
        GaDAO analyticsDAO = new GaDAO();
        this.deviceModel = analyticsDAO.getDeviceModel();
        return deviceModel;
    }

    public void setDeviceModel(PieChartModel deviceModel) {
        this.deviceModel = deviceModel;
    }

    
    
    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }
    public BarChartModel getBarModel() throws SQLException, NamingException {
        GaDAO analyticsDAO = new GaDAO();
        this.barModel = analyticsDAO.getBarModel();
        return barModel;
    }
    
    public void setLineModel(LineChartModel lineModel){
        this.lineModel = lineModel;
    }
    
    public LineChartModel getLineModel() throws SQLException, NamingException {
        GaDAO analyticsDAO = new GaDAO();
        this.lineModel = analyticsDAO.getLineModel();
        return lineModel;
    }
    
    public GaDTO getGaDTO() {
        return analytics;
    }
    
    public void setGaDTO(GaDTO analytics) {
        this.analytics = analytics;
    }
    
    public ArrayList<GaDTO> getAll() throws SQLException, NamingException {
        GaDAO analyticsDAO = new GaDAO();
        ArrayList<GaDTO> analytics = new ArrayList<>();
        analytics = analyticsDAO.listAll();
        return analytics;
    }
    
    public ArrayList<GaDTO> getRecords (String source) throws SQLException, NamingException {
        GaDAO analyticsDAO = new GaDAO();
        ArrayList<GaDTO> analytics = new ArrayList<>();
        analytics = analyticsDAO.listRecords(source);
        return analytics;
    }
}
