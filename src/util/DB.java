
package util;

/**
 *
 * @author Denilson
 */
public final class DB {
    
    private String usuario;
    private String senha;
    private String protocolo;
    private String driver;
    private String porta;
    private String host;
    private String dataBase;
    private String url;

    public DB(String usuario, String senha, String protocolo, String driver, String porta, String host, String dataBase) {
        setUsuario(usuario);
        setSenha(senha);
        setProtocolo(protocolo);
        setDriver(driver);
        setPorta(porta);
        setHost(host);
        setDataBase(dataBase);
    }

    DB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getUrl() {
        url = ""+protocolo+"://"+host+":"+porta+"/"+dataBase+"";
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
}
