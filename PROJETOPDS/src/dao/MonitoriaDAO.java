/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.DiaDaSemana;
import model.Horario;
import model.Monitoria;

/**
 *
 * @author Izaltino
 */
public class MonitoriaDAO {
     private Connection connection;
     
    public boolean inserirMonitoria(Monitoria monitoria){
        String sql = "INSERT INTO monitoria (miamoncpf, miasalid, miamatid, miahorinicio) VALUES (?, ?, ?)";        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(0, monitoria.getSala().getId());
            ps.setInt(1, monitoria.getMateria().getId());
            ps.setString(2, monitoria.getHora().getHoraInicio());
            ps.execute();
            
            connection.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Vector<Monitoria> consultarProduto(String str){
        try{
            String sql = "select * from monitoria"+
                    "inner join sala on salid = miasalid"+
                    "inner join horario on horinicio = miahorinicio"+
                    "inner join materia on matid = miamatid"+
                    "where matnome like ?";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setString(1, "%" + str + "%");
            ResultSet resultado = instrucao.executeQuery();
            Vector<Monitoria> monitorias = new Vector<>();
            while(resultado.next()){
                Monitoria monitoria = new Monitoria();
                monitoria.setId(resultado.getInt("monid"));
                
                DiaDaSemana dia = new DiaDaSemana(resultado.getInt("diaid"), resultado.getString("dianome"));
                Horario hora = new 
                monitoria.se
                produtos.add(produto);
            }
            return produtos;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
