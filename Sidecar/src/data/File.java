/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class File implements Serializable{
    private String fileName;
    private String filePath;
    private byte[] file;

    public File(String fileName, String filePath) throws FileNotFoundException, IOException {
        this.fileName = fileName;
        this.filePath = filePath;
        java.io.File fileAux = new java.io.File(filePath);
        FileInputStream fis = new FileInputStream(fileAux);
        file = new byte[(int) fileAux.length()];
        fis.read(file);
        fis.close();
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

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public boolean save(String projectPath){
        try {
            System.out.println("Salvando archivo " + fileName);
            this.filePath = projectPath + "/" + fileName;
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(file);
            fos.close();
            return true;
        } catch (FileNotFoundException ex ) {
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    boolean update(File file) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(file.getFile());
            fos.close();
            return true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
}
