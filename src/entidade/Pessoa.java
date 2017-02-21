package entidade;

import java.util.Collection;

/**
 *
 * @author denilson
 */
public class Pessoa {

    private Integer idPessoa;
    private String nome;
    private String email;
    private Endereco Endereco;
    private Contato contato;

    public Pessoa() {
        this.Endereco = new Endereco();
        this.contato = new Contato();
    }

    public Pessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
        this.Endereco = new Endereco();
        this.Endereco = new Endereco();
         this.contato = new Contato();
    }

    public Pessoa(Integer idPessoa, String nome) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.Endereco = new Endereco();
        this.Endereco = new Endereco();
         this.contato = new Contato();
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

 
    public Endereco getEndereco() {
        return Endereco;
    }

    public void setIdEndereco(Endereco Endereco) {
        this.Endereco = Endereco;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }


}
