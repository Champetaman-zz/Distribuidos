/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author TG1604
 */
public class Agenda {
    
    private Map<String, AgendaItem> agenda;
    private static Agenda instance;
    private Agenda(){
        this.agenda = new HashMap<>();
    }
    
    public static Agenda getInstance(){
        if(instance == null)
            instance = new Agenda();
        return instance;
    }

    public Map<String, AgendaItem> getAgenda() {
        return agenda;
    }

    public void setAgenda(Map<String, AgendaItem> agenda) {
        this.agenda = agenda;
    }
}
