/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.CoordenadorAlterarMateriasControle;
import control.CoordenadorAlterarSalasControle;
import model.Materia;
import model.Sala;
import view.CoordenadorAlterarMateriasTela;
import view.CoordenadorAlterarSalasTela;

/**
 *
 * @author Izaltino
 */
public class CoordenadorAlterarSalasMVC {
    
    public static void main(Sala sala){
        CoordenadorAlterarSalasTela tela = new CoordenadorAlterarSalasTela();
        CoordenadorAlterarSalasControle control = new CoordenadorAlterarSalasControle(tela, sala);
        tela.setVisible(true);
    }
}
