
package DAO;

import entidade.Endereco;
import entidade.Pessoa;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Conexao;

/**
 *
 * @author denil
 */
public abstract class EnderecoDAO {

    private static Conexao conexao;
    protected static int idGerado;

    public static Endereco buscarEndereco(int id) {
        Endereco enderecoEntidade = new Endereco();
        conexao = Conexao.getInstancia();
        String sqlEndereco = "select * from endereco WHERE id_endereco=?;";
        EnderecoDAO.conexao.preparar(sqlEndereco);

        try {
            EnderecoDAO.conexao.getPs().setInt(1, id);
            ResultSet resultado = conexao.executeQuery();

            if (resultado != null && resultado.next()) {
                enderecoEntidade.setIdEndereco(id);
                enderecoEntidade.setLogradouro(resultado.getString("logradouro"));
                enderecoEntidade.setCep(resultado.getInt("cep"));
                enderecoEntidade.setIdCidade(CidadeDAO.buscarcidade(resultado.getInt("id_cidade")));
            
            }

        } catch (SQLException ex) {
            Logger.getLogger(EnderecoDAO.class.getName()).log(Level.SEVERE, "erro cadastrar endereço", ex);
        }

        return enderecoEntidade;

    }

    public static int inserirEndereco(Endereco enderecoEntidade) {
        conexao = Conexao.getInstancia();
        boolean retorno = false;
        int autoIncrement = 0;
        if (enderecoEntidade.getIdCidade().getIdCidade() == 0) {
            return 0;
        }
        String SqlEndereco = "INSERT INTO endereco (logradouro, id_cidade, cep)"
                + "	VALUES (?, ?, ?);";
        EnderecoDAO.conexao.prepararAI(SqlEndereco);

        try {
            EnderecoDAO.conexao.getPs().setString(1, enderecoEntidade.getLogradouro());
            EnderecoDAO.conexao.getPs().setInt(2, enderecoEntidade.getIdCidade().getIdCidade());
            EnderecoDAO.conexao.getPs().setInt(3, enderecoEntidade.getCep());

            retorno = EnderecoDAO.conexao.executeUpdate();
            if (retorno) {
                autoIncrement  = EnderecoDAO.conexao.getAutoIncrement();
            }

        } catch (SQLException ex) {
            Logger.getLogger(EnderecoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return autoIncrement;
    }

    public static boolean updateEndereco(Endereco enderecoEntidade) {
        conexao = Conexao.getInstancia();
        boolean retorno = false;
        String SqlEndereco = "UPDATE  endereco SET logradouro=?, id_cidade=?, cep=? WHERE id_endereco=?";

        EnderecoDAO.conexao.preparar(SqlEndereco);

        try {
            EnderecoDAO.conexao.getPs().setString(1, enderecoEntidade.getLogradouro());
            EnderecoDAO.conexao.getPs().setInt(2, enderecoEntidade.getIdCidade().getIdCidade());
            EnderecoDAO.conexao.getPs().setInt(3, enderecoEntidade.getCep());
            EnderecoDAO.conexao.getPs().setInt(4, enderecoEntidade.getIdEndereco());

            retorno = EnderecoDAO.conexao.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(EnderecoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public static boolean deleteEndereco(int id) {

        EnderecoDAO.conexao = Conexao.getInstancia();
        boolean retorno = false;
        String SqlEndereco = "DELETE FROM endereco WHERE id_endereco=?";
        EnderecoDAO.conexao.preparar(SqlEndereco);
        try {

            EnderecoDAO.conexao.getPs().setInt(1, id);

            retorno = EnderecoDAO.conexao.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EnderecoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }
}
