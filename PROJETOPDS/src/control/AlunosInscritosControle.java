/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.List;
import model.Inscricao;
import view.AlunosInscritosTela;
import view.tableModels.AlunosTableModel;

/**
 *
 * @author sandr
 */
public class AlunosInscritosControle {
    private List<Inscricao> inscritos;
    private AlunosInscritosTela tela;
    
    public AlunosInscritosControle(AlunosInscritosTela tela, List<Inscricao> inscritos){
        this.inscritos = inscritos;
        this.tela = tela;
        listar();
    }
    
    public void listar(){
        tela.getTabela().setModel(new AlunosTableModel(inscritos));
    }
}
