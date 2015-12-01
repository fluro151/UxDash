package dao;

import dto.BounceDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


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
                System.out.println("bounceid: " + rs.getString("id"));
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
    
}
