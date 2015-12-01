package dao;

import ChartGen.ChartBean;
import dto.SessionDTO;
import static java.lang.Integer.parseInt;
import java.security.NoSuchAlgorithmException;
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
public class SessionDAO {
    
    public SessionDTO find(String id) throws SQLException, NamingException {
        SessionDTO result = new SessionDTO();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "Select id, value, timeStamp, sourceName "
                    + "from sessions "
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
        
    public ArrayList<SessionDTO> listAll() throws SQLException, NamingException {
        ArrayList<SessionDTO> sessions = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                    "Select id, value, timeStamp, sourceName "
                    + "from sessions "
                    
   
            );) {
            
            
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SessionDTO result = new SessionDTO();
                result.setID(rs.getString("id"));
                System.out.println("sessionid: " + rs.getString("id"));
                result.setValue(rs.getString("value"));
                result.setTimeStamp(rs.getTimestamp("timeStamp"));
                result.setSourceName(rs.getString("sourceName"));
                sessions.add(result);
            }
        }
        
        return sessions;
    }
    
    
        public ArrayList<SessionDTO> listRecords(String source) throws SQLException, NamingException {
            ArrayList<SessionDTO> sessions = new ArrayList<>();
            DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
            
            try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                    
                    "select id, value, timestamp, sourceName "
                    +"from sessions "
                    +"where sourcename = ? "
                    
                    
            );) {
                ps.setString(1, source);
                
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    SessionDTO result = new SessionDTO();
                    result.setID(rs.getString("id"));
                    result.setValue(rs.getString("value"));
                    result.setTimeStamp(rs.getTimestamp("timeStamp"));
                    result.setSourceName(rs.getString("sourceName"));
                    sessions.add(result);
                }
            }
            return sessions;
        }
    
        public SessionDTO listID(int id) throws SQLException, NamingException {
        SessionDTO result = new SessionDTO();   
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                    "select id, value, timeStamp, sourceName  "
                    + "from sessions "
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
        
        public void add(SessionDTO session) throws NoSuchAlgorithmException, SQLException, NamingException {
            
            DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
            
            try (Connection conn = ds.getConnection(); PreparedStatement query = conn.prepareStatement(
                    "insert into SESSIONS (id, value, timeStamp, sourceName )"
                    + "values (?, ?, ?, ? )"
            
            );) {
              
            query.setString(1, session.getID());
            query.setString(2, session.getValue());
            query.setTimestamp(3, session.getTimeStamp());
            query.setString(4, session.getSourceName());
            
            query.execute();
                
            }
        }
        
        public BarChartModel getModel() throws SQLException, NamingException {
            BarChartModel model = new BarChartModel();
            ChartSeries sessions = new ChartSeries();
            SessionDTO sDTO = new SessionDTO();
            DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
            
            try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                    "select id, value, sourceName, timeStamp "
                    +"from sessions "
            );) {
                
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                sessions.set(rs.getString("timeStamp"), parseInt(rs.getString("value")));
                
                }
                
            }
//            sessions.set("2004", 100);
//            sessions.set("2005", 200);
//            sessions.set("2006", 300);
//            sessions.set("2007", 400);
//            sessions.set("2008", 500);
            model.addSeries(sessions);
            model.setTitle("TEST TITLE");
            model.setLegendPosition("se");
            
            Axis xAxis = model.getAxis(AxisType.X);
            xAxis.setLabel("Sessions");
            
            Axis yAxis = model.getAxis(AxisType.Y);
            yAxis.setLabel("Session Count");
            yAxis.setMin(0);
            yAxis.setMax(500);
            return model;
        }
    
}
