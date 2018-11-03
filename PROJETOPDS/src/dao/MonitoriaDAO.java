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
import java.util.List;
import java.util.Vector;
import model.Aluno;
import model.DiaDaSemana;
import model.Horario;
import model.Materia;
import model.Monitor;
import model.Monitoria;
import model.Sala;

/**
 *
 * @author Izaltino
 */
public class MonitoriaDAO {
     private Connection connection;
     
     public MonitoriaDAO(){
         this.connection = FabricaConexao.getConnection();
     }
     
    public int verificaConflito(Monitoria m, Aluno a){
        String sql = "SELECT f_verificaconflito(?,?,?) as resp";
        int resp=0;
        try{
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setInt(1, m.getDia().getId());
            instrucao.setString(2, m.getHora().getHoraInicio());
            instrucao.setString(3, a.getCpf());
            ResultSet resultado = instrucao.executeQuery();
            while(resultado.next()){
                resp = resultado.getInt("resp");
            }
            return resp;
        }catch(SQLException ex){
            System.out.println(ex);
            return -1;
        }
    }
    
    public int verificaConflitoMonitor(Monitoria m, Monitor mon){
        String sql = "SELECT f_verificaconflitomonitor(?,?,?) as resp";
        int resp=0;
        try{
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setInt(1, m.getDia().getId());
            instrucao.setString(2, m.getHora().getHoraInicio());
            instrucao.setString(3, mon.getCpf());
            ResultSet resultado = instrucao.executeQuery();
            while(resultado.next()){
                resp = resultado.getInt("resp");
            }
            return resp;
        }catch(SQLException ex){
            System.out.println(ex);
            return -1;
        }
    }
    
    public Monitoria consultarMonitoria(int miaid){
        try{
            String sql = "CALL sp_consultamonitoria(?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setInt(1, miaid);
            ResultSet resultado = instrucao.executeQuery();
            Monitoria monitoria=null;
            while(resultado.next()){
                Sala sala = new Sala(resultado.getInt("salid"), resultado.getString("salnome"));
                System.out.println(sala.toString());
                Horario hora = new Horario(resultado.getString("horhora"));
                System.out.println(hora.toString());
                Materia materia = new Materia(resultado.getInt("matid"), resultado.getString("matnome"));
                System.out.println(materia.toString());
                DiaDaSemana dia = new DiaDaSemana(resultado.getInt("diaid"), resultado.getString("dianome"));
                System.out.println(dia.toString());
                Monitor monitor = new Monitor(resultado.getString("moncpf"), resultado.getString("monnome"), materia);
                monitoria = new Monitoria(resultado.getInt("miaid"), resultado.getInt("miavagas"),
                                                    true, materia, monitor, dia, hora, sala);
            }
            return monitoria;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
     
    public boolean inserirMonitoria(Monitoria monitoria){
        String sql = "CALL sp_inserirmonitoria(?, ?, ?)";        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, monitoria.getSala().getId());
            ps.setInt(2, monitoria.getDia().getId());
            ps.setString(3, monitoria.getHora().getHoraInicio());
            ps.execute();
            
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
    public Vector<Monitoria> consultarMonitoriaCoord(String str){
        try{
            String sql = "CALL sp_consultamonitoriascoord(?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, "%"+str+"%");
            ResultSet resultado = instrucao.executeQuery();
            Vector<Monitoria> monitorias = new Vector<>();
            while(resultado.next()){
                Sala sala = new Sala(resultado.getInt("salid"), resultado.getString("salnome"));
                Horario hora = new Horario(resultado.getString("horhora"));
                Materia materia = new Materia(resultado.getInt("matid"), resultado.getString("matnome"));
                DiaDaSemana dia = new DiaDaSemana(resultado.getInt("diaid"), resultado.getString("dianome"));
                Monitor monitor = new Monitor(resultado.getString("moncpf"), resultado.getString("monnome"), materia);
                Monitoria monitoria = new Monitoria(resultado.getInt("miaid"), resultado.getInt("miavagas"),
                                                    materia, monitor, dia, hora, sala);
                
                monitorias.add(monitoria);
            }
            return monitorias;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public Vector<Monitoria> consultarMonitoriasDisponiveis(String str, Aluno a){
        try{
            String sql = "CALL sp_consultamonitoriasdisponiveis(?, ?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, str);
            instrucao.setString(2, a.getCpf());
            ResultSet resultado = instrucao.executeQuery();
            Vector<Monitoria> monitorias = new Vector<>();
            while(resultado.next()){
                Sala sala = new Sala(resultado.getInt("salid"), resultado.getString("salnome"));
                Horario hora = new Horario(resultado.getString("horhora"));
                Materia materia = new Materia(resultado.getInt("matid"), resultado.getString("matnome"));
                DiaDaSemana dia = new DiaDaSemana(resultado.getInt("diaid"), resultado.getString("dianome"));
                Monitor monitor = new Monitor(resultado.getString("moncpf"), resultado.getString("monnome"), materia);
                Monitoria monitoria = new Monitoria(resultado.getInt("miaid"), resultado.getInt("miavagas"),
                                                    false, materia, monitor, dia, hora, sala);
                
                monitorias.add(monitoria);
            }
            return monitorias;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public Vector<Monitoria> consultarMonitoriasInscrito(String str, Aluno a){
        try{
            String sql = "CALL sp_consultamonitoriasinscrito(?, ?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, str);
            instrucao.setString(2, a.getCpf());
            ResultSet resultado = instrucao.executeQuery();
            Vector<Monitoria> monitorias = new Vector<>();
            while(resultado.next()){
                Sala sala = new Sala(resultado.getInt("salid"), resultado.getString("salnome"));
                Horario hora = new Horario(resultado.getString("horhora"));
                Materia materia = new Materia(resultado.getInt("matid"), resultado.getString("matnome"));
                DiaDaSemana dia = new DiaDaSemana(resultado.getInt("diaid"), resultado.getString("dianome"));
                Monitor monitor = new Monitor(resultado.getString("moncpf"), resultado.getString("monnome"), materia);
                Monitoria monitoria = new Monitoria(resultado.getInt("miaid"), resultado.getInt("miavagas"),
                                                    true, materia, monitor, dia, hora, sala);
                
                monitorias.add(monitoria);
            }
            return monitorias;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public Vector<Monitoria> consultarMonitoriasLivre(String str){
        try{
            String sql = "CALL sp_consultamonitoriaslivres(?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, str);
            ResultSet resultado = instrucao.executeQuery();
            Vector<Monitoria> monitorias = new Vector<>();
            while(resultado.next()){
                Sala sala = new Sala(resultado.getInt("salid"), resultado.getString("salnome"));
                Horario hora = new Horario(resultado.getString("horhora"));
                Materia materia = new Materia(resultado.getInt("matid"), resultado.getString("matnome"));
                DiaDaSemana dia = new DiaDaSemana(resultado.getInt("diaid"), resultado.getString("dianome"));
                Monitor monitor = new Monitor(resultado.getString("moncpf"), resultado.getString("monnome"), materia);
                Monitoria monitoria = new Monitoria(resultado.getInt("miaid"), resultado.getInt("miavagas"),
                                                    false, materia, monitor, dia, hora, sala);
                
                monitorias.add(monitoria);
            }
            return monitorias;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public Vector<Monitoria> consultarMonitoriasMonitor(String str, Monitor m){
        try{
            String sql = "CALL sp_consultamonitoriasmonitor(?,?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, str);
            instrucao.setString(2, m.getCpf());
            ResultSet resultado = instrucao.executeQuery();
            Vector<Monitoria> monitorias = new Vector<>();
            while(resultado.next()){
                Sala sala = new Sala(resultado.getInt("salid"), resultado.getString("salnome"));
                Horario hora = new Horario(resultado.getString("horhora"));
                Materia materia = new Materia(resultado.getInt("matid"), resultado.getString("matnome"));
                DiaDaSemana dia = new DiaDaSemana(resultado.getInt("diaid"), resultado.getString("dianome"));
                Monitor monitor = new Monitor(resultado.getString("moncpf"), resultado.getString("monnome"), materia);
                Monitoria monitoria = new Monitoria(resultado.getInt("miaid"), resultado.getInt("miavagas"),
                                                    true, materia, monitor, dia, hora, sala);
                
                monitorias.add(monitoria);
            }
            return monitorias;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public void acaoSalvarDoMonitor(List<Monitoria> mia, Monitor mon){
        try{
            PreparedStatement instrucao;
            for(Monitoria monitoria: mia ){
                String sql = "CALL sp_monitorcheckboxmarcado(?,?)";
                instrucao=connection.prepareStatement(sql);
                instrucao.setInt(1, monitoria.getId());
                instrucao.setString(2, mon.getCpf());
                instrucao.execute();
            }
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public void acaoAlterarDoMonitor(List<Monitoria> mia, Monitor mon){
        try{
            PreparedStatement instrucao;
            for(Monitoria monitoria: mia ){
                String sql = "CALL sp_monitorcheckboxdesmarcado(?,?)";
                instrucao=connection.prepareStatement(sql);
                instrucao.setInt(1, monitoria.getId());
                instrucao.setString(2, mon.getCpf());
                instrucao.execute();
            }
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public int numeroDeMonitorias(Monitor m){
        int cont = 0;
        try{
            String sql = "SELECT f_numerodemonitorias(?) AS resp";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, m.getCpf());
            ResultSet resultado = instrucao.executeQuery();
            while(resultado.next()){
               cont = resultado.getInt("resp");
            }
            return cont;
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return cont;
    }
}