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

/**
 *
 * @author sandr
 * @Adapted by izaltinoNeto
 */
public class DiaDAO {
    private Connection connection;
    
    public DiaDAO(){
        this.connection = FabricaConexao.getConnection();
    }
    
    public Vector<DiaDaSemana> consultarDia(String str){
        try{
            String sql = "SELECT * FROM diadasemana";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            ResultSet resultado = instrucao.executeQuery();
            Vector<DiaDaSemana> dias = new Vector<>();
            while(resultado.next()){
                DiaDaSemana dia = new DiaDaSemana(resultado.getInt("diaid"), resultado.getString("dianome"));
                dias.add(dia);
            }
            return dias;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
        }
    }

    public Vector<Vector> diadasemanamaisrequisitados(){
     try{
            String sql = "CALL sp_diadasemanamaisrequisitados()";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            ResultSet resultado = instrucao.executeQuery();
            Vector<Vector> dias = new Vector<>();
            while(resultado.next()){
                Vector linha = new Vector();
                DiaDaSemana dia = new DiaDaSemana(resultado.getInt("diaid"), resultado.getString("dianome"));
                linha.add(dia);
                linha.add(resultado.getInt("numero de inscricoes"));
                
                dias.add(linha);
            }
            return dias;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
        }
    }
}
