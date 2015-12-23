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
import org.primefaces.model.UploadedFile;
import java.io.*;
import java.net.*;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Harrison
 */
public class Main {
   
    public static void main(String dirPath, String fileName){
    try{
    CSVLoader loader = new CSVLoader(getCon());
    
    System.out.println(dirPath + fileName);
    loader.loadCSV(dirPath + fileName, "SESSIONS", true);
    
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
