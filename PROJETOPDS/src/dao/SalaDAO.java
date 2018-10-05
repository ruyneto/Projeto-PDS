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
import model.Sala;

/**
 *
 * @author sandr
 */
public class SalaDAO {
    private Connection connection;
    
    public SalaDAO(){
        this.connection = FabricaConexao.getConnection();
    }
    
    public Vector<Sala> consultarSala(String str){
        try{
            String sql = "SELECT * FROM sala";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            ResultSet resultado = instrucao.executeQuery();
            Vector<Sala> salas = new Vector<>();
            while(resultado.next()){
                Sala sala = new Sala(resultado.getInt("salid"), resultado.getString("salnome"));
                salas.add(sala);
            }
            return salas;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
        }
    }
}
