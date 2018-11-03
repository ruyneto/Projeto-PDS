/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ruyneto
 */
public class Monitoria implements Comparable<Monitoria>{
    private int id;
    private int vagas;
    private Materia materia;
    private Monitor monitor;
    private DiaDaSemana dia;
    private Horario hora;
    private Sala sala;
    private boolean inscrito;

    public Monitoria(int id, int vagas, boolean inscrito, Materia materia, Monitor monitor, DiaDaSemana dia, Horario hora, Sala sala) {
        this.materia = materia;
        this.monitor = monitor;
        this.dia = dia;
        this.hora = hora;
        this.sala = sala;
        this.id = id;
        this.vagas = vagas;
        this.inscrito = inscrito;
    }
    
    public Monitoria(int id, int vagas, Materia materia, Monitor monitor, DiaDaSemana dia, Horario hora, Sala sala) {
        this.materia = materia;
        this.monitor = monitor;
        this.dia = dia;
        this.hora = hora;
        this.sala = sala;
        this.id = id;
        this.vagas = vagas;
    }
    
    public Monitoria(DiaDaSemana dia, Horario hora, Sala sala) {
        this.dia = dia;
        this.hora = hora;
        this.sala = sala;
    }
    
    public Monitoria(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public DiaDaSemana getDia() {
        return dia;
    }

    public void setDia(DiaDaSemana dia) {
        this.dia = dia;
    }

    public Horario getHora() {
        return hora;
    }

    public void setHora(Horario hora) {
        this.hora = hora;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public boolean isInscrito() {
        return inscrito;
    }

    public void setInscrito(boolean inscrito) {
        this.inscrito = inscrito;
    }

    @Override
    public int compareTo(Monitoria t) {
        if(this.dia.getId()<t.getId()){
            if(this.getHora().getHoraInicio().compareTo(t.getHora().getHoraInicio())==-1){
                return -1;
            }
            else{
                return 1;
            }
        }
        if(this.dia.getId()>t.getId()){
            return 1;
        }
        return 0;
    }
}
