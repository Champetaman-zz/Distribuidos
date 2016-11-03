/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class File implements Serializable{
    private String fileName;
    private String filePath;
    private String localCopy;
    BasicFileAttributes metadata;

    public File(String fileName, String filePath) throws FileNotFoundException, IOException {
        this.fileName = fileName;
        this.filePath = filePath;
        this.metadata = Files.readAttributes(Paths.get(filePath), BasicFileAttributes.class);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }  

    public boolean save(String projectPath){
        return true;
    }

    boolean update(File file) {
        return true;
    }

    public String getLocalCopy() {
        return localCopy;
    }

    public void setLocalCopy(String localCopy) {
        this.localCopy = localCopy;
    }
    
    public boolean updateMetadata(){
        try {
            this.metadata = Files.readAttributes(Paths.get(filePath), BasicFileAttributes.class);
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
}
