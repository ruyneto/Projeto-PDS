/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.MateriaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Materia;
import util.Alert;
import view.CoordenadorAlterarMateriasTela;
import view.CoordenadorCadastrarMateriasTela;

/**
 *
 * @author Izaltino
 */
public class CoordenadorAlterarMateriasControle {

    private CoordenadorAlterarMateriasTela tela;
    private Materia materia;

    public CoordenadorAlterarMateriasControle(CoordenadorAlterarMateriasTela tela, Materia materia) {
        this.tela = tela;
        this.materia = materia;
        this.tela.getCpNome().setText(materia.getNome());
                
        this.tela.getBtSalvar().addActionListener(new listenerBtSalvar());
    }
    
    
    
    class listenerBtSalvar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
          
          materia.setNome(tela.getCpNome().getText());
          MateriaDAO dao = new MateriaDAO();
            try {
                dao.alterarMateria(materia);
                Alert.materiaAlterarSucesso(materia);
                tela.dispose();
            } catch (Exception ex) {
                System.out.println("Erro ao inserir!!");
            }
          }
    }
}
