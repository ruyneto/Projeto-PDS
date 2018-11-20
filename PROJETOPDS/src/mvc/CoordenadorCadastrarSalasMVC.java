/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.CoordenadorCadastrarMateriasControle;
import control.CoordenadorCadastrarSalasControle;
import model.Materia;
import model.Sala;
import view.CoordenadorCadastrarMateriasTela;
import view.CoordenadorCadastrarSalasTela;

/**
 *
 * @author Izaltino
 */
public class CoordenadorCadastrarSalasMVC {

    public static void main(String[] args){
        CoordenadorCadastrarSalasTela tela = new CoordenadorCadastrarSalasTela();
        CoordenadorCadastrarSalasControle control = new CoordenadorCadastrarSalasControle(tela, new Sala());
        tela.setVisible(true);
    }
}
