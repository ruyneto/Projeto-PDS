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
import model.Materia;
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
    
    public Vector<Sala> consultarSalasAtivas(String str){
        try{
            String sql = "CALL sp_consultasalasativas";
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

public Vector<Sala> pesquisarSala(String texto){
    String sql = "SELECT * FROM sala WHERE salnome LIKE ?";        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,"%"+texto+"%");
           ResultSet resultado = ps.executeQuery();
            Vector<Sala> salas = new Vector<>();
            while(resultado.next()){
                Sala sala = new Sala(resultado.getInt("salid"), resultado.getString("salnome"));
                sala.setAtiva(resultado.getInt("salativa"));
                salas.add(sala);
            }
            return salas; 
           
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    
    public boolean inserirSala(Sala sala){
        String sql = "INSERT INTO sala (salnome,salativa) VALUES (?,1)";        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, sala.getNome());
            //ps.setInt(2, sala.getAtiva());
            ps.execute();
            
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    } 
    
    public boolean alterarSala(Sala sala){
         String sql = "UPDATE sala SET salnome = ?, salativa = ? WHERE salid = ?";        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, sala.getNome());
            ps.setInt(2,sala.getAtiva());
            ps.setInt(3, sala.getId());
            ps.execute();
            
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
    public boolean deletarSala(Sala sala){
         String sql = "DELETE FROM sala WHERE salid = ?";        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, sala.getId());
            ps.execute();
            
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }


}
