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

/**
 *
 * @author sandr
 */
public class MateriaDAO {
    private Connection connection;

    public MateriaDAO() {
        this.connection = FabricaConexao.getConnection();
    }
    
    public Vector<Materia> consultarMaterias(String str){
        try{
            String sql = "sp_consultamaterias()";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            ResultSet resultado = instrucao.executeQuery();
            Vector<Materia> materias = new Vector<>();
            while(resultado.next()){
                Materia materia = new Materia(resultado.getInt("matid"), resultado.getString("matnome"));
                materias.add(materia);
            }
            return materias;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
        }
    }
    
    public Vector<Materia> consultaMateriasAtivas(){
        try{
            String sql = "CALL sp_consultamateriasativas()";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            ResultSet resultado = instrucao.executeQuery();
            Vector<Materia> materias = new Vector<>();
            while(resultado.next()){
                Materia materia = new Materia(resultado.getInt("matid"), resultado.getString("matnome"));
                materias.add(materia);
            }
            return materias;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
        }
    }
}
