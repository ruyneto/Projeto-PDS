/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.MateriaDAO;
import dao.SalaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Materia;
import model.Sala;
import util.Alert;
import view.CoordenadorCadastrarMateriasTela;
import view.CoordenadorCadastrarSalasTela;

/**
 *
 * @author Izaltino
 */
public class CoordenadorCadastrarSalasControle {

    private CoordenadorCadastrarSalasTela tela;
    private Sala sala;

    public CoordenadorCadastrarSalasControle(CoordenadorCadastrarSalasTela tela, Sala sala) {
        this.tela = tela;
        this.sala = sala;
        
        this.tela.getBtSalvar().addActionListener(new listenerBtSalvar());
    }
    
    class listenerBtSalvar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
          
            sala.setNome(tela.getCpNome());
            if(!sala.getNome().equals("")){
                SalaDAO dao = new SalaDAO();
                try {
                    dao.inserirSala(sala);
                    Alert.salaInserirSucesso(sala);
                    tela.dispose();
                } catch (Exception ex) {
                    System.out.println("Erro ao inserir!!");
                }
            }
            else{
                JOptionPane.showMessageDialog(tela, "O campo sala não pode ser vazio!");
            }
        }
    }
}
