/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.DiaDAO;
import dao.HorarioDAO;
import dao.MateriaDAO;
import dao.MonitoriaDAO;
import dao.SalaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.DiaDaSemana;
import model.Horario;
import model.Materia;
import model.Monitoria;
import model.Sala;
import view.MonitoriaCadastrarTela;

/**
 *
 * @author sandr
 */
public class MonitoriaCadastroControle {
    private MonitoriaCadastrarTela tela;
    private ActionListener combo;

    public MonitoriaCadastroControle(MonitoriaCadastrarTela tela) {
        this.tela = tela;
        carregarComboMateria();
        carregarComboSala();
        carregarComboDia();
        carregarComboHorario((Sala)tela.getCbSala().getSelectedItem(),
                            (DiaDaSemana)tela.getCbDia().getSelectedItem());
        adicionarListener();
        tela.getBtnCadastrar().addActionListener(new addListenerBtSalvar());
    }
    
    public void carregarComboMateria(){
        MateriaDAO dao = new MateriaDAO();
        tela.getCbMateria().removeAllItems();
        for(Materia m: dao.consultarMateria("_")){
            tela.getCbMateria().addItem(m);
        }
    }
    
    public void carregarComboSala(){
        SalaDAO dao = new SalaDAO();
        tela.getCbSala().removeAllItems();
        for(Sala s: dao.consultarSala("_")){
            tela.getCbSala().addItem(s);
        }
    }
    
    public void carregarComboDia(){
        DiaDAO dao = new DiaDAO();
        tela.getCbDia().removeAllItems();
        for(DiaDaSemana d: dao.consultarDia("_")){
            tela.getCbDia().addItem(d);
        }
    }
    
    public void carregarComboHorario(Sala s, DiaDaSemana d){
        HorarioDAO dao = new HorarioDAO();
        tela.getCbHora().removeAllItems();
        for(Horario h: dao.consultarHora(s, d)){
            tela.getCbHora().addItem(h);
        }
    }
    
    public void adicionarListener(){
        combo = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                carregarComboHorario((Sala)tela.getCbSala().getSelectedItem(),
                            (DiaDaSemana)tela.getCbDia().getSelectedItem());
            }
        };
        tela.getCbDia().addActionListener(combo);
        tela.getCbSala().addActionListener(combo);
    }
    
    class addListenerBtSalvar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            MonitoriaDAO dao = new MonitoriaDAO();
            Materia materia = (Materia)tela.getCbMateria().getSelectedItem();
            Sala sala = (Sala)tela.getCbSala().getSelectedItem();
            DiaDaSemana dia = (DiaDaSemana)tela.getCbDia().getSelectedItem();
            Horario hora = (Horario)tela.getCbHora().getSelectedItem();
            
            Monitoria monitoria = new Monitoria(materia, dia, hora, sala);
            
            boolean cadastrou = dao.inserirMonitoria(monitoria);
            
            if(cadastrou){
                JOptionPane.showMessageDialog(null, "Aula de monitoria cadastrada\n"+
                                                    "com sucesso");
            }
            else{
                JOptionPane.showMessageDialog(null, "Err ao cadastrar Aula de monitoria");
            }
            tela.dispose();
        }
        
    }
}
