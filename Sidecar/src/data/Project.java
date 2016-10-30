/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author david
 */
public class Project implements Serializable {
    private String projectName;
    private List<File> files;
    private List<String> backups;

    public Project(String projectName) {
        this.projectName = projectName;
        this.files = new ArrayList<>();
        this.backups = new ArrayList<>();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public boolean addFile(File file){
        if(this.files.contains(file)){
            return false;
        }else{
            this.files.add(file);
            return true;
        }
    }

    public List<File> getFiles() {
        return files;
    }

    public List<String> getBackups() {
        return backups;
    }
    
    public void addBackups(List<RemoteServer> servers){
        for(RemoteServer server: servers){
            this.backups.add(server.getServerID());
        }
    }
    
    public boolean save(){
        java.io.File theDir = new java.io.File(projectName);
        if(theDir.mkdir()){
            for(File file: files){
               if(!file.save(theDir.getAbsolutePath())){
                   return false;
               }
            }
            return true;
        }else{
            return false;
        }
    }

    public boolean update(Project project) {
        for(File file: files){
            boolean encontro = false;
            for(File projectFile: project.getFiles()){
                if(projectFile.getFileName().equals(file.getFileName())){
                    encontro = true;
                    if(!file.update(projectFile)){
                        return false;
                    }
                }
                if(!encontro){
                    //TODO create file
                }
            }
        }
        return true;
    }
}
