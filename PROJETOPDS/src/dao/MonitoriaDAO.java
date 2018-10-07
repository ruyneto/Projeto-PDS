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
                    "INNER JOIN materia ON matid = miamatid "+
                    "INNER JOIN monitor ON moncpf = miamoncpf "+
                    "WHERE matnome LIKE ? "+
                    "order by diaid asc, matnome asc, horhora asc";
        
        String sql2 = "SELECT * FROM monitoria "+
                    "INNER JOIN sala ON salid = miasalid "+
                    "INNER JOIN horario ON horhora = miahorhora "+
                    "INNER JOIN diadasemana ON diaid = miadiaid "+
                    "INNER JOIN materia ON matid = miamatid "+
                    "INNER JOIN monitor ON moncpf = miamoncpf "+
                    "WHERE matnome LIKE ? AND matid != 1 "+
                    "order by diaid asc, matnome asc, horhora asc";
        try{
            PreparedStatement instrucao = connection.prepareStatement((opc==1)?sql:sql2);
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
                                                    materia, monitor, dia, hora, sala);
                
                monitorias.add(monitoria);
            }
            return monitorias;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
}