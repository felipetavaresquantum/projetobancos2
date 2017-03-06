package dao;

public class DadosConexaoBanco
{
    private String Usuario = "postgres";
    private String Senha = "root";
    private String Protocolo = "jdbc:postgresql";
    private String Driver = "org.postgresql.Driver";
    private String Porta = "5432";
    private String Servidor = "localhost";
    private String Banco = "cadastro_fornecedor";
    private String Url = "";

    public String getUsuario() 
    {
        return Usuario;
    }

    public void setUsuario(String Usuario) 
    {
        this.Usuario = Usuario;
    }

    public String getSenha() 
    {
        return Senha;
    }

    public void setSenha(String Senha) 
    {
        this.Senha = Senha;
    }

    public String getProtocolo() 
    {
        return Protocolo;
    }

    public void setProtocolo(String Protocolo) 
    {
        this.Protocolo = Protocolo;
    }

    public String getDriver() 
    {
        return Driver;
    }

    public void setDriver(String Driver) 
    {
        this.Driver = Driver;
    }

    public String getPorta() 
    {
        return Porta;
    }

    public void setPorta(String Porta) 
    {
        this.Porta = Porta;
    }

    public String getServidor() 
    {
        return Servidor;
    }

    public void setServidor(String Servidor) 
    {
        this.Servidor = Servidor;
    }

    public String getBanco() 
    {
        return Banco;
    }

    public void setDataBase(String Banco) 
    {
        this.Banco = Banco;
    }

    public String getUrl() 
    {
        Url = "" + Protocolo + "://" + Servidor + ":" + Porta + "/" + Banco + "";
        
        return Url;
    }

    public void setUrl(String Url) 
    {
        this.Url = Url;
    }
}