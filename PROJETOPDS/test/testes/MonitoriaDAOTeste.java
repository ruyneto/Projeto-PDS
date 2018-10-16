/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import connection.FabricaConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class MonitoriaDAOTeste {
     private Connection connection;
     
     public MonitoriaDAOTeste(){
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
     
    public boolean inserirMonitoria(Monitoria monitoria){
        String sql = "INSERT INTO monitoria (miasalid, miamatid, miadiaid, miahorhora) VALUES (?, ?, ?, ?)";        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, monitoria.getSala().getId());
            ps.setInt(2, monitoria.getMateria().getId());
            ps.setInt(3, monitoria.getDia().getId());
            ps.setString(4, monitoria.getHora().getHoraInicio());
            ps.execute();
            
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
    public Vector<Monitoria> consultarMonitoria(String str, int opc){
        String sql = "SELECT * FROM monitoria "+
                    "INNER JOIN sala ON salid = miasalid "+
                    "INNER JOIN horario ON horhora = miahorhora "+
                    "INNER JOIN diadasemana ON diaid = miadiaid "+
                    "LEFT OUTER JOIN materia ON matid = miamatid "+
                    "LEFT OUTER JOIN monitor ON moncpf = miamoncpf "+
                    "WHERE salnome LIKE ? "+
                    "order by diaid asc, matnome asc, horhora asc";
        
        String sql2 = "SELECT * FROM monitoria "+
                    "INNER JOIN sala ON salid = miasalid "+
                    "INNER JOIN horario ON horhora = miahorhora "+
                    "INNER JOIN diadasemana ON diaid = miadiaid "+
                    "INNER JOIN materia ON matid = miamatid "+
                    "INNER JOIN monitor ON moncpf = miamoncpf "+
                    "LEFT OUTER JOIN inscricao ON miaid = insmiaid "+
                    "WHERE matnome LIKE ? AND matid != 1 AND miavagas>0 "+
                    "order by diaid asc, matnome asc, horhora asc";
        
        String sql3 = "SELECT * FROM monitoria "+
                    "INNER JOIN sala ON salid = miasalid "+
                    "INNER JOIN horario ON horhora = miahorhora "+
                    "INNER JOIN diadasemana ON diaid = miadiaid "+
                    "INNER JOIN materia ON matid = miamatid "+
                    "INNER JOIN monitor ON moncpf = miamoncpf "+
                    "INNER JOIN inscricao ON miaid = insmiaid "+
                    "WHERE matnome LIKE ? AND matid != 1 "+
                    "order by diaid asc, matnome asc, horhora asc";
        
        String sql4 = "SELECT * FROM monitoria "+
                    "INNER JOIN sala ON salid = miasalid "+
                    "INNER JOIN horario ON horhora = miahorhora "+
                    "INNER JOIN diadasemana ON diaid = miadiaid "+
                    "INNER JOIN materia ON matid = miamatid "+
                    "WHERE salnome LIKE ? AND matid = 1 "+
                    "order by diaid asc, matnome asc, horhora asc";
        try{
            PreparedStatement instrucao;
            switch(opc){
                case 1:
                    instrucao = connection.prepareStatement(sql);
                break;
                case 2:
                    instrucao = connection.prepareStatement(sql2);
                break;
                case 3:
                    instrucao = connection.prepareStatement(sql3);
                break;
                default:
                    instrucao = connection.prepareStatement(sql);
                break;
                
            }
            instrucao.setString(1, "%"+str+"%");
            ResultSet resultado = instrucao.executeQuery();
            Vector<Monitoria> monitorias = new Vector<>();
            while(resultado.next()){
                Sala sala = new Sala(resultado.getInt("salid"), resultado.getString("salnome"));
                Horario hora = new Horario(resultado.getString("horhora"));
                Materia materia = new Materia(resultado.getInt("matid"), resultado.getString("matnome"));
                DiaDaSemana dia = new DiaDaSemana(resultado.getInt("diaid"), resultado.getString("dianome"));
                Monitor monitor = new Monitor(resultado.getString("moncpf"), resultado.getString("monnome"));
                Monitoria monitoria = new Monitoria(resultado.getInt("miaid"), resultado.getInt("miavagas"),
                                                    false, materia, monitor, dia, hora, sala);
                
                monitorias.add(monitoria);
            }
            return monitorias;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public boolean atualizarMonitoria(Monitoria monitoria, Monitor monitor){
        String sql = "UPDATE monitoria SET miamoncpf = ?";        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, monitoria.getSala().getId());
            ps.setInt(2, monitoria.getMateria().getId());
            ps.setInt(3, monitoria.getDia().getId());
            ps.setString(4, monitoria.getHora().getHoraInicio());
            ps.execute();
            
            connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
}