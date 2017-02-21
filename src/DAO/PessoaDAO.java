package DAO;

import entidade.Pessoa;
import util.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author denilson
 */
public abstract class PessoaDAO {

    private static Conexao conexao;

    /**
     *
     */
    protected static int idGerado;

    public static int inserir(Pessoa pessoa) {

        PessoaDAO.conexao = Conexao.getInstancia();
        boolean retorno = false;
        int autoIncrement = 0;
        String sqlPessoa = "INSERT INTO pessoa (nome, email, id_endereco)"
                + "VALUES (?, ?,?);";

        PessoaDAO.conexao.prepararAI(sqlPessoa);

        try {
            PessoaDAO.conexao.getPs().setString(1, pessoa.getNome());
            PessoaDAO.conexao.getPs().setString(2, pessoa.getEmail());
            PessoaDAO.conexao.getPs().setInt(3, pessoa.getEndereco().getIdEndereco());

            retorno = PessoaDAO.conexao.executeUpdate();

            if (retorno) {
                autoIncrement = PessoaDAO.conexao.getAutoIncrement();
            }

        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return autoIncrement;
    }

    public static Pessoa buscarPessoa(int id) {
        Pessoa pessoaEntidade = new Pessoa();
        PessoaDAO.conexao = Conexao.getInstancia();
        String sqlPessoa = "select * from pessoa WHERE id_pessoa=? ;";
        PessoaDAO.conexao.preparar(sqlPessoa);

        try {
            PessoaDAO.conexao.getPs().setInt(1, id);
            ResultSet resultado = PessoaDAO.conexao.executeQuery();

            if (resultado != null && resultado.next()) {
                pessoaEntidade.setNome(resultado.getString("nome"));
                pessoaEntidade.setIdPessoa(id);
                pessoaEntidade.setIdEndereco(EnderecoDAO.buscarEndereco(resultado.getInt("id_endereco")));
                pessoaEntidade.setEmail(resultado.getString("email"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pessoaEntidade;
    }

    public static ArrayList<Pessoa> listarPessoas() {
        ArrayList<Pessoa> arrayPessoas = new ArrayList<>();
        PessoaDAO.conexao = Conexao.getInstancia();
        String sqlPessoa = "select * from pessoa;";
        PessoaDAO.conexao.preparar(sqlPessoa);

        try {

            ResultSet resultado = PessoaDAO.conexao.executeQuery();

            while (resultado.next()) {
                Pessoa pessoaEntidade = new Pessoa();
                pessoaEntidade.setNome(resultado.getString("nome"));
                pessoaEntidade.setIdPessoa(resultado.getInt("id_pessoa"));
                pessoaEntidade.setIdEndereco(EnderecoDAO.buscarEndereco(resultado.getInt("id_endereco")));
                pessoaEntidade.setEmail(resultado.getString("email"));

                arrayPessoas.add(pessoaEntidade);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrayPessoas;
    }

    public static boolean updatePessoa(Pessoa pessoaEntidade) {

        PessoaDAO.conexao = Conexao.getInstancia();
        boolean retorno = false;
        String SqlEndereco = "UPDATE  pessoa SET nome=?, email=? WHERE id_pessoa=?;";

        PessoaDAO.conexao.preparar(SqlEndereco);

        try {
            PessoaDAO.conexao.getPs().setString(1, pessoaEntidade.getNome());
            PessoaDAO.conexao.getPs().setString(2, pessoaEntidade.getEmail());
            PessoaDAO.conexao.getPs().setInt(3, pessoaEntidade.getIdPessoa());

            retorno = PessoaDAO.conexao.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public static boolean deletePessoa(int id) {

        PessoaDAO.conexao = Conexao.getInstancia();
        boolean retorno = false;
        String SqlEndereco = "DELETE FROM pessoa WHERE id_pessoa=?;";
        PessoaDAO.conexao.preparar(SqlEndereco);
        try {

            PessoaDAO.conexao.getPs().setInt(1, id);

            retorno = PessoaDAO.conexao.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

}
