package controles;

import dao.Conexao;
import utilitarios.Alerta;
import utilitarios.FuncoesDiversas;

public class CCadastro 
{
    public static Conexao ConPostgreSQL;
    
    public static boolean ConectarBanco()
    {
        try
        {
            ConPostgreSQL = new Conexao();
            ConPostgreSQL.Conectar(false);
            
            return true;
        }
        catch(Exception e)
        {
            Alerta.MostrarAlertaErro("Não foi possivel estabelecer uma conexão com o servidor. \n\nErro: " + 
            e.toString(), "Erro Conexão");
            
            return false;
        }
    }
    
    public static boolean DesconectarBanco()
    {
        try
        {
            if (!ConPostgreSQL.getConexao().isClosed())
            {
                ConPostgreSQL.Desconectar();
                
                if (!ConPostgreSQL.getConexao().isClosed())
                {
                    Alerta.MostrarAlertaErro("Não foi realizar a desconexão", "Erro Desconexão");
                }
            }
            
            return true;
        }
        catch(Exception e)
        {
            Alerta.MostrarAlertaErro("Não foi possivel realizar a desconexão com o servidor. \n\nErro: " + 
            e.toString(), "Erro Desconexão");
            
            return false;
        }
    }
    
    public static void InserirRegistro()
    {
        try
        {
            int vrIdPessoa= 0, vrIdEndereco = 0, vrIdPessoaFisica = 0;
            
            StringBuilder StrSQL = new StringBuilder();
            
            //Inserindo endereço da pessoa
            
            StrSQL.setLength(0);
            
            StrSQL.append("INSERT INTO endereco (complemento, logradouro, cep, cidade_id) VALUES "). 
            append("(").append(FuncoesDiversas.QuoteStringSimples("RUA A Nº 25")).append(",").
            append(FuncoesDiversas.QuoteStringSimples("BAIRRO CENTRO")).append(",").
            append(FuncoesDiversas.QuoteStringSimples("77023-078")).append(", 1) RETURNING endereco_id;");
            
            FuncoesDiversas.QuoteStringSimples("");
            
            ConPostgreSQL.getStmt().execute(StrSQL.toString());

            if (ConPostgreSQL.getStmt().getResultSet().next())
            {
                vrIdEndereco = ConPostgreSQL.getStmt().getResultSet().getInt(1);
            }
            
            //Inserindo pessoa fisica
            StrSQL.setLength(0);
            
            StrSQL.append("INSERT INTO pessoa_fisica (data_nascimento, cpf, rg) VALUES (").
            append(FuncoesDiversas.QuoteStringSimples("1991-07-16")).append(",").
            append(FuncoesDiversas.QuoteStringSimples("03719112570")).append(",").
            append(FuncoesDiversas.QuoteStringSimples("27928181")).append(") RETURNING pessoa_fisica_id;");

            ConPostgreSQL.getStmt().execute(StrSQL.toString());

            if (ConPostgreSQL.getStmt().getResultSet().next())
            {
                vrIdPessoaFisica = ConPostgreSQL.getStmt().getResultSet().getInt(1);
            }
            
            //Inserindo a pessoa 
            StrSQL.setLength(0);
            
            StrSQL.append("INSERT INTO pessoa (nome, tipo_pessoa_id, endereco_id, pessoa_fisica_juridica_id) VALUES (").
            append(FuncoesDiversas.QuoteStringSimples("ALINE SILVA")).append(",1,").
            append(vrIdEndereco).append(",").append(vrIdPessoaFisica).append(") RETURNING pessoa_id;");

            ConPostgreSQL.getStmt().execute(StrSQL.toString());

            if (ConPostgreSQL.getStmt().getResultSet().next())
            {
                vrIdPessoa = ConPostgreSQL.getStmt().getResultSet().getInt(1);
            }
            
            //Inserindo fornecedor
            StrSQL.setLength(0);
            
            StrSQL.append("INSERT INTO fornecedor (pessoa_id, status_id) VALUES (").append(vrIdPessoa).append(",1)");
            
            ConPostgreSQL.getStmt().execute(StrSQL.toString());
            
            ConPostgreSQL.Commit();
            
            Alerta.MostrarAlerta("Registro inserido com sucesso!!!!", "Inserção Registro");
        }
        catch(Exception e)
        {
            ConPostgreSQL.RollBack();
            Alerta.MostrarAlertaErro("Não foi possivel realizar a inserção do registro. \n\nRollBack Executado. \n\nMensagem Erro: " + e.toString(), "Inserção Registro");
        }
    }
    
    public static void RemoverRegistro()
    {
        try
        {
            Alerta.MostrarAlerta("Registro removido com sucesso!!!!", "Remoção Registro");
        }
        catch(Exception e)
        {
            Alerta.MostrarAlertaErro("Não foi possivel realizar a remoção do registro. \n\nErro: " + e.toString(), "Remoção Registro");
        }
    }
}