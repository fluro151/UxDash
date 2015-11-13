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
    
    public SourceDTO find(String id) throws SQLException, NamingException {
        SourceDTO result = new SourceDTO();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "Select sourceName "
                    + "from sources "
                  );) {
            //Set parameters
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.setSourceName(rs.getString("sourceName"));
                
            }
        }

        return result;
    }
        
    public ArrayList<SourceDTO> listAll() throws SQLException, NamingException {
        ArrayList<SourceDTO> sources = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                    "Select sourceName "
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
    
        public SourceDTO listID(int id) throws SQLException, NamingException {
        SourceDTO result = new SourceDTO();   
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                    "select sourceName "
                    + "from sources "
                    
            );) {
            ps.setInt(1, id);
            /**
             * Looping over every row in the result set *
             */
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.setSourceName(rs.getString("sourceName"));
            }
        }

        return result;
    }
        
        public void add(SourceDTO source) throws NoSuchAlgorithmException, SQLException, NamingException {
            
            DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
            
            try (Connection conn = ds.getConnection(); PreparedStatement query = conn.prepareStatement(
                    "insert into SOURCES (sourceName)"
            
            );) {
              
            query.setString(1, source.getSourceName());
            
            query.execute();
                
            }
        }
    
}
