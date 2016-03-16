/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyticsAccess;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;
import com.google.api.services.analytics.model.Accounts;
import com.google.api.services.analytics.model.GaData;
import com.google.api.services.analytics.model.Profiles;
import com.google.api.services.analytics.model.Webproperties;
import com.google.api.services.analytics.model.GaData.ColumnHeaders;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

import javax.naming.InitialContext;
import javax.sql.DataSource;


/**
 * A simple example of how to access the Google Analytics API using a service
 * account.
 */
public class HelloAnalytics {


  private static final String APPLICATION_NAME = "Hello Analytics";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final String KEY_FILE_LOCATION = "C:\\Users\\Harrison\\Documents\\NetBeansProjects\\uxdash\\src\\java\\analyticsAccess\\UxDashboard-cbf9b42f30e2.p12";
  private static final String SERVICE_ACCOUNT_EMAIL = "uxdash-56@uxdashboard-1189.iam.gserviceaccount.com";
  public static void main(String[] args) {
    try {
      Analytics analytics = initializeAnalytics();

      String profile = getFirstProfileId(analytics);
      System.out.println("First Profile Id: "+ profile);
     // storeData(getResults(analytics, profile));
      printDataTable(getResults(analytics,profile));
     //printResults(getResults(analytics, profile));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
    
  public static Analytics getAnalytics() throws Exception{
          Analytics analytics = initializeAnalytics();
      return analytics;
  }
  
  public static String getProfile() throws IOException, Exception {
      String profile = getFirstProfileId(getAnalytics());
      return profile;
  }
  public static GaData getDataResults() throws Exception {
      GaData data = getResults(getAnalytics(),getProfile());
      return data;
  }
  
  public static GaData getDateResults() throws Exception {
      GaData data = getDateResults(getAnalytics(),getProfile());
      return data;
  }
  

  private static Analytics initializeAnalytics() throws Exception {
    // Initializes an authorized analytics service object.

    // Construct a GoogleCredential object with the service account email
    // and p12 file downloaded from the developer console.
    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    GoogleCredential credential = new GoogleCredential.Builder()
        .setTransport(httpTransport)
        .setJsonFactory(JSON_FACTORY)
        .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
        .setServiceAccountPrivateKeyFromP12File(new File(KEY_FILE_LOCATION))
        .setServiceAccountScopes(AnalyticsScopes.all())
        .build();

    // Construct the Analytics service object.
    return new Analytics.Builder(httpTransport, JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME).build();
  }


  
  private static String getFirstProfileId(Analytics analytics) throws IOException {
    // Get the first view (profile) ID for the authorized user.
    String profileId = null;

    // Query for the list of all accounts associated with the service account.
    Accounts accounts = analytics.management().accounts().list().execute();

    if (accounts.getItems().isEmpty()) {
      System.err.println("No accounts found");
    } else {
      String firstAccountId = accounts.getItems().get(0).getId();

      // Query for the list of properties associated with the first account.
      Webproperties properties = analytics.management().webproperties()
          .list(firstAccountId).execute();

      if (properties.getItems().isEmpty()) {
        System.err.println("No Webproperties found");
      } else {
        String firstWebpropertyId = properties.getItems().get(0).getId();

        // Query for the list views (profiles) associated with the property.
        Profiles profiles = analytics.management().profiles()
            .list(firstAccountId, firstWebpropertyId).execute();

        if (profiles.getItems().isEmpty()) {
          System.err.println("No views (profiles) found");
        } else {
          // Return the first (view) profile associated with the property.
          profileId = profiles.getItems().get(0).getId();
        }
      }
    }
    return profileId;
  }

  private static GaData getResults(Analytics analytics, String profileId) throws IOException {
    // Query the Core Reporting API for the number of sessions
    // in the past seven days.
    return analytics.data().ga()
            
        .get("ga:" + profileId, "2015-01-01", "today", "ga:users,ga:sessions,ga:bounces,ga:bounceRate,ga:sessionDuration ")
            .setDimensions("ga:source,ga:browser,ga:operatingSystem,ga:mobileDeviceBranding,ga:date")
            .setSort("ga:users")
        .execute();
  }
  
  private static GaData getDateResults(Analytics analytics, String profileId) throws IOException {
      return analytics.data().ga()
              
          .get("ga:" + profileId, "2015-01-01", "today", "ga:users,ga:sessions,ga:avgSessionDuration,ga:percentNewSessions,ga:bounces,ga:bounceRate,ga:timeOnPage ")
              .setDimensions("ga:date")
              .setSort("ga:date")
          .execute();
  }
  
 
  private static void printResults(GaData results) {
    // Parse the response from the Core Reporting API for
    // the profile name and number of sessions.
      System.out.println("column headers");
      for (GaData.ColumnHeaders header : results.getColumnHeaders()){
          System.out.println("column name: " + header.getName());
          System.out.println("column Type: " + header.getColumnType());
          System.out.println("column data type " + header.getDataType());
      }
    if (results != null && !results.getRows().isEmpty()) {
      System.out.println("View (Profile) Name: "
        + results.getProfileInfo().getProfileName());
      System.out.println("Total Sessions: " + results.getRows().get(0).get(0));
    } else {
      System.out.println("No results found");
    }
  }
  
  private static void printDataTable(GaData gaData) {
      if(gaData.getTotalResults() > 0 ) {
          System.out.println("Data Table");
          
          for (GaData.ColumnHeaders header : gaData.getColumnHeaders()) {
              System.out.format("%-32s", header.getName() + '(' + header.getDataType() + ')');         
          }
          System.out.println();
          
          for (List<String> rowValues : gaData.getRows()) {
              for (String value : rowValues) {
                  System.out.format("%-32s", value);
              }
              System.out.println();
          }
      } else {
          System.out.println("No results found");
      }
  }
}


