
package entidade;


import java.util.Date;
/**
 *
 * @author denilson
 */
public class PessoaFisica extends Pessoa{

    private int idPessoaFisica;
    private String rg;
    private String cpf;
    private String dataNascimento;
   

    public PessoaFisica() {
       super();
    }

    public PessoaFisica(String rg, String cpf, String dataNascimento) {
        super();
        this.rg = rg;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public int getIdPessoaFisica() {
        return idPessoaFisica;
    }

    public void setIdPessoaFisica(int idPessoaFisica) {
        this.idPessoaFisica = idPessoaFisica;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

}
