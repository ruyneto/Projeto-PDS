/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.MateriaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Materia;
import util.Alert;
import view.CoordenadorCadastrarMateriasTela;

/**
 *
 * @author Izaltino
 */
public class CoordenadorCadastrarMateriasControle {

    private CoordenadorCadastrarMateriasTela tela;
    private Materia materia;

    public CoordenadorCadastrarMateriasControle(CoordenadorCadastrarMateriasTela tela, Materia materia) {
        this.tela = tela;
        this.materia = materia;
        
        this.tela.getBtSalvar().addActionListener(new listenerBtSalvar());
    }
    
    class listenerBtSalvar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
          
            materia.setNome(tela.getCpNome());
            if(!materia.getNome().equals("")){
                MateriaDAO dao = new MateriaDAO();
                try {
                    dao.inserirMateria(materia);
                    Alert.materiaInserirSucesso(materia);
                    tela.dispose();
                } catch (Exception ex) {
                    System.out.println("Erro ao inserir!!");
                }
            }
            else{
                JOptionPane.showMessageDialog(tela, "O campo matéria não pode ser vazio!");
            }
        }
    }
}
