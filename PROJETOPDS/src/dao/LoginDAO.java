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
import model.Usuario;

/**
 *
 * @author sandr
 */
public class LoginDAO {
    private Connection connection = FabricaConexao.getConnection();
    
    public Usuario validacao(String login, String senha, String funcao){
        Usuario usu = new Usuario();
        try{
            String sql = "CALL sp_validacao(?,?,?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, login);
            instrucao.setString(2, senha);
            instrucao.setString(3, funcao);
            
            ResultSet resultado = instrucao.executeQuery();
            
            while(resultado.next()){
                usu.setCpf(resultado.getString("usucpf"));
                usu.setNome(resultado.getString("usunome"));
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return usu;
    }
}
