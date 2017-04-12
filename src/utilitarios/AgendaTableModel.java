package utilitarios;

import atividadebancos2distribuidos.Contato;
import atividadebancos2distribuidos.Email;
import atividadebancos2distribuidos.Telefone;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author denilson
 */
public class AgendaTableModel extends AbstractTableModel {

    private ArrayList<Contato> contatos;
    private ArrayList<Telefone> telefones;
    private ArrayList<Email> emails;
    private String[] colunas = {"Nome", "Sobrenome", "Fone", "Email"};

    public AgendaTableModel() {
        this.contatos = new ArrayList<>();
        this.telefones = new ArrayList<>();
        this.emails = new ArrayList<>();
    }

    public void addContato(Email e) {
        this.emails.add(e);
        this.fireTableDataChanged();
    }

    public void addTelefone(Telefone t) {
        this.telefones.add(t);
        this.fireTableDataChanged();
    }

    public void removeContato(int rowIndex) {
        this.contatos.remove(rowIndex);
        this.telefones.remove(rowIndex);
        this.emails.remove(rowIndex);
        this.fireTableDataChanged();
    }

    public Contato getContato(int rowIndex) {
        return this.contatos.get(rowIndex);
    }

    public String getColumnName(int num) {
        return this.colunas[num];
    }

    @Override
    public int getRowCount() {
        return contatos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return contatos.get(linha).getNome();
            case 1:
                return contatos.get(linha).getSobrenome();
            case 2:
                return telefones.get(linha).getTelefone();
            case 3:
                return emails.get(linha).getEmail();
        }
        return null;
    }

}
