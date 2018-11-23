/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.CoordenadorCadastrarMateriasControle;
import model.Materia;
import view.CoordenadorCadastrarMateriasTela;

/**
 *
 * @author Izaltino
 */
public class CoordenadorCadastrarMateriasMVC {

    public static void main(String[] args){
        CoordenadorCadastrarMateriasTela tela = new CoordenadorCadastrarMateriasTela();
        CoordenadorCadastrarMateriasControle control = new CoordenadorCadastrarMateriasControle(tela, new Materia());
        tela.setVisible(true);
    }
}
