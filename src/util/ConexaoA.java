
package util;


import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Denilson
 */
public class ConexaoA {
    private static DB db = new DB("denilson", "postgres", "jdbc:postgresql", "org.postgresql.Driver", "5432", "localhost", "postgres");
    
    
    private Connection con;
    private ResultSet rs;
    private Statement stmt;
    private PreparedStatement pst;
    
    public DB getDB(){
        return ConexaoA.db;
    }
    public ConexaoA(){
        ConexaoA.db = new DB();
    }
    
    public ConexaoA(DB db){
        ConexaoA.db = db;
    }

    public static Connection Conexao() throws ClassNotFoundException {
        try{
            Class.forName(db.getDriver());
            Connection con;
            con = DriverManager.getConnection(db.getUrl(),db.getUsuario(),db.getSenha());
            //JOptionPane.showMessageDialog(null, "Conectado com sucesso!");
            return con;
        }
        
        catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
            return null;
        }
    }
    
    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        ConexaoA.db = db;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public PreparedStatement getPst() {
        return pst;
    }

    public void setPst(PreparedStatement pst) {
        this.pst = pst;
    }
    
}
