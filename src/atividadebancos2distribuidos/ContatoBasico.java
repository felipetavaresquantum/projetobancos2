package atividadebancos2distribuidos;

import java.util.ArrayList;

/**
 *
 * @author denilson
 */
abstract public class ContatoBasico {

    protected String nome;
    protected String sobrenome;

    protected ArrayList<Telefone> telefones;

    public ContatoBasico() {
        nome = "";
        sobrenome = "";
        telefones = new ArrayList();
    }

    public ContatoBasico(String nome, String sobrenome) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        telefones = new ArrayList();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setTelefone(Telefone tf) {
        telefones.add(tf);
    }

}
