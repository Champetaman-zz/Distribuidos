/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author TG1604
 */
public class FileInfo {
    
    private Set<String> copies;

    public FileInfo() {
        this.copies = new HashSet<>();
    }
    
    public void addCopy(String serverName){
        copies.add(serverName);
    }
    
    public Set<String> getCopies() {
        return copies;
    }

    public void setCopies(Set<String> copies) {
        this.copies = copies;
    }
}
