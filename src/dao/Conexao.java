package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utilitarios.Alerta;

public class Conexao 
{
    private DadosConexaoBanco DadosConexao;
    private Connection ConPostgreSQL;
    private ResultSet Query;
    private Statement Stm;
    private PreparedStatement PStm;
    
    public Conexao() 
    {
        this.DadosConexao = new DadosConexaoBanco();
    }

    public void Conectar(boolean AutoCommitAtivo)
    {
        try
        {
            Class.forName(DadosConexao.getDriver());
            this.ConPostgreSQL=DriverManager.getConnection(getDadosBanco().getUrl(), getDadosBanco().getUsuario(), getDadosBanco().getSenha());
            this.ConPostgreSQL.setAutoCommit(AutoCommitAtivo);
            this.Stm = (Statement) this.ConPostgreSQL.createStatement();
        }
        catch(ClassNotFoundException | SQLException e)
        {
            Alerta.MostrarAlertaErro("Ocorreu um erro durante o processo de conexão.  \n\nErro: " + e.toString(), "Erro Conexão");
        }
    }
  
    public DadosConexaoBanco getDadosBanco() 
    {
        return this.DadosConexao;
    }
    
    public void setDadosConexao(DadosConexaoBanco DadosConexao) 
    {
        this.DadosConexao = DadosConexao;
    }
    
    public void Desconectar()
    {
        try 
        {
            getConexao().close();
        } 
        catch (SQLException e) 
        {
            Alerta.MostrarAlertaErro("Ocorreu um erro durante o processo de desconexão.  \n\nErro: " + e.toString(), "Erro Desconexão");
        }
    }

    public Statement getStatement()
    {
        try 
        {
            return getConexao().createStatement();
        } 
        catch (SQLException e) 
        {
            Alerta.MostrarAlertaErro("Ocorreu um erro durante o retorno da statement.  \n\nErro: " + e.toString(), "Erro Statement");
        }
        
        return null;
    }

    public PreparedStatement getPreparedStatement(String ComandoSQL)
    {
        try 
        {
            return this.ConPostgreSQL.prepareStatement(ComandoSQL);
        } 
        catch (SQLException e) 
        {
            Alerta.MostrarAlertaErro("Ocorreu um erro durante o retorno do prepared statement.  \n\nErro: " + e.toString(), "Erro Prepared Statement");
        }
        
        return null;
    }

    public Statement getStatement(StringBuffer ComandoSQL)
    {
        try 
        {
            return this.ConPostgreSQL.prepareStatement(ComandoSQL.toString());
        } 
        catch (SQLException e) 
        {
            Alerta.MostrarAlertaErro("Ocorreu um erro durante o retorno da statement.  \n\nErro: " + e.toString(), "Erro Statement");
        }
        
        return null;
    }

    public void execUpdate(PreparedStatement Ps)
    {
        try 
        {
            Ps.executeUpdate();
        } 
        catch (SQLException e) 
        {
            Alerta.MostrarAlertaErro("Ocorreu um erro durante o update.  \n\nErro: " + e.toString(), "Erro Update");
        }
    }

    public ResultSet execQuery(PreparedStatement Ps)
    {
        try 
        {
            return Ps.executeQuery();
        } 
        catch (SQLException e) 
        {
            Alerta.MostrarAlertaErro("Ocorreu um erro durante a consulta.  \n\nErro: " + e.toString(), "Erro Consulta");
        }
        
        return null;
    }

    public ResultSet execQuery(String sql)
    {
        try 
        {
            Statement stm = getStatement();
            return stm.executeQuery(sql);
        } 
        catch (SQLException e) 
        {
            Alerta.MostrarAlertaErro("Ocorreu um erro durante a consulta.  \n\nErro: " + e.toString(), "Erro Consulta");
        }
        
        return null;
    }

    public void RollBack()
    {
        try 
        {
            this.ConPostgreSQL.rollback();
        } 
        catch (SQLException e) 
        {
            Alerta.MostrarAlertaErro("Ocorreu um erro durante o rollback da operação.  \n\nErro: " + e.toString(), "Erro RollBack");
        }
    }

    public void Commit()
    {
        try 
        {
            this.ConPostgreSQL.commit();
        } 
        catch (SQLException e) 
        {
            Alerta.MostrarAlertaErro("Ocorreu um erro durante o commit da operação.  \n\nErro: " + e.toString(), "Erro Commit");
        }
    }

    public Connection getConexao() 
    {
        return ConPostgreSQL;
    }

    public void setConexao(Connection ConPostgreSQL) 
    {
        this.ConPostgreSQL = ConPostgreSQL;
    }

    public ResultSet getConsulta() 
    {
        return Query;
    }

    public void setResults(ResultSet Query) 
    {
        this.Query = Query;
    }

    public Statement getStmt() 
    {
        return Stm;
    }

    public void setStmt(Statement Stm) 
    {
        this.Stm = Stm;
    }
    
    public PreparedStatement getPstmt() 
    {
        return PStm;
    }

    public void setPstmt(PreparedStatement PStm) 
    {
        this.PStm = PStm;
    }
}