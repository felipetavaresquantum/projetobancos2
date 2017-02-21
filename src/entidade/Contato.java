
package entidade;

/**
 *
 * @author denilson
 */
public class Contato {

    private Integer idContato;
    private String ddd;
    private String fone;
    private Pessoa idPessoa;

    public Contato() {
       
    }

    public Contato(Integer idContato) {
        this.idContato = idContato;
       
    }

    public Contato(Integer idContato, String ddd, String fone) {
        this.idContato = idContato;
        this.ddd = ddd;
        this.fone = fone;
       
    }

    public Integer getIdContato() {
        return idContato;
    }

    public void setIdContato(Integer idContato) {
        this.idContato = idContato;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public Pessoa getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Pessoa idPessoa) {
        this.idPessoa = idPessoa;
    }
    
    

}
