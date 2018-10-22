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
import model.Aluno;

/**
 *
 * @author sandr
 */
public class AlunoDAO {
    private Connection connection;
    
    public AlunoDAO(){
        connection = FabricaConexao.getConnection();
    }
    
    public Vector<Aluno> consultaAlunos(){
        try{
            String sql = "CALL sp_consultaalunos()";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            ResultSet resultado = instrucao.executeQuery();

            Vector<Aluno> alunos = new Vector<>();
            while(resultado.next()){
                Aluno aluno = new Aluno();
                aluno.setCpf(resultado.getString("usucpf"));
                aluno.setNome(resultado.getString("usunome"));
                alunos.add(aluno);
            }
            return alunos;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
        }
    }
}
