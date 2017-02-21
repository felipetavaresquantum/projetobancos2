/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entidade.Cidade;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Conexao;

/**
 *
 * @author denil
 */
public abstract class CidadeDAO {

    private static Conexao conexao;
    protected static int idGerado;

    public static ArrayList<Cidade> ListaCidades(int id) {
        conexao = Conexao.getInstancia();
        ArrayList<Cidade> listaCidades = new ArrayList<>();
        try {

            String query = "SELECT * FROM cidade WHERE id_estado= ? ORDER BY id_cidade;";
            conexao.preparar(query);
            conexao.getPs().setInt(1, id);
            ResultSet resultado = conexao.executeQuery();

            while (resultado.next()) {

                Cidade cidade = new Cidade();
                cidade.setCidade(resultado.getString("cidade"));
                cidade.setIdCidade(resultado.getInt("id_cidade"));
                cidade.setIdEstado(EstadoDAO.buscarEstado(resultado.getInt("id_estado")));

                listaCidades.add(cidade);
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao obter dados: " + ex.toString());
        }

        return listaCidades;
    }


    public static Cidade buscarcidade(int id) {
        Cidade cidade = new Cidade();
        try {
            conexao = Conexao.getInstancia();

            String query = "SELECT * FROM cidade WHERE id_cidade =?;";
            conexao.preparar(query);
            conexao.getPs().setInt(1, id);
            ResultSet resultado = conexao.executeQuery();

            if (resultado != null && resultado.next()) {

                cidade.setCidade(resultado.getString("cidade"));
                cidade.setIdCidade(resultado.getInt("id_cidade"));
                cidade.setIdEstado(EstadoDAO.buscarEstado(resultado.getInt("id_estado")));

            }

        } catch (SQLException ex) {
            System.err.println("Erro ao obter dados: " + ex.toString());
        }

        return cidade;
    }
    
        public static Cidade buscarcidade(String  nome) {
        Cidade cidade = new Cidade();
        try {
            conexao = Conexao.getInstancia();

            String query = "SELECT * FROM cidade WHERE descricao like = ? order by cidade;";
            conexao.preparar(query);
            conexao.getPs().setString(1, "%"+nome.toUpperCase()+"%");
            ResultSet resultado = conexao.executeQuery();

            if (resultado != null && resultado.next()) {

                cidade.setCidade(resultado.getString("cidade"));
                cidade.setIdCidade(resultado.getInt("id_cidade"));
                cidade.setIdEstado(EstadoDAO.buscarEstado(resultado.getInt("id_estado")));

            }

        } catch (SQLException ex) {
            System.err.println("Erro ao obter dados: " + ex.toString());
        }

        return cidade;
    }

    public static boolean inserirCidade(Cidade cidadeEntidade) {

        conexao = Conexao.getInstancia();
        boolean retorno = false;
        String query = "INSERT INTO cidade (cidade, id_estado) "
                + "	VALUES (?, ?);";

        conexao.preparar(query);
        try {
            conexao.getPs().setString(1, cidadeEntidade.getCidade());
            conexao.getPs().setInt(2, cidadeEntidade.getIdEstado().getIdEstado());

            retorno = conexao.executeUpdate();
             if (retorno) {
                CidadeDAO.idGerado = CidadeDAO.conexao.getAutoIncrement();
            }

        } catch (SQLException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

   
    public static boolean updateCidade(Cidade cidadeEntidade) {
        conexao = Conexao.getInstancia();
        boolean retorno = false;
        String query = "UPDATE cidade SET cidade=?, id_estado=?  WHERE id=?;";

        conexao.preparar(query);
        try {

            conexao.getPs().setString(1, cidadeEntidade.getCidade());
            conexao.getPs().setInt(2, cidadeEntidade.getIdEstado().getIdEstado());
            conexao.getPs().setInt(3, cidadeEntidade.getIdCidade());

            retorno = conexao.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

   
    public static boolean deleteCidade(int idCidade) {
        conexao = Conexao.getInstancia();
        boolean retorno = false;
        String sql = "DELETE FROM cidade WHERE id_cidade=? ;";
        conexao.preparar(sql);

        try {

            conexao.getPs().setInt(2, idCidade);
            retorno = conexao.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

}
