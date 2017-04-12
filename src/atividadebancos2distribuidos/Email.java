package atividadebancos2distribuidos;

/**
 *
 * @author denilson
 */
public class Email extends Contato {

    private String email;

    public Email(String nome, String sobrenome, String email) {
        super(nome, sobrenome);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
