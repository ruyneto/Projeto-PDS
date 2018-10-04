/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import model.Horario;
import model.Monitoria;
import view.MonitoriaPesquisaTela;

/**
 *
 * @author Izaltino
 */
public class MonitoriaControle {
    private MonitoriaPesquisaTela tela;
    private Monitoria modelo;
    private Vector<Monitoria> monitorias;

    public MonitoriaControle(MonitoriaPesquisaTela tela, Monitoria modelo) {
        this.tela = tela;
        this.modelo = modelo;
    }
    
    
    
}
