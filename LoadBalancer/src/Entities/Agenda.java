/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author TG1604
 */
public class Agenda {
    
    //private Map<String, AgendaItem> agenda;
    private Queue<AgendaItem> agenda;
    private static Agenda instance;
    private Agenda(){
        this.agenda = new LinkedList<AgendaItem>();
    }
    
    public static Agenda getInstance(){
        if(instance == null)
            instance = new Agenda();
        return instance;
    }

    public Queue<AgendaItem> getAgenda() {
        return agenda;
    }

    public void setAgenda(Queue<AgendaItem> agenda) {
        this.agenda = agenda;
    }
    
}
