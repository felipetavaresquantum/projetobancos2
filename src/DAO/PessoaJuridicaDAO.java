
package DAO;

import entidade.Pessoa;
import entidade.PessoaJuridica;
import java.sql.Date;
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
public abstract class PessoaJuridicaDAO {

    private static Conexao conexao;

    protected static int idGerado;

    private static boolean inserir(PessoaJuridica pessoa) {

        PessoaJuridicaDAO.conexao = Conexao.getInstancia();
        boolean retorno = false;
        String sqlPessoa = "INSERT INTO pessoa_juridica (cnpj, data_abertura, id_pessoa, insc_estadual)"
                + "VALUES (?, ?, ?, ?);";

        PessoaJuridicaDAO.conexao.prepararAI(sqlPessoa);

        try {
            PessoaJuridicaDAO.conexao.getPs().setString(1, pessoa.getCnpj());
            PessoaJuridicaDAO.conexao.getPs().setString(2, pessoa.getDataAbertura());
            PessoaJuridicaDAO.conexao.getPs().setInt(3, pessoa.getIdPessoa());
            PessoaJuridicaDAO.conexao.getPs().setString(4, pessoa.getInscricaoEstadual());

            retorno = PessoaJuridicaDAO.conexao.executeUpdate();

            if (retorno) {
                PessoaJuridicaDAO.idGerado = PessoaJuridicaDAO.conexao.getAutoIncrement();
            }

        } catch (SQLException ex) {
            Logger.getLogger(PessoaJuridicaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public static boolean cadastrarPesoaJuridica(PessoaJuridica pessoa) {
        boolean retorno = false;
        PessoaJuridicaDAO.conexao = Conexao.getInstancia();
        PessoaJuridicaDAO.conexao.iniciaTransacao();
        int idEndereco = EnderecoDAO.inserirEndereco(pessoa.getEndereco());
        if (idEndereco > 0) {
            pessoa.getEndereco().setIdEndereco(idEndereco);
            int idPessoa = PessoaDAO.inserir(pessoa);
            if (idPessoa > 0) {
                pessoa.setIdPessoa(idPessoa);
                retorno = PessoaJuridicaDAO.inserir(pessoa);
                if (retorno) {
                    pessoa.getContato().setIdPessoa(new Pessoa(idPessoa));
                    retorno = ContatoDAO.inserirContato(pessoa.getContato());
                }
            }

        }

        PessoaJuridicaDAO.conexao.fecharTransacao(retorno);
        return retorno;

    }

    public static PessoaJuridica buscarPessoaJurudica(int id) {
        PessoaJuridica pessoaEntidade = new PessoaJuridica();
        PessoaJuridicaDAO.conexao = Conexao.getInstancia();
        String sqlPessoa = "select * from pessoa_juridica WHERE id_pessoa_juridica=? ;";
        PessoaJuridicaDAO.conexao.preparar(sqlPessoa);

        try {
            PessoaJuridicaDAO.conexao.getPs().setInt(1, id);
            ResultSet resultado = PessoaJuridicaDAO.conexao.executeQuery();

            if (resultado != null && resultado.next()) {
                Pessoa pessoa = PessoaDAO.buscarPessoa(resultado.getInt("id_pessoa"));
                pessoaEntidade.setCnpj(resultado.getString("cnpj"));
                pessoaEntidade.setDataAbertura(resultado.getString("data_abertura"));
                pessoaEntidade.setInscricaoEstadual(resultado.getString("insc_estadual"));
                pessoaEntidade.setIdEndereco(pessoa.getEndereco());
                pessoaEntidade.setNome(pessoa.getNome());
                pessoaEntidade.setEmail(pessoa.getEmail());
                pessoaEntidade.setIdPessoa(resultado.getInt("id_pessoa"));
                pessoaEntidade.setIdPessoaJuridica(id);
                pessoaEntidade.setContato(ContatoDAO.buscarContatoByPessoa(resultado.getInt("id_pessoa")));


            }

        } catch (SQLException ex) {
            Logger.getLogger(PessoaJuridicaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pessoaEntidade;
    }

    public static ArrayList<PessoaJuridica> listarPessoas() {
        ArrayList<PessoaJuridica> arrayPessoas = new ArrayList<>();
        PessoaJuridicaDAO.conexao = Conexao.getInstancia();
        String sqlPessoa = "select * from pessoa_juridica;";
        PessoaJuridicaDAO.conexao.preparar(sqlPessoa);

        try {

            ResultSet resultado = PessoaJuridicaDAO.conexao.executeQuery();

            while (resultado.next()) {
                PessoaJuridica pessoaEntidade = new PessoaJuridica();
                Pessoa pessoa = PessoaDAO.buscarPessoa(resultado.getInt("id_pessoa"));
                pessoaEntidade.setCnpj(resultado.getString("cnpj"));
                pessoaEntidade.setInscricaoEstadual(resultado.getString("insc_estadual"));
                pessoaEntidade.setDataAbertura(resultado.getString("data_abertura"));
                pessoaEntidade.setIdEndereco(pessoa.getEndereco());
                pessoaEntidade.setNome(pessoa.getNome());
                pessoaEntidade.setEmail(pessoa.getEmail());

                arrayPessoas.add(pessoaEntidade);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PessoaJuridicaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrayPessoas;
    }

    private static boolean updatePessoa(PessoaJuridica pessoaEntidade) {

        PessoaJuridicaDAO.conexao = Conexao.getInstancia();
        boolean retorno = false;
        String SqlEndereco = "UPDATE  pessoa_juridica SET cnpj =?, data_abertura=?, insc_estadual =? WHERE id_pessoa_juridica=?;";

        PessoaJuridicaDAO.conexao.preparar(SqlEndereco);

        try {
            PessoaJuridicaDAO.conexao.getPs().setString(1, pessoaEntidade.getCnpj());
            PessoaJuridicaDAO.conexao.getPs().setString(2, pessoaEntidade.getDataAbertura());
            PessoaJuridicaDAO.conexao.getPs().setString(3, pessoaEntidade.getInscricaoEstadual());
            PessoaJuridicaDAO.conexao.getPs().setInt(4, pessoaEntidade.getIdPessoaJuridica());

            retorno = PessoaJuridicaDAO.conexao.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PessoaJuridicaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public static boolean atualizarPessoa(PessoaJuridica pj) {
        PessoaJuridicaDAO.conexao = Conexao.getInstancia();
        PessoaJuridicaDAO.conexao.iniciaTransacao();
        boolean retorno = false;

        retorno = EnderecoDAO.updateEndereco(pj.getEndereco());
        if (retorno) {
            retorno = PessoaDAO.updatePessoa(pj);
            if (retorno) {
                retorno = PessoaJuridicaDAO.updatePessoa(pj);

                if (retorno) {

                    retorno = ContatoDAO.updateContato(pj.getContato());
                }
            }
        }

        PessoaJuridicaDAO.conexao.fecharTransacao(retorno);
        return retorno;
    }

    private static boolean deletePessoa(int id) {

        PessoaJuridicaDAO.conexao = Conexao.getInstancia();
        boolean retorno = false;
        String SqlEndereco = "DELETE FROM pessoa_juridica WHERE id_pessoa_juridica=?;";
        PessoaJuridicaDAO.conexao.preparar(SqlEndereco);
        try {

            PessoaJuridicaDAO.conexao.getPs().setInt(1, id);

            retorno = PessoaJuridicaDAO.conexao.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PessoaJuridicaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public static boolean apagarPessoa(PessoaJuridica pj) {
        PessoaJuridicaDAO.conexao = Conexao.getInstancia();
        PessoaJuridicaDAO.conexao.iniciaTransacao();
        boolean retorno = false;

        retorno = PessoaJuridicaDAO.deletePessoa(pj.getIdPessoaJuridica());
        if (retorno) {
            retorno = ContatoDAO.deleteContato(pj.getContato().getIdContato());
            if (retorno) {
                retorno = PessoaDAO.deletePessoa(pj.getIdPessoa());
                if (retorno) {
                    retorno = EnderecoDAO.deleteEndereco(pj.getEndereco().getIdEndereco());
                    
                }
            }
        }

        PessoaJuridicaDAO.conexao.fecharTransacao(retorno);
        return retorno;
    }

}
