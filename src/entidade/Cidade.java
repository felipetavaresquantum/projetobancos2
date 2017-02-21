
package entidade;


import java.util.Collection;
/**
 *
 * @author denil
 */
public class Cidade {

    private Integer idCidade;
    private String cidade;
    private Estado idEstado;
    private Collection<Endereco> enderecoCollection;

    public Cidade() {
        this.idEstado = new Estado();
    }

    public Cidade(Integer idCidade) {
        this.idCidade = idCidade;
         this.idEstado = new Estado();
    }

    public Cidade(Integer idCidade, String descricao) {
        this.idCidade = idCidade;
        this.cidade = descricao;
         this.idEstado = new Estado();
    }

    public Integer getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public Collection<Endereco> getEnderecoCollection() {
        return enderecoCollection;
    }

    public void setEnderecoCollection(Collection<Endereco> enderecoCollection) {
        this.enderecoCollection = enderecoCollection;
    }

}
