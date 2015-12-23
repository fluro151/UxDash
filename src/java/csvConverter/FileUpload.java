/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvConverter;
 
import csvConverter.Main;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
 
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
 
@ManagedBean
public class FileUpload {
    private UploadedFile uploadedFile;
    private static String dirPath;
    
    public void createTempDirectory(){
        Path basedir = FileSystems.getDefault().getPath("C:/Temp/Uxdash/");
        
        String default_tmp = System.getProperty("java.io.tmpdir");
        System.out.println(default_tmp);
        this.dirPath = default_tmp;
    }
 
    public void handleFileUpload(FileUploadEvent event) throws IOException {
//        this.file = event.getFile();
        uploadedFile = event.getFile();
        String filename = FilenameUtils.getName(uploadedFile.getFileName());
        InputStream input = uploadedFile.getInputstream();
        
        createTempDirectory();
        OutputStream output = new FileOutputStream(new File(dirPath, filename));
        
        try {
            IOUtils.copy(input, output);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
        Main.main(dirPath, filename);
    }
//        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
//        FacesContext.getCurrentInstance().addMessage(null, message);
//        System.out.println("Uploading now, wait please");
//        System.out.println(event.getFile());
    
  
}