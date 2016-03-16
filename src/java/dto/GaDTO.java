/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.Date;

/**
 *
 * @author Harrison
 */
public class GaDTO {
 
    private String webSource;
    private String browser;
    private String operatingSystem;
    private String mobileDeviceBranding;
    private int users;
    private int sessions;
    private double sessionDuration;
    private int bounces;
    private double bounceRate;
    private Date accessDate;
    private int Month;
    private int Year;
    private String dataSource;
    private double percentNewSessions;
    private double timeOnPage;

    public double getPercentNewSessions() {
        return percentNewSessions;
    }

    public void setPercentNewSessions(double percentNewSessions) {
        this.percentNewSessions = percentNewSessions;
    }

    public double getTimeOnPage() {
        return timeOnPage;
    }

    public void setTimeOnPage(double timeOnPage) {
        this.timeOnPage = timeOnPage;
    }


    public int getMonth() {
        return Month;
    }

    public void setMonth(int Month) {
        this.Month = Month;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int Year) {
        this.Year = Year;
    }
  


    

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getWebSource() {
        return webSource;
    }

    public void setWebSource(String webSource) {
        this.webSource = webSource;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getMobileDeviceBranding() {
        return mobileDeviceBranding;
    }

    public void setMobileDeviceBranding(String mobileDeviceBranding) {
        this.mobileDeviceBranding = mobileDeviceBranding;
    }

    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public int getSessions() {
        return sessions;
    }

    public void setSessions(int sessions) {
        this.sessions = sessions;
    }

    public double getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(int sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public int getBounces() {
        return bounces;
    }

    public void setBounces(int bounces) {
        this.bounces = bounces;
    }

    public double getBounceRate() {
        return bounceRate;
    }

    public void setBounceRate(int bounceRate) {
        this.bounceRate = bounceRate;
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }
            
           
    
}
