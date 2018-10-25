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
        

        if(materia.getAtiva()==1) {
            this.tela.getBtAtiva().setSelected(true);
            this.tela.getBtAtiva().setText("SIM");

        }
        else {
            this.tela.getBtAtiva().setSelected(false);
            this.tela.getBtAtiva().setText("NÃO");
        
        }
        this.tela.getBtSalvar().addActionListener(new listenerBtSalvar());
        this.tela.getBtAtiva().addActionListener(new listenerBtAtiva());
        
    }
    
    class listenerBtAtiva implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(tela.getBtAtiva().isSelected()) tela.getBtAtiva().setText("SIM");
            else tela.getBtAtiva().setText("NÃO");

            
        }
    
    }
    
    class listenerBtSalvar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
          materia.setNome(tela.getCpNome().getText());
          if(tela.getBtAtiva().isSelected()) materia.setAtiva(1);
          else materia.setAtiva(0);
          
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
