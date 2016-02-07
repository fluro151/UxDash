/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import static analyticsAccess.HelloAnalytics.*;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.GaData;
import dto.GaDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
        xAxis.setTickAngle(-60);        
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
    
    public void insert() throws Exception{
        GaData data = getDataResults();
        String result = data.getRows().get(0).get(0);
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        System.out.println("Result" + result);
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "Insert into GOOGLEANALYTICS "
                + "(SESSIONS) values "
                + "(?) "
        );) {
            ps.setString(1, result);
            ps.executeUpdate();
        }
    }
    
    public void firstInsertGaData() throws Exception{
        GaData data = getDataResults();
        boolean rows;
        System.out.println("gathering data...");
            if(data.getTotalResults() > 0) {
                System.out.println("data gathered. Parsing...");
                for (List<String> rowValues : data.getRows()) {
                        System.out.println("Validating Records...");
                        rows = validateData(rowValues.get(0),rowValues.get(1),rowValues.get(2),rowValues.get(3),rowValues.get(4),rowValues.get(5),rowValues.get(6),rowValues.get(7),rowValues.get(8),rowValues.get(9));
                        if (rows == false) {
                            
                        
                        System.out.println("inserting...");
                        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");    
                        try(Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                                "Insert into GOOGLEANALYTICS "
                                +"(WEBSOURCE,BROWSER,OPERATINGSYSTEM,MOBILEDEVICEBRANDING,USERS,SESSIONS,BOUNCES,BOUNCERATE,SESSIONDURATION,ACCESSDATE) values "
                                +"(?,?,?,?,?,?,?,?,?,?) "
                        );) {
                            
                        
                            
                            ps.setString(1, rowValues.get(0));
                            ps.setString(2, rowValues.get(1));
                            ps.setString(3, rowValues.get(2));
                            ps.setString(4, rowValues.get(3));
                            ps.setDate(10, (Date) toDate(rowValues.get(4)));
                            ps.setString(5, rowValues.get(5));
                            ps.setString(6, rowValues.get(6));
                            ps.setString(7, rowValues.get(7));
                            ps.setString(8, rowValues.get(8));
                            ps.setString(9, rowValues.get(9));
                            ps.executeUpdate();
                        
                            
                        
                        
                    }
                        }
                    
                }
                
            }
    }

    private java.util.Date toDate(String get) throws ParseException {
        String date = get;
        date = date.substring(0, 4) + "-" + date.substring(4, date.length());
        date = date.substring(0, 7) + "-" + date.substring(7, date.length());
        //System.out.println(date);
        DateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date formattedDate = myFormat.parse(date);
        //System.out.println(formattedDate);
        java.sql.Date sqlDate = new java.sql.Date(formattedDate.getTime());
        return sqlDate;
       

    }

    private boolean validateData(String get0, String get1, String get2, String get3, String get4, String get5, String get6, String get7, String get8, String get9) throws NamingException, SQLException, ParseException {
        boolean Duplicate = false;
       // System.out.println(get0 + " " + get1+ " " + get2+ " " + get3+ " " + get4+ " " + get5+ " " + get6+ " " + get7+ " " + get8+ " " + get9);
        
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
                
                
                
                if (result.getWebSource().equals(get0)
                        && result.getBrowser().equals(get1) 
                        && result.getOperatingSystem().equals(get2) 
                        && result.getMobileDeviceBranding().equals(get3)
                        && result.getUsers() == Integer.parseInt(get5)
                        && result.getSessions() == Integer.parseInt(get6)
                        && result.getBounces() == Integer.parseInt(get7)
                        && result.getBounceRate() == Double.parseDouble(get8)
                        && result.getSessionDuration() == Double.parseDouble(get9)
                        && result.getAccessDate().equals(toDate(get4))
                        
                        ) {
                    
                    System.out.println("True");
                    Duplicate = true;
                    break;
                    
                }
                else {
                    System.out.println("False");
                    Duplicate = false;
             }
            }
        
        
        return Duplicate;
    }
    }
}
