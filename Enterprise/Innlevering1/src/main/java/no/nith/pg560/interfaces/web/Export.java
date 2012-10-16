package no.nith.pg560.interfaces.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
 
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
 
@Named
@ConversationScoped
public class Export implements Serializable {
   
    private static final long serialVersionUID = 1L;
    private String htmlBuffer;
    
    public Export() {
         
    }
     
    public void exportHtmlTableToExcel() throws IOException{       
 
//        //Set the filename
//        DateTime dt = new DateTime();
//        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd_HHmmss");
        String filename = "results2" + ".xls";
         
         
        //Setup the output
        String contentType = "application/vnd.ms-excel";
        FacesContext fc = FacesContext.getCurrentInstance();
         
        HttpServletResponse response = (HttpServletResponse)fc.getExternalContext().getResponse();
        response.setHeader("Content-disposition", "attachment; filename=" + filename);
        response.setContentType(contentType);
         
        //Write the table back out
        PrintWriter out = response.getWriter();
        out.print(htmlBuffer);
        out.close();
        fc.responseComplete();
    } 
     
    public String getHtmlBuffer() {
        return htmlBuffer;
    }
     
    public void setHtmlBuffer(String htmlBuffer) {
        this.htmlBuffer = htmlBuffer;
    }
 
}
