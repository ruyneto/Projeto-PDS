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
import model.Usuario;

/**
 *
 * @author sandr
 */
public class UsuarioDAO {
    private Connection connection = FabricaConexao.getConnection();
    
    public Vector<Vector> TipoDoUsuario(Usuario usuario){
        Vector linha = new Vector();
        linha.add(null);
        linha.add(null);
        linha.add(null);
        linha.add(null);
        try{
            String sql = "select * from tipousuario " +
                        "where tususucpf = ?;";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, usuario.getCpf());
            
            ResultSet resultado = instrucao.executeQuery();
            
            while(resultado.next()){
                switch(resultado.getInt("tusfcoid")){
                    case 1: linha.set(1, 1);break;
                    case 2: linha.set(2, 2);break;
                    case 3: linha.set(3, 3);break;
               
                }
                
            }
            connection.close();
        }catch(SQLException ex){
            System.out.println(ex);
        }
 
       return linha;
    }
}
