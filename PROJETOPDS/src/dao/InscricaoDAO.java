/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.FabricaConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Aluno;
import model.Monitoria;

/**
 *
 * @author sandr
 */
public class InscricaoDAO {
    private Connection connection;
    
    public InscricaoDAO(){
        connection = FabricaConexao.getConnection();
    }
    
    public boolean inserirInscricao(Aluno a, Monitoria m){
        try{
            String sql = "INSERT INTO inscricao VALUES (?,?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, a.getCpf());
            instrucao.setInt(2, m.getId());
            instrucao.execute();
            instrucao.clearParameters();
            sql = "UPDATE monitoria SET miavagas = miavagas-1 WHERE miaid = ?";
            instrucao = connection.prepareStatement(sql);
            instrucao.setInt(1, m.getId());
            instrucao.execute();
       
            return true;
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return false;
    }
    
    public boolean removerInscricao(Aluno a, Monitoria m){
        try{
            String sql = "DELETE FROM inscricao WHERE "+
                            "insalucpf = ? AND insmiaid = ?";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, a.getCpf());
            instrucao.setInt(2, m.getId());
            instrucao.execute();
            instrucao.clearParameters();
            sql = "UPDATE monitoria SET miavagas = miavagas+1 WHERE miaid = ?";
            instrucao = connection.prepareStatement(sql);
            instrucao.setInt(1, m.getId());
            instrucao.execute();
            return true;
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return false;
    }
}
