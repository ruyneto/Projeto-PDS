/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Time;

/**
 *
 * @author Izaltino
 */
public class Horario {
    private String horaInicio;

    public Horario(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Horario() {
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }    
    
    @Override
    public String toString(){
        return horaInicio;
    }
    
}
