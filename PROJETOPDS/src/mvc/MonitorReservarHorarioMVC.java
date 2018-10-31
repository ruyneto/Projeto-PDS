/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.MonitorReservarHorarioControle;
import model.Monitor;
import view.MonitorReservarHorarioTela;

/**
 *
 * @author sandr
 */
public class MonitorReservarHorarioMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Monitor monitor = new Monitor("555.555.555-55", "Sandro", null);
        MonitorReservarHorarioTela tela = new MonitorReservarHorarioTela();
        MonitorReservarHorarioControle controle = new MonitorReservarHorarioControle(monitor, tela);
        tela.setVisible(true);
    }
}