/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import csvConverter.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Harrison
 */
@Named
@RequestScoped
public class ContentController {

    public void uploadContent(FileUploadEvent event) throws IOException {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Upload", event.getFile().getFileName());
        FacesContext.getCurrentInstance().addMessage(null, message);
        FileUpload fileupload = new FileUpload();
        fileupload.handleFileUpload(event);
    }
}
