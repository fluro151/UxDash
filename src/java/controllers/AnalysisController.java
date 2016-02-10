/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.AnalysisDAO;
import dto.AnalysisDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Harrison
 */
@Named
@RequestScoped
public class AnalysisController{
    private AnalysisDTO analysisDTO = new AnalysisDTO();
    private String note;
    private UIComponent notes;
    private UIInput input;
    

    public UIInput getInput() {
        return input;
    }

    public void setInput(UIInput input) {
        this.input = input;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String Note) throws NamingException, SQLException {
        System.out.println("Setting note" + Note);
        this.note = Note;
    }

    public ArrayList<AnalysisDTO> getAll() throws NamingException, SQLException {
        AnalysisDAO analysisDAO = new AnalysisDAO();
        ArrayList<AnalysisDTO> analysis = new ArrayList<>();
        analysis = analysisDAO.listAll();
        return analysis;
    }
    
    public AnalysisDTO getAnalysisDTO() {
        return analysisDTO;
    }
    public void setAnalysis(AnalysisDTO analysis) {
        this.analysisDTO = analysis;
    }
    
    public void setNotes() throws NamingException, SQLException {
        String notes = this.note;
        FacesContext context = FacesContext.getCurrentInstance();
        AnalysisDAO analysis = new AnalysisDAO();
        
        try {
            analysis.insertNote(notes);
            this.note = "";
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(e.getMessage()));
        }
        
        
    }
       
}
