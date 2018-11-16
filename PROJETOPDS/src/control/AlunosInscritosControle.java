/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.List;
import model.Inscricao;
import model.Monitoria;
import view.AlunosInscritosTela;
import view.tableModels.AlunosTableModel;

/**
 *
 * @author sandr
 */
public class AlunosInscritosControle {
    private List<Inscricao> inscritos;
    private AlunosInscritosTela tela;
    private Monitoria monitoria;
    
    public AlunosInscritosControle(AlunosInscritosTela tela, List<Inscricao> inscritos, Monitoria monitoria){
        this.inscritos = inscritos;
        this.tela = tela;
        this.monitoria = monitoria;
        labels();
        listar();
    }
    
    public void labels(){
        tela.getNomeMonitor().setText(monitoria.getMonitor().getNome());
        tela.getNomeMateria().setText(monitoria.getMonitor().getMateria().getNome());
        tela.getTotal().setText(String.valueOf(inscritos.size()));
    }
    
    public void listar(){
        tela.getTabela().setModel(new AlunosTableModel(inscritos));
    }
}
