/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.GaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Harrison
 */
public class GaDAO {
    
    public PieChartModel getDeviceModel() throws SQLException,  NamingException {
        PieChartModel model = new PieChartModel();
        ArrayList<GaDTO> analytics = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement (
                "select mobileDeviceBranding, count(mobileDeviceBranding) Total "
                +"from googleAnalytics "
                +"group by mobileDeviceBranding "
                +"order by mobileDeviceBranding "
        );) {
            
            ResultSet rs = ps.executeQuery();
            GaDTO result = new GaDTO();
            while (rs.next()) {
                int Count = rs.getInt("Total");
                result.setMobileDeviceBranding(rs.getString("mobileDeviceBranding"));
                
            model.set(result.getMobileDeviceBranding(), Count);
            }
            
        }
        
        model.setTitle("Device Usage");
        model.setLegendPosition("e");
        model.setDataFormat("value");
        model.setShowDataLabels(true);
        
        return model;
        
    }
    
    //Model of Bounces over Time
//    public BarChartModel getDeviceModel() throws SQLException,  NamingException {
//        BarChartModel model = new BarChartModel();
//        ChartSeries bounces = new ChartSeries();
//        ArrayList<GaDTO> analytics = new ArrayList<>();
//        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
//        
//        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement (
//                "select mobileDeviceBranding, count(mobileDeviceBranding) Total "
//                +"from googleAnalytics "
//                +"group by mobileDeviceBranding "
//                +"order by mobileDeviceBranding "
//        );) {
//            
//            ResultSet rs = ps.executeQuery();
//            GaDTO result = new GaDTO();
//            while (rs.next()) {
//                int Count = rs.getInt("Total");
//                result.setMobileDeviceBranding(rs.getString("mobileDeviceBranding"));
//                
//            bounces.set(result.getMobileDeviceBranding(), Count);
//            }
//            
//        }
//        model.addSeries(bounces);
//        model.setTitle("Device Usage");
//        Axis xAxis = model.getAxis(AxisType.X);
//        xAxis.setLabel("Devices");
//        xAxis.setTickAngle(-50);
//        Axis yAxis = model.getAxis(AxisType.Y);
//        yAxis.setLabel("Count");
//        
//        return model;
//        
//    }
    //Model of Bounces over Time
    public BarChartModel getBarModel() throws SQLException,  NamingException {
        BarChartModel model = new BarChartModel();
        ChartSeries bounces = new ChartSeries();
        ArrayList<GaDTO> analytics = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement (
                "select accessDate, sum(bounces) Total "
                +"from googleAnalytics "
                +"group by accessDate "
                +"order by accessDate "
        );) {
            
            ResultSet rs = ps.executeQuery();
            GaDTO result = new GaDTO();
            while (rs.next()) {
                result.setBounces(rs.getInt("Total"));
                result.setAccessDate(rs.getDate("accessDate"));
                
            bounces.set(result.getAccessDate(), result.getBounces());
            }
            
        }
        model.addSeries(bounces);
        model.setAnimate(true);
        model.setTitle("Bounces/Date");
        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Date");
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Bounce Count");
        return model;
        
    }
    
    //Model of Sessions over Time
    public LineChartModel getLineModel() throws SQLException,  NamingException {
        LineChartModel model = new LineChartModel();
        ChartSeries sessions = new ChartSeries();
        ArrayList<GaDTO> gaDTO = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "select accessDate, sum(sessions) Total "
                +"from googleAnalytics "
                +"group by accessDate "
                +"order by accessDate "
        );) {
            
            ResultSet rs = ps.executeQuery();
            GaDTO result = new GaDTO();
            while (rs.next()) {
                result.setSessions(rs.getInt("Total"));
                result.setAccessDate(rs.getDate("accessDate"));
            sessions.set(rs.getDate("accessDate"), result.getSessions());
            }
    }
        model.addSeries(sessions);
        
        model.setTitle("Sessions/Date");
        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Date");
        
        xAxis.setTickAngle(-50);
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Session Count");
        return model;
    }

    
    public ArrayList<GaDTO> listRecords(String source) throws SQLException, NamingException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ArrayList<GaDTO> listAll() throws SQLException, NamingException{
        ArrayList<GaDTO> analytics = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement (
                "select webSource, browser, operatingSystem,  mobileDeviceBranding, users, sessions, bounces, bounceRate, sessionDuration, accessDate, dataSource "
                +"from googleAnalytics "
        );) {
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GaDTO result = new GaDTO();
                result.setWebSource(rs.getString("webSource"));
                result.setBrowser(rs.getString("browser"));
                result.setOperatingSystem(rs.getString("operatingSystem"));
                result.setMobileDeviceBranding(rs.getString("mobileDeviceBranding"));
                result.setUsers(rs.getInt("users"));
                result.setSessions(rs.getInt("sessions"));
                result.setBounces(rs.getInt("bounces"));
                result.setBounceRate(rs.getInt("bounceRate"));
                result.setSessionDuration(rs.getInt("sessionDuration"));
                result.setAccessDate(rs.getDate("accessDate"));
                result.setDataSource(rs.getString("dataSource"));
                analytics.add(result);
            }
        }
        return analytics;
    }    
}
