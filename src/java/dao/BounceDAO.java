package dao;

import dto.BounceDTO;
import static java.lang.Integer.parseInt;
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
import org.primefaces.model.chart.ChartSeries;


/**
 *
 * @author Harry
 */
public class BounceDAO {
    
    public BounceDTO find(String id) throws SQLException, NamingException {
        BounceDTO result = new BounceDTO();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "Select id, value, timeStamp, sourceName "
                    + "from bounces "
                    + "where id = ? "
            );) {
            //Set parameters
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.setID(rs.getString("id"));
                result.setValue(rs.getString("value"));
                result.setTimeStamp(rs.getTimestamp("timeStamp"));
                result.setSourceName(rs.getString("sourceName"));
            }
        }

        return result;
    }
        
    public ArrayList<BounceDTO> listAll() throws SQLException, NamingException {
        ArrayList<BounceDTO> bounces = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                    "Select id, value, timeStamp, sourceName "
                    + "from bounces "
                    
   
            );) {
            
            
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BounceDTO result = new BounceDTO();
                result.setID(rs.getString("id"));
                result.setValue(rs.getString("value"));
                result.setTimeStamp(rs.getTimestamp("timeStamp"));
                result.setSourceName(rs.getString("sourceName"));
                bounces.add(result);
            }
        }
        
        return bounces;
    }
    
    
    public ArrayList<BounceDTO> listRecords(String source) throws SQLException, NamingException {
        ArrayList<BounceDTO> bounces = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
        
                "select id, value, timeStamp, sourceName "
                + "from bounces "
                + "where sourceName = ? "
                
        );) {
            ps.setString(1, source);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BounceDTO result = new BounceDTO();
                result.setID(rs.getString("id"));
                result.setValue(rs.getString("value"));
                result.setTimeStamp(rs.getTimestamp("timeStamp"));
                result.setSourceName(rs.getString("sourceName"));
                bounces.add(result);
            }
        }
        return bounces;
    }
    
        public BounceDTO listID(int id) throws SQLException, NamingException {
        BounceDTO result = new BounceDTO();   
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                    "select id, value, timeStamp, sourceName  "
                    + "from bounce "
                    + "where id = ? "
            );) {
            ps.setInt(1, id);
            /**
             * Looping over every row in the result set *
             */
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.setID(rs.getString("id"));
                result.setValue(rs.getString("value"));
                result.setTimeStamp(rs.getTimestamp("timeStamp"));
                result.setSourceName(rs.getString("sourceName"));
            }
        }

        return result;
    }
        
        
         public BarChartModel getModel() throws SQLException, NamingException {
            BarChartModel model = new BarChartModel();
            ChartSeries bounces = new ChartSeries();
            ArrayList<BounceDTO> bDTO = new ArrayList<>();
            DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
            
            try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                    "select id, value, sourceName, timeStamp "
                    +"from bounces "
            );) {
                
                ResultSet rs = ps.executeQuery();
                String[] results = new String[4];
                BounceDTO result = new BounceDTO();
                while (rs.next()) {
                    results[0] = rs.getString("id");
                    results[1] = rs.getString("sourceName");
                    results[2] = rs.getString("value");
                    results[3] = rs.getString("timeStamp");
                    result.setID(rs.getString("id"));
                    result.setSourceName(rs.getString("sourceName"));
                    result.setValue(rs.getString("value"));
                    result.setTimeStamp(rs.getTimestamp("timeStamp"));
                
                bounces.set(result.getTimeStamp(), parseInt(result.getValue()));
                
               // sessions.set(results[1], parseInt(results[2]));
               // sessions.set(rs.getString("timeStamp"), parseInt(rs.getString("value")));
                
                }
                
            }
//            sessions.set("2004", 100);
//            sessions.set("2005", 200);
//            sessions.set("2006", 300);
//            sessions.set("2007", 400);
//            sessions.set("2008", 500);
            model.addSeries(bounces);
            model.setTitle("TEST TITLE");
            model.setLegendPosition("se");
            
            Axis xAxis = model.getAxis(AxisType.X);
            xAxis.setLabel("Bounces");
            
            Axis yAxis = model.getAxis(AxisType.Y);
            yAxis.setLabel("Bounce Count");
            yAxis.setMin(0);
            yAxis.setMax(500);
            return model;
        }
         
          public BarChartModel getSourceModel(String source) throws SQLException, NamingException {
            BarChartModel model = new BarChartModel();
            ChartSeries bounces = new ChartSeries();
            ArrayList<BounceDTO> bDTO = new ArrayList<>();
            DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
            
            try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                    "select id, value, sourceName, timeStamp "
                    +"from bounces "
                    +"where sourceName = ? "
            );) {
                ps.setString(1, source);
                ResultSet rs = ps.executeQuery();
                BounceDTO result = new BounceDTO();
                while (rs.next()) {
                    
                    result.setID(rs.getString("id"));
                    result.setSourceName(rs.getString("sourceName"));
                    result.setValue(rs.getString("value"));
                    result.setTimeStamp(rs.getTimestamp("timeStamp"));
                
                bounces.set(result.getTimeStamp(), parseInt(result.getValue()));
                
                }
                
            }
            model.addSeries(bounces);
            model.setTitle("Bounces by Source");
            model.setLegendPosition("se");
            
            Axis xAxis = model.getAxis(AxisType.X);
            xAxis.setLabel("Bounces");
            
            Axis yAxis = model.getAxis(AxisType.Y);
            yAxis.setLabel("Bounce Count");
            yAxis.setMin(0);
            yAxis.setMax(500);
            return model;
        }
    
}
