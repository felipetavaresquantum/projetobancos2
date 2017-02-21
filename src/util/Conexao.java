package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao {

    private static Conexao instancia = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private static DB db = new DB("denilson", "postgres", "jdbc:postgresql", "org.postgresql.Driver", "5432", "localhost", "postgres");
   

    /*
    @return PreparedStatement
     */
    public PreparedStatement getPs() {
        return ps;
    }

    private Conexao() {

        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(),db.getUsuario(),db.getSenha());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //retorna um instancia de conexão
    public static Conexao getInstancia() {
        try {
            if (instancia == null || !instancia.con.isValid(0)) {
                instancia = new Conexao();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            instancia = new Conexao();
        } finally {
            return (instancia);
        }

    }

    //ob: sempre ser chamando apos criar a query, e passa-la como parametro
    public void preparar(String sql) {
        try {
            this.ps = this.con.prepareStatement(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void prepararAI(String sql) {
        try {
            this.ps = this.con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int getAutoIncrement() {
        try {
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                return (rs.getInt(1));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (0);
    }

    // fecha a conexão com o banco 
    public void fechar() {
        try {
            this.ps.close();
            this.con.close();
            Conexao.instancia = null;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //executa comando que nao alteram dados do banco
    //um objeto ResultSet deve receber esse metodo
    public ResultSet executeQuery() {
        try {
            return (this.ps.executeQuery());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //executa comando que altera dados do banco 
    //caso retorne false, significa que o comando 
    //não funcionou
    public boolean executeUpdate() {
        try {
            return (this.ps.executeUpdate() > 0);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (false);
    }

    public void iniciaTransacao() {
        try {
            this.con.setAutoCommit(false);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void fecharTransacao(boolean b) {
        try {
            if (b) {
                this.con.commit();
            } else {
                this.con.rollback();
            }
            this.con.setAutoCommit(true);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
