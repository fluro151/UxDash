package dao;

import dto.SourceDTO;
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
public class SourceDAO {
    
    public SourceDTO find(String source) throws SQLException, NamingException {
        SourceDTO result = new SourceDTO();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                    "Select bounces.ID, bounces.value, sessions.value, bounces.timestamp, bounces.sourceName, sessions.sourceName "
                    + "from bounces join sessions "
                    + "on bounces.id = sessions.id "
                    + "where sessions.sourceName = ? "
                    + "or bounces.sourceName = ? "
                  );) {
            //Set parameters
            ps.setString(1, source);
            System.out.println(source);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.setSourceName(rs.getString("sourceName"));
//                result.setSessionValue(rs.getString("value"));
//                result.setBounceValue(rs.getString("value"));
                result.setSourceTimestamp(rs.getTimestamp("timeStamp"));
                
            }
        }

        return result;
    }
        
    public ArrayList<SourceDTO> listAll() throws SQLException, NamingException {
        ArrayList<SourceDTO> sources = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                    "Select * "
                    + "from sources "
            );) {
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SourceDTO result = new SourceDTO();
                result.setSourceName(rs.getString("sourceName"));
                sources.add(result);
            }
        }   
        
        return sources;
    }
    
        public SourceDTO getSource(String source) throws NamingException, SQLException {
            //ArrayList<SourceDTO> sourceArray = new ArrayList<>();
            SourceDTO result = new SourceDTO();
            SourceDTO sources  = new SourceDTO();
            
            DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
            
            try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(

                    "Select sourceName "
                    + "from sources "
                    + "where sourceName = ? "
            );) {
                ps.setString(1, source);
                System.out.println(source);
                
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    result.setSourceName(rs.getString("sourceName"));
                    
                }
            }
        return result;
        }
        
        public ArrayList<SourceDTO> getSourceSessions(String source) throws NamingException, SQLException {
            ArrayList<SourceDTO> sourceArray = new ArrayList<>();
            SourceDTO sources = new SourceDTO();
            
            DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
            
            try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                    "select sources.sessionValue "
                    + "from sources "
//                    + "union "
//                    + "select sum(cast(sessions.value as int)) " 
//                    + "from sessions "
//                    + "where sessions.sourceName = ? "
            );) {
                
                //ps.setString(1, source);
                
            
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    SourceDTO result = new SourceDTO();
                    result.setSessionValue(rs.getInt("sessionValue"));
                    
                    sourceArray.add(result);
                    
                }
            }
            return sourceArray;
        }
}
//        public void add(SourceDTO source) throws NoSuchAlgorithmException, SQLException, NamingException {
//            
//            DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
//            
//            try (Connection conn = ds.getConnection(); PreparedStatement query = conn.prepareStatement(
//                    "insert into SOURCES (sourceName)"
//            
//            );) {
//              
//            query.setString(1, source.getSourceName());
//            
//            query.execute();
//                
//            }
//        }
    

