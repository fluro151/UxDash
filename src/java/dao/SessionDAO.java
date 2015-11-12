package dao;

import dto.SessionDTO;
import java.security.NoSuchAlgorithmException;
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
public class SessionDAO {
    
    public SessionDTO find(String id) throws SQLException, NamingException {
        SessionDTO result = new SessionDTO();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "Select id, value, timeStamp "
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
            }
        }

        return result;
    }
        
    public ArrayList<SessionDTO> listAll() throws SQLException, NamingException {
        ArrayList<SessionDTO> sessions = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                    "Select id, value, timeStamp "
                    + "from sessions "
                    
   
            );) {
            
            
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SessionDTO result = new SessionDTO();
                result.setID(rs.getString("id"));
                System.out.println("sessionid: " + rs.getString("id"));
                result.setValue(rs.getString("value"));
                result.setTimeStamp(rs.getTimestamp("timeStamp"));
                sessions.add(result);
            }
        }
        
        return sessions;
    }
    
        public SessionDTO listID(int id) throws SQLException, NamingException {
        SessionDTO result = new SessionDTO();   
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                    "select id, value, timeStamp  "
                    + "from session "
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
            }
        }

        return result;
    }
        
        public void add(SessionDTO session) throws NoSuchAlgorithmException, SQLException, NamingException {
            
            DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
            
            try (Connection conn = ds.getConnection(); PreparedStatement query = conn.prepareStatement(
                    "insert into SESSIONS (id, sourceValue, SourceTimeStamp)"
                    + "values (?, ?, ?)"
            
            );) {
              
            query.setString(1, session.getID());
            query.setString(2, session.getValue());
            query.setTimestamp(3, session.getTimeStamp());
            
            query.execute();
                
            }
        }
    
}
