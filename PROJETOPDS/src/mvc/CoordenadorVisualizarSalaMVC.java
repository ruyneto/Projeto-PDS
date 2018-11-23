/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.CoordenadorVisualizarMateriasControle;
import control.CoordenadorVisualizarSalasControle;
import view.CoordenadorVisualizarMateriasTela;
import view.CoordenadorVisualizarSalasTela;

/**
 *
 * @author Izaltino
 */
public class CoordenadorVisualizarSalaMVC {
    public static void main(String[] args){
       CoordenadorVisualizarSalasTela tela = new CoordenadorVisualizarSalasTela();
       CoordenadorVisualizarSalasControle controle = new CoordenadorVisualizarSalasControle(tela, null);
       tela.setVisible(true);
    }   
}
