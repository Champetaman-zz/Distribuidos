/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import java.util.List;
import skeleton.ServerSkeleton;

/**
 *
 * @author david
 */
public class Project {
    private String projectName;
    private List<File> files;
    private List<ServerSkeleton> backups;

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
}
