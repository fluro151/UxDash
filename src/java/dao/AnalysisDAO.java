/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.AnalysisDTO;
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
 * @author Harrison
 */
public class AnalysisDAO {

    public ArrayList<AnalysisDTO> listAll() throws NamingException, SQLException {
        ArrayList<AnalysisDTO> analysis = new ArrayList<>();
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement (
                "select analysis, date "
                +"from adminAnalysis "
                
        );) {
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                AnalysisDTO result = new AnalysisDTO();
                result.setAnalysis(rs.getString("analysis"));
                result.setDate(rs.getDate("date"));
                analysis.add(result);
            }
        }
        return analysis;
        
    }

    public void insertNote(String note) throws NamingException, SQLException {
        
        DataSource ds = (DataSource) InitialContext.doLookup("jdbc/uxdash");
        
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "Insert into adminAnalysis "
                +"(Analysis) values "
                +"(?) "
        );) {
            ps.setString(1, note);
            ps.executeUpdate();
        }
    }   
    
}
