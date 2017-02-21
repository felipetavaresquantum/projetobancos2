package DAO;

import entidade.Contato;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Conexao;

/**
 *
 * @author denil
 */
public abstract class ContatoDAO {

    private static Conexao conexao;
    protected static int idGerado;

    public static Contato buscarContato(int id) {
        Contato contatoEntidade = new Contato();
        conexao = Conexao.getInstancia();
        String sqlContato = "select * from contato WHERE id_contato=?;";
        ContatoDAO.conexao.preparar(sqlContato);

        try {
            ContatoDAO.conexao.getPs().setInt(1, id);
            ResultSet resultado = conexao.executeQuery();

            if (resultado != null && resultado.next()) {
                contatoEntidade.setIdContato(id);
                contatoEntidade.setDdd(resultado.getString("ddd"));
                contatoEntidade.setFone(resultado.getString("fone"));
                contatoEntidade.setIdPessoa(PessoaDAO.buscarPessoa(resultado.getInt("id_pessoa")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ContatoDAO.class.getName()).log(Level.SEVERE, "erro cadastrar contato", ex);
        }

        return contatoEntidade;
    }
    
     public static Contato buscarContatoByPessoa(int id) {
        Contato contatoEntidade = new Contato();
        conexao = Conexao.getInstancia();
        String sqlContato = "select * from contato WHERE id_pessoa=?;";
        ContatoDAO.conexao.preparar(sqlContato);

        try {
            ContatoDAO.conexao.getPs().setInt(1, id);
            ResultSet resultado = conexao.executeQuery();

            if (resultado != null && resultado.next()) {
                contatoEntidade.setIdContato(resultado.getInt("id_contato"));
                contatoEntidade.setDdd(resultado.getString("ddd"));
                contatoEntidade.setFone(resultado.getString("fone"));
                contatoEntidade.setIdPessoa(PessoaDAO.buscarPessoa(id));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ContatoDAO.class.getName()).log(Level.SEVERE, "erro cadastrar contato", ex);
        }

        return contatoEntidade;
    }

    public static boolean inserirContato(Contato contatoEntidade) {
        conexao = Conexao.getInstancia();
        boolean retorno = false;
        int autoIncrement = 0;
        if (contatoEntidade.getIdPessoa().getIdPessoa() == 0) {
            return false;
        }
        String SqlContato = "INSERT INTO contato (ddd, fone, id_pessoa)"
                + "	VALUES (?, ?, ?)";
        ContatoDAO.conexao.prepararAI(SqlContato);

        try {
            ContatoDAO.conexao.getPs().setString(1, contatoEntidade.getDdd());
            ContatoDAO.conexao.getPs().setString(2, contatoEntidade.getFone());
            ContatoDAO.conexao.getPs().setInt(3, contatoEntidade.getIdPessoa().getIdPessoa());

            retorno = ContatoDAO.conexao.executeUpdate();
            if (retorno) {
                autoIncrement = ContatoDAO.conexao.getAutoIncrement();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ContatoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }
    
    public static boolean updateContato(Contato contatoEntidade) {
        conexao = Conexao.getInstancia();
        boolean retorno = false;
        String SqlContato = "UPDATE  contato SET ddd=?, id_pessoa=?, fone=? WHERE id_contato=?;";

        ContatoDAO.conexao.preparar(SqlContato);

        try {
            ContatoDAO.conexao.getPs().setString(1, contatoEntidade.getDdd());
            ContatoDAO.conexao.getPs().setInt(2, contatoEntidade.getIdPessoa().getIdPessoa());
            ContatoDAO.conexao.getPs().setString(3, contatoEntidade.getFone());
            ContatoDAO.conexao.getPs().setInt(4, contatoEntidade.getIdContato());

            retorno = ContatoDAO.conexao.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ContatoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }
    
    public static boolean deleteContato(int id) {

        ContatoDAO.conexao = Conexao.getInstancia();
        boolean retorno = false;
        String SqlContato = "DELETE FROM contato WHERE id_contato=?;";
        ContatoDAO.conexao.preparar(SqlContato);
        try {

            ContatoDAO.conexao.getPs().setInt(1, id);

            retorno = ContatoDAO.conexao.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ContatoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }
}
