/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvConverter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import oracle.jdbc.driver.OracleDriver;



/**
 *
 * @author Harrison
 */
public class Main {
    
    public static void main() {
    try{
    CSVLoader loader = new CSVLoader(getCon());
    
    loader.loadCSV("C:/Users/Harrison/Documents/NetBeansProjects/uxdash/file.csv", "SESSIONS", true);
    
    }   catch(Exception e) {
    e.printStackTrace();
    }
    
}
    
    private static Connection getCon() throws SQLException{
        Connection connection = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/uxdash", "uxd" , "uxd" );
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    }
