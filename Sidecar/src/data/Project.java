/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author david
 */
public class Project implements Serializable {
    private String projectName;
    private Map<String, File> archivos;

    public Project(String projectName) {
        this.projectName = projectName;
        this.archivos = new HashMap<>();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public boolean addFile(File file){
        if(this.archivos.get(file.getFileName()) != null){
            return false;
        }else{
            this.archivos.put(file.getFileName(), file);
            return true;
        }
    }

    public File getFile(String nombre){
        return archivos.get(nombre);
    }
    
    public boolean save(){
        return true;
    }

    public boolean update(Project project) {  
        return true;
    }
}
