/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.CoordenadorAlterarMateriasControle;
import model.Materia;
import view.CoordenadorAlterarMateriasTela;

/**
 *
 * @author Izaltino
 */
public class CoordenadorAlterarMateriasMVC {
    
    public static void main(Materia materia){
        CoordenadorAlterarMateriasTela tela = new CoordenadorAlterarMateriasTela();
        CoordenadorAlterarMateriasControle control = new CoordenadorAlterarMateriasControle(tela, materia);
        tela.setVisible(true);
    }
}
