
package entidade;

/**
 *
 * @author denilson
 */
public class Endereco {

    private int idEndereco;
    private String logradouro;
    private int cep;
    private Cidade idCidade;

    public Endereco() {
        this.idCidade = new Cidade();
    }

    public Endereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
         this.idCidade = new Cidade();
    }

    public Endereco(String logradouro, int cep) {
        this.logradouro = logradouro;
        this.cep = cep;
         this.idCidade = new Cidade();
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

   
    public Cidade getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Cidade idCidade) {
        this.idCidade = idCidade;
    }

}
