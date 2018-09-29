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
public class Monitoria {

    public Monitoria(Materia materia,String horarioInicial,String horarioFinal) {
        this.materia = materia;
        this.horarioInicial = horarioInicial;
        this.horarioFinal = horarioFinal;
    }
    
    private Materia materia;
    private String horarioInicial;
    private String horarioFinal;

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public String getHorarioInicial() {
        return horarioInicial;
    }

    public void setHorarioInicial(String horarioInicial) {
        this.horarioInicial = horarioInicial;
    }

    public String getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(String horarioFinal) {
        this.horarioFinal = horarioFinal;
    }
}
