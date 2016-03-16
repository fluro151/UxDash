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
    public BarChartModel getBarModel() throws SQLException,  NamingException {
        BarChartModel model = new BarChartModel();
        ChartSeries bounces = new ChartSeries();
        ArrayList<GaDTO> analytics = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement (
                "select monthInt,yearInt, sum(bounces) Total "
                +"from googleAnalytics "
                +"group by MonthInt,Yearint "
                +"order by Yearint, MonthInt ASC "
        );) {
            
            ResultSet rs = ps.executeQuery();
            GaDTO result = new GaDTO();
            while (rs.next()) {
                result.setBounces(rs.getInt("Total"));
//                result.setAccessDate(rs.getDate("accessDate"));
                result.setMonth(rs.getInt("monthint"));
                result.setYear(rs.getInt("yearInt"));
                String temp = Integer.toString(rs.getInt("yearInt"));
                
            bounces.set(rs.getString("monthint") + " " + temp, result.getBounces());
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
                "select monthint,yearInt,  sum(sessions) Total "
                +"from googleAnalytics "
                +"group by MonthInt,Yearint "
                +"order by Yearint, MonthInt ASC "
        );) {
            
            ResultSet rs = ps.executeQuery();
            GaDTO result = new GaDTO();
            while (rs.next()) {
                result.setSessions(rs.getInt("Total"));
                //result.setAccessDate(rs.getDate("accessDate"));
                result.setMonth(rs.getInt("monthint"));
                result.setYear(rs.getInt("yearInt"));
                String temp = Integer.toString(rs.getInt("yearInt"));
            sessions.set(rs.getInt("monthint") + " " + temp, result.getSessions());
            }
    }
        model.addSeries(sessions);
        
        model.setTitle("Sessions/Date");
        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Date");
        
        xAxis.setTickAngle(50);
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Session Count");
        return model;
    }
    
    public LineChartModel getDurationModel() throws SQLException, NamingException {
        LineChartModel model = new LineChartModel();
        ChartSeries duration = new ChartSeries();
        ArrayList<GaDTO> gaDTO = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "select monthint,yearint, AVG(sessionduration) total "
                +"from googleAnalytics "
                +"group by MonthInt, YearInt "
                +"order by YearInt, MonthInt ASC "
        );) {
            ResultSet rs = ps.executeQuery();
            GaDTO result = new GaDTO();
            while(rs.next()) {
                result.setSessionDuration(rs.getInt("total"));
                result.setMonth(rs.getInt("monthInt"));
                result.setYear(rs.getInt("yearInt"));
                String temp = Integer.toString(rs.getInt("yearInt"));
            duration.set(rs.getInt("monthInt") + " " + temp,result.getSessionDuration());
            }
        }
        model.addSeries(duration);
        model.setTitle("Avg Duration/Time");
        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Date");
        xAxis.setTickAngle(50);
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Avg Duration (seconds)");
        return model;
    }
    
        public LineChartModel getNewSessionsModel() throws SQLException, NamingException {
        LineChartModel model = new LineChartModel();
        ChartSeries duration = new ChartSeries();
        ArrayList<GaDTO> gaDTO = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "select monthint,yearint, sum(percentNewSessions) Total "
                +"from googleAnalytics "
                +"group by MonthInt, YearInt "
                +"order by YearInt, MonthInt ASC "
        );) {
            ResultSet rs = ps.executeQuery();
            GaDTO result = new GaDTO();
            while(rs.next()) {
                result.setPercentNewSessions(rs.getDouble("Total"));
                result.setMonth(rs.getInt("monthInt"));
                result.setYear(rs.getInt("yearInt"));
                String temp = Integer.toString(rs.getInt("yearInt"));
            duration.set(rs.getInt("monthInt") + " " + temp,result.getPercentNewSessions());
            }
        }
        model.addSeries(duration);
        model.setTitle("% New Sessions/Time");
        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Date");
        xAxis.setTickAngle(50);
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("% New Sessions");
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
                                +"(WEBSOURCE,BROWSER,OPERATINGSYSTEM,MOBILEDEVICEBRANDING,USERS,SESSIONS,BOUNCES,BOUNCERATE,SESSIONDURATION,ACCESSDATE,MONTHINT,YEARINT) values "
                                +"(?,?,?,?,?,?,?,?,?,?,?,?) "
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
                            ps.setInt(11, setMonth((Date) toDate(rowValues.get(4))));
                            ps.setInt(12, setYear((Date) toDate(rowValues.get(4))));
                            ps.executeUpdate();
                        
                            
                        
                        
                    }
                        }
                    
                }
                
            }
    }
    
    public void insertDateGaData() throws Exception{
        GaData data = getDateResults();
        boolean rows;
        System.out.println("gathering date data...");
            if(data.getTotalResults() > 0) {
                System.out.println("data gathered. Parsing...");
                for (List<String> rowValues : data.getRows()) {
                        System.out.println("Validating date Records...");
                        rows = validateDateData(rowValues.get(0));
                        if (rows == false) {
                            
                        
                        System.out.println("New Row Queryed - inserting...");
                        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");    
                        try(Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                                "Insert into GOOGLEANALYTICS "
                                +"(USERS,SESSIONS,BOUNCES,BOUNCERATE,SESSIONDURATION,ACCESSDATE,MONTHINT,YEARINT,PERCENTNEWSESSIONS,TIMEONPAGE) values "
                                +"(?,?,?,?,?,?,?,?,?,?) "
                        );) {
                            
                        
                            
                            ps.setString(1, rowValues.get(1));
                            ps.setString(2, rowValues.get(2));
                            ps.setString(3, rowValues.get(5));
                            ps.setString(4, rowValues.get(6));
                            ps.setString(5, rowValues.get(3));
                            ps.setDate(6, (Date) toDate(rowValues.get(0)));
                            ps.setInt(7, setMonth((Date) toDate(rowValues.get(0))));
                            ps.setInt(8, setYear((Date) toDate(rowValues.get(0))));
                            ps.setDouble(9, Double.parseDouble(rowValues.get(4)));
                            ps.setDouble(10, Double.parseDouble(rowValues.get(7)));
                            ps.executeUpdate();
                        
                            
                        
                        
                    }
                        }
                    
                }
                
            }
            System.out.println("Analytics Query Completed");
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

    private boolean validateDateData(String get0) throws NamingException, SQLException, ParseException {
        boolean Duplicate = false;
       // System.out.println(get0 + " " + get1+ " " + get2+ " " + get3+ " " + get4+ " " + get5+ " " + get6+ " " + get7+ " " + get8+ " " + get9);
        
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement (
                "Select accessDate "
                +"from googleAnalytics "
        );) {
          ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GaDTO result = new GaDTO();
                result.setAccessDate(rs.getDate("accessDate"));
                
                
                
                if (result.getAccessDate().equals(toDate(get0))) {
                    
                    
                    Duplicate = true;
                    break;
                    
                }
                else {
                    
                    Duplicate = false;
             }
            }
        
        
        return Duplicate;
    }
    }
    
    private int setMonth(Date date) {
        int value;
        String month = null;
        value = date.getMonth() + 1;
        if (value == 1) {
            month = "Jan";
        }
        else if (value == 2) {
            month = "Feb";
        }
        else if (value == 3) {
            month = "Mar";
        }
        else if (value == 4) {
            month = "Apr";
        }
        else if (value == 5) {
            month = "May";
        }
        else if (value == 6) {
            month = "Jun";
        }
        else if (value == 7) {
            month = "Jul";
        }
        else if (value == 8) {
            month = "Aug";
        }
        else if (value == 9) {
            month = "Sep";
        }
        else if (value == 10) {
            month = "Oct";
        }
        else if (value == 11) {
            month = "Nov";
        }
        else if (value == 12) {
            month = "Dec";
        }
        return value;
    }

    private int setYear(Date date) {
        int year = 0;
        String longDate;
        String temp;
        longDate = date.toString();
 
        temp = longDate.substring(0, 4);
        year = Integer.parseInt(temp);
        return year;
    }
}
