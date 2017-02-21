/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entidade.Estado;
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
public abstract class EstadoDAO {

    private static Conexao conexao;
    public static int idGerado;

    public static ArrayList<Estado> getListaEstados() {
        EstadoDAO.conexao = Conexao.getInstancia();
        ArrayList<Estado> listaDeEstados = new ArrayList<>();

        try {
            listaDeEstados = new ArrayList<>();
            String query = "SELECT * FROM estado ORDER BY id_estado";
            EstadoDAO.conexao.preparar(query);
            ResultSet resultado = EstadoDAO.conexao.executeQuery();

            while (resultado.next()) {
                String uf = resultado.getString("uf");
                String descricao = resultado.getString("estado");
                int id = resultado.getInt("id_estado");

                listaDeEstados.add(new Estado(id, descricao, uf));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao obter dados: " + ex.toString());
        }

        return listaDeEstados;
    }

    public static Estado buscarEstado(int id) {
        Estado estadoEntidade = new Estado();
        try {
            EstadoDAO.conexao = Conexao.getInstancia();

            String query = "select * from estado WHERE id_estado=?;";
            EstadoDAO.conexao.preparar(query);

            EstadoDAO.conexao.getPs().setInt(1, id);

            ResultSet resultado = EstadoDAO.conexao.executeQuery();

            if (resultado != null && resultado.next()) {

                estadoEntidade.setEstado(resultado.getString("estado"));
                estadoEntidade.setUf(resultado.getString("uf"));
                estadoEntidade.setIdEstado(id);

            }
        } catch (SQLException ex) {
            System.err.println("Erro ao obter dados: " + ex.toString());
        }
        return estadoEntidade;
    }

    public static boolean inserir(Estado estadoEntidade) {
        conexao = Conexao.getInstancia();
        boolean retorno = false;
        int autoIncrement = 0;
        String sql = "INSERT INTO estado (uf, estado) "
                + " VALUES (?, ?);";
        conexao.prepararAI(sql);
        try {
            conexao.getPs().setString(1, estadoEntidade.getUf());
            conexao.getPs().setString(2, estadoEntidade.getEstado());

            retorno = conexao.executeUpdate();
            if (retorno) {
                // System.err.println(conexao.getAutoIncrement());
                autoIncrement = conexao.getAutoIncrement();
            }

        } catch (SQLException ex) {
            Logger.getLogger(EnderecoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;

    }

    public static boolean update(Estado estadoEntidade) {
        conexao = Conexao.getInstancia();
        boolean retorno = false;
        String sql = "UPDATE estado SET estado=?, set uf=? WHERE id_estado=?; ";
        conexao.preparar(sql);
        try {
            conexao.getPs().setString(1, estadoEntidade.getEstado());
            conexao.getPs().setString(2, estadoEntidade.getUf());
            conexao.getPs().setInt(3, estadoEntidade.getIdEstado());

            retorno = conexao.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EstadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public static boolean deleteEstado(int id) {
        conexao = Conexao.getInstancia();
        boolean retorno = false;
        String sql = "DELETE FROM estado  WHERE id_estado=?;";
        conexao.preparar(sql);

        try {

            conexao.getPs().setInt(1, id);

            retorno = conexao.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(EstadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

}
