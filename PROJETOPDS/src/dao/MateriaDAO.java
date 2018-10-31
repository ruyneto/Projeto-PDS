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
            String sql = "call sp_consultamaterias()";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            ResultSet resultado = instrucao.executeQuery();
            Vector<Materia> materias = new Vector<>();
            while(resultado.next()){
                Materia materia = new Materia(resultado.getInt("matid"), resultado.getString("matnome"),resultado.getInt("matativa"));
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
    
    public Vector<Materia> pesquisarMateria(String texto){
    String sql = "SELECT * FROM materia WHERE matnome LIKE ?";        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,"%"+texto+"%");
           ResultSet resultado = ps.executeQuery();
            Vector<Materia> materias = new Vector<>();
            while(resultado.next()){
                Materia materia = new Materia(resultado.getInt("matid"), resultado.getString("matnome"), resultado.getInt("matativa"));
                materias.add(materia);
            }
            return materias; 
           
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    
    public boolean inserirMateria(Materia materia){
        String sql = "INSERT INTO materia (matnome,matativa) VALUES (?,?)";        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, materia.getNome());
            ps.setInt(2, materia.getAtiva());
            ps.execute();
            
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    } 
    
    public boolean alterarMateria(Materia materia){
         String sql = "UPDATE materia SET matnome = ?, matativa = ? WHERE matid = ?";        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, materia.getNome());
            ps.setInt(2,materia.getAtiva());
            ps.setInt(3, materia.getId());
            ps.execute();
            
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
    public boolean deletarMateria(Materia materia){
         String sql = "DELETE FROM materia WHERE matid = ?";        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, materia.getId());
            ps.execute();
            
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public Vector<Vector> materiasMaisRequisitadas(){
        try{
            String sql = "call sp_disciplinasmaisrequisitadas()";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            ResultSet resultado = instrucao.executeQuery();
            Vector<Vector> materias = new Vector<>();
            while(resultado.next()){
                Vector linha = new Vector();
                Materia materia = new Materia();
                materia.setId(resultado.getInt("matid"));
                materia.setNome(resultado.getString("matnome"));
                linha.add(materia);
                linha.add(resultado.getInt("numero de inscricoes"));
                materias.add(linha);
            }
            return materias;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
        }
    }
}
