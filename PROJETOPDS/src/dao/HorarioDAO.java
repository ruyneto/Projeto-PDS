
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.FabricaConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.DiaDaSemana;
import model.Horario;
import model.Sala;

/**
 *
 * @author sandr
 */
public class HorarioDAO {
    private Connection connection;
    
    public HorarioDAO(){
        this.connection = FabricaConexao.getConnection();
    }
    
    public Vector<Horario> consultarHora(Sala s, DiaDaSemana d){
        try{
            String sql = "CALL sp_consultarhorasdisponiveis(?,?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, s.getNome());
            instrucao.setString(2, d.getNome());
            ResultSet resultado = instrucao.executeQuery();
            Vector<Horario> horarios = new Vector<>();
            while(resultado.next()){
                Horario hora = new Horario(resultado.getString("horhora"));
                horarios.add(hora);
            }
            return horarios;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
        }
    }

    public Vector<Vector> horariosMaisRequisitados(String str){
    try{
            String sql = "CALL sp_horariosmaisrequisitados()";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, str);
            ResultSet resultado = instrucao.executeQuery();
            Vector<Vector> horarios = new Vector<>();
            while(resultado.next()){
                Vector linha = new Vector();
                Horario hora = new Horario(resultado.getString("horhora"));
                linha.add(hora);                
                linha.add(resultado.getInt("numero de inscricoes"));                
                horarios.add(linha);
            }
            return horarios;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
        }
    }
}
