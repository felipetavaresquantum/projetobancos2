
package DAO;

import entidade.Pessoa;
import entidade.PessoaFisica;
import java.sql.Date;
import util.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author denilson
 */
public abstract class PessoaFisicaDAO  {

    private static Conexao conexao;

   
    protected static int idGerado;

    private static boolean inserir(PessoaFisica pessoa) {

        PessoaFisicaDAO.conexao = Conexao.getInstancia();
        boolean retorno = false;
        String sqlPessoa = "INSERT INTO pessoa_fisica (rg, cpf, data_nascimento, id_pessoa)"
                + "VALUES (?, ?, ?, ?);";

        PessoaFisicaDAO.conexao.prepararAI(sqlPessoa);

        try {
            PessoaFisicaDAO.conexao.getPs().setString(1, pessoa.getRg());
            PessoaFisicaDAO.conexao.getPs().setString(2, pessoa.getCpf());
            PessoaFisicaDAO.conexao.getPs().setString(3, pessoa.getDataNascimento());
            PessoaFisicaDAO.conexao.getPs().setInt(4, pessoa.getIdPessoa());

            retorno = PessoaFisicaDAO.conexao.executeUpdate();

            if (retorno) {
                PessoaFisicaDAO.idGerado = PessoaFisicaDAO.conexao.getAutoIncrement();
            }

        } catch (SQLException ex) {
            Logger.getLogger(PessoaFisicaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public static boolean cadastrarPesoaFisica(PessoaFisica pessoa) {
        boolean retorno = false;
        PessoaFisicaDAO.conexao = Conexao.getInstancia();
        PessoaFisicaDAO.conexao.iniciaTransacao();
        int idEndereco = EnderecoDAO.inserirEndereco(pessoa.getEndereco());
        if (idEndereco > 0) {
            pessoa.getEndereco().setIdEndereco(idEndereco);
            int idPessoa = PessoaDAO.inserir(pessoa);
            if (idPessoa > 0) {
                pessoa.setIdPessoa(idPessoa);
                retorno = PessoaFisicaDAO.inserir(pessoa);
                if(retorno)
                {
                    pessoa.getContato().setIdPessoa(new Pessoa(idPessoa));
                   retorno = ContatoDAO.inserirContato(pessoa.getContato());
                }
            }
        }
        PessoaFisicaDAO.conexao.fecharTransacao(retorno);
        return retorno;
    }

    public static PessoaFisica buscarPessoaFisica(int id) {
        PessoaFisica pessoaEntidade = new PessoaFisica();
        PessoaFisicaDAO.conexao = Conexao.getInstancia();
        String sqlPessoa = "select * from pessoa_fisica WHERE id_pessoa_fisica=? ;";
        PessoaFisicaDAO.conexao.preparar(sqlPessoa);

        try {
            PessoaFisicaDAO.conexao.getPs().setInt(1, id);
            ResultSet resultado = PessoaFisicaDAO.conexao.executeQuery();

            if (resultado != null && resultado.next()) {
                Pessoa pessoa = PessoaDAO.buscarPessoa(resultado.getInt("id_pessoa"));
                pessoaEntidade.setRg(resultado.getString("rg"));
                pessoaEntidade.setCpf(resultado.getString("cpf"));
                pessoaEntidade.setDataNascimento(resultado.getString("data_nascimento"));
                pessoaEntidade.setIdEndereco(pessoa.getEndereco());
                pessoaEntidade.setNome(pessoa.getNome());
                pessoaEntidade.setEmail(pessoa.getEmail());
                pessoaEntidade.setIdPessoa(resultado.getInt("id_pessoa"));
                pessoaEntidade.setIdPessoaFisica(id);
                pessoaEntidade.setContato(ContatoDAO.buscarContatoByPessoa(resultado.getInt("id_pessoa")));

            }

        } catch (SQLException ex) {
            Logger.getLogger(PessoaFisicaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pessoaEntidade;
    }

    public static ArrayList<PessoaFisica> listarPessoas() {
        ArrayList<PessoaFisica> arrayPessoas = new ArrayList<>();
        PessoaFisicaDAO.conexao = Conexao.getInstancia();
        String sqlPessoa = "select * from pessoa_fisica;";
        PessoaFisicaDAO.conexao.preparar(sqlPessoa);

        try {

            ResultSet resultado = PessoaFisicaDAO.conexao.executeQuery();

            while (resultado.next()) {
                PessoaFisica pessoaEntidade = new PessoaFisica();
                Pessoa pessoa = PessoaDAO.buscarPessoa(resultado.getInt("id_pessoa"));
                pessoaEntidade.setRg(resultado.getString("rg"));
                pessoaEntidade.setCpf(resultado.getString("cpf"));
                pessoaEntidade.setDataNascimento(resultado.getString("data_nascimento"));
                pessoaEntidade.setIdEndereco(pessoa.getEndereco());
                pessoaEntidade.setNome(pessoa.getNome());
                pessoaEntidade.setEmail(pessoa.getEmail());

                arrayPessoas.add(pessoaEntidade);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PessoaFisicaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrayPessoas;
    }

    private static boolean updatePessoa(PessoaFisica pessoaEntidade) {

        PessoaFisicaDAO.conexao = Conexao.getInstancia();
        boolean retorno = false;
        String SqlEndereco = "UPDATE  pessoa_fisica SET rg=?, cpf=?, data_nascimento=? WHERE id_pessoa_fisica=?;";

        PessoaFisicaDAO.conexao.preparar(SqlEndereco);

        try {
            PessoaFisicaDAO.conexao.getPs().setString(1, pessoaEntidade.getRg());
            PessoaFisicaDAO.conexao.getPs().setString(2, pessoaEntidade.getCpf());
            PessoaFisicaDAO.conexao.getPs().setString(3, pessoaEntidade.getDataNascimento());
            PessoaFisicaDAO.conexao.getPs().setInt(4, pessoaEntidade.getIdPessoaFisica());

            retorno = PessoaFisicaDAO.conexao.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PessoaFisicaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public static boolean atualizarPessoa(PessoaFisica pf) {
        PessoaFisicaDAO.conexao = Conexao.getInstancia();
        PessoaFisicaDAO.conexao.iniciaTransacao();
        boolean retorno = false;

        retorno = EnderecoDAO.updateEndereco(pf.getEndereco());
        if (retorno) {
            retorno = PessoaDAO.updatePessoa(pf);
            if (retorno) {
                retorno = PessoaFisicaDAO.updatePessoa(pf);
                 if (retorno) {
                    retorno = ContatoDAO.updateContato(pf.getContato());
                }
            }
        }

        PessoaFisicaDAO.conexao.fecharTransacao(retorno);
        return retorno;
    }

    private static boolean deletePessoa(int id) {

        PessoaFisicaDAO.conexao = Conexao.getInstancia();
        boolean retorno = false;
        String SqlEndereco = "DELETE FROM pessoa_fisica WHERE id_pessoa_fisica=?;";
        PessoaFisicaDAO.conexao.preparar(SqlEndereco);
        try {

            PessoaFisicaDAO.conexao.getPs().setInt(1, id);

            retorno = PessoaFisicaDAO.conexao.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PessoaFisicaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public static boolean apagarPessoa(PessoaFisica pf) {
        PessoaFisicaDAO.conexao = Conexao.getInstancia();
        PessoaFisicaDAO.conexao.iniciaTransacao();
        boolean retorno = false;

        retorno = PessoaFisicaDAO.deletePessoa(pf.getIdPessoaFisica());
        if (retorno) {
            retorno = ContatoDAO.deleteContato(pf.getContato().getIdContato());
            if (retorno) {
                retorno = PessoaDAO.deletePessoa(pf.getIdPessoa());
                if (retorno) {
                     retorno = EnderecoDAO.deleteEndereco(pf.getEndereco().getIdEndereco());
                }
            }
        }

        PessoaFisicaDAO.conexao.fecharTransacao(retorno);
        return retorno;
    }
}
