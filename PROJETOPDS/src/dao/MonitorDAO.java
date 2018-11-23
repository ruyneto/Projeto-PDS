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
import javax.swing.JOptionPane;
import model.Aluno;
import model.Materia;
import model.Monitor;

/**
 *
 * @author sandr
 */
public class MonitorDAO {
    private Connection connection;
    
    public MonitorDAO(){
        connection = FabricaConexao.getConnection();
    }
    
    public Vector<Monitor> consultaMonitores(String str){
        try{
            String sql = "CALL sp_consultamonitores(?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, "%"+str+"%");
            ResultSet resultado = instrucao.executeQuery();

            Vector<Monitor> monitores = new Vector<>();
            while(resultado.next()){
                Materia materia = new Materia();
                materia.setId(resultado.getInt("matid"));
                materia.setNome(resultado.getString("matnome"));
                Monitor monitor = new Monitor();
                monitor.setCpf(resultado.getString("usucpf"));
                monitor.setNome(resultado.getString("usunome"));
                monitor.setMateria(materia);
                monitores.add(monitor);
            }
            return monitores;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public Monitor consultaMonitor(String str){
        try{
            String sql = "CALL sp_consultamonitor(?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, str);
            ResultSet resultado = instrucao.executeQuery();

            Monitor monitor = new Monitor();
            while(resultado.next()){
                Materia materia = new Materia();
                materia.setId(resultado.getInt("matid"));
                materia.setNome(resultado.getString("matnome"));
                monitor.setCpf(resultado.getString("usucpf"));
                monitor.setNome(resultado.getString("usunome"));
                monitor.setMateria(materia);
            }
            return monitor;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public void registrarMonitor(Aluno a, Materia m){
        try{
            String sql = "CALL sp_registrarmonitor(?,?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, a.getCpf());
            instrucao.setInt(2, m.getId());
            instrucao.execute();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Este aluno não pode ser registrado\n"
                    + "novamente neste semestre");
        }
    }
    
    public void inativarMonitor(Monitor mon, Materia m){
        try{
            String sql = "CALL sp_inativarmonitor(?,?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, mon.getCpf());
            instrucao.setInt(2, m.getId());
            instrucao.execute();
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public boolean validaAluno(Aluno a){
        try{
            String sql = "SELECT f_validaaluno(?) AS resp";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, a.getCpf());
            ResultSet resultado = instrucao.executeQuery();
            while(resultado.next()){
                return resultado.getBoolean("resp");
            }
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
        return false;
    }

    public  Vector<Vector> monitoresMaisRequisitados(String str){
     try{
            String sql = "CALL sp_monitoresmaisrequisitados(?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, str);
            ResultSet resultado = instrucao.executeQuery();

            Vector<Vector> monitores = new Vector<>();
            while(resultado.next()){
                Vector linha= new Vector();
                Materia materia = new Materia();
                materia.setId(resultado.getInt("matid"));
                materia.setNome(resultado.getString("matnome"));
                Monitor monitor = new Monitor();
                monitor.setCpf(resultado.getString("usucpf"));
                monitor.setNome(resultado.getString("usunome"));
                monitor.setMateria(materia);
                
                linha.add(monitor);
                
                linha.add(resultado.getInt("numero de inscricoes"));
                
                monitores.add(linha);
            }
            return monitores;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    } 

    public Vector<Vector> monitoresQueOferecemMaisHorarios(String str){
    try{
            String sql = "CALL sp_monitoresqueoferecemmaishorarios(?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, str);
            ResultSet resultado = instrucao.executeQuery();

            Vector<Vector> monitores = new Vector<>();
            while(resultado.next()){
                Vector linha= new Vector();
                Materia materia = new Materia();
                materia.setId(resultado.getInt("matid"));
                materia.setNome(resultado.getString("matnome"));
                Monitor monitor = new Monitor();
                monitor.setCpf(resultado.getString("usucpf"));
                monitor.setNome(resultado.getString("usunome"));
                monitor.setMateria(materia);
                
                linha.add(monitor);
                
                linha.add(resultado.getInt("horarios oferecidos"));
                
                monitores.add(linha);
            }
            return monitores;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public Vector<Vector> monitoresQueOferecemMenosHorarios(String str){
    try{
            String sql = "CALL sp_monitoresqueoferecemmenoshorarios(?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, str);
            ResultSet resultado = instrucao.executeQuery();

            Vector<Vector> monitores = new Vector<>();
            while(resultado.next()){
                Vector linha= new Vector();
                Materia materia = new Materia();
                materia.setId(resultado.getInt("matid"));
                materia.setNome(resultado.getString("matnome"));
                Monitor monitor = new Monitor();
                monitor.setCpf(resultado.getString("usucpf"));
                monitor.setNome(resultado.getString("usunome"));
                monitor.setMateria(materia);
                
                linha.add(monitor);
                
                linha.add(resultado.getInt("horarios oferecidos"));
                
                monitores.add(linha);
            }
            return monitores;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        } 
    }
}
