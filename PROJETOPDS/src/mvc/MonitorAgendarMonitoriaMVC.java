/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.MonitorPesquisaMonitoriaControle;
import model.Monitor;
import view.MonitorVisualizarTela;

/**
 *
 * @author sandr
 */
public class MonitorAgendarMonitoriaMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Monitor monitor = new Monitor("555.555.555-55", "Gustavo", null);
        MonitorVisualizarTela tela = new MonitorVisualizarTela();
        MonitorPesquisaMonitoriaControle controle = new MonitorPesquisaMonitoriaControle(monitor, tela);
        tela.setVisible(true);
    }
}