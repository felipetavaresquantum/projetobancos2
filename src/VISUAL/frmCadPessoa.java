package VISUAL;

import DAO.CidadeDAO;
import DAO.EstadoDAO;
import DAO.PessoaFisicaDAO;
import DAO.PessoaJuridicaDAO;
import entidade.Cidade;
import entidade.Estado;
import entidade.Pessoa;
import entidade.PessoaFisica;
import entidade.PessoaJuridica;
import util.ConexaoA;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author denilson
 */
public final class frmCadPessoa extends javax.swing.JInternalFrame {

    int f = 0;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    int idcidade;

    public frmCadPessoa() throws ClassNotFoundException {
        initComponents();
        this.setLocation(310, 100);
        con = ConexaoA.Conexao();
        listarPessoaJ();
        listarPessoaF();
        listarEstados();
        this.idcidade = cmbEstado.getSelectedIndex();

    }

    public void listarPessoaF() {
        String sqlPessoaF = "SELECT * from vw_pf";

        try {
            pst = con.prepareStatement(sqlPessoaF);
            rs = pst.executeQuery();
            tblUsuarios.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }

    }

    public void listarPessoaJ() {
        String sqlPessoaJ = "SELECT * from vw_pj";

        try {
            pst = con.prepareStatement(sqlPessoaJ);
            rs = pst.executeQuery();
            tblUsuarios.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }

    }

    public void cadastrarClientes() {
        Pessoa p = new Pessoa();

        p.setNome(txtNome.getText());
        p.setEmail(txtEmail.getText());
        p.getEndereco().setLogradouro(txtEndereco.getText());
        p.getEndereco().setCep(Integer.parseInt(txtCEP.getText()));
        p.getEndereco().setIdCidade(new Cidade(Integer.parseInt(cmbCidade.getSelectedItem().toString().replaceAll("[^0-9]", ""))));
        p.getContato().setDdd(this.txtDDD.getText());
        p.getContato().setFone(this.txtFone.getText());

        if (cmbTipo.getSelectedItem() == "PJ") {

            PessoaJuridica pj = new PessoaJuridica();
            pj.setInscricaoEstadual(txtRG.getText());
            pj.setCnpj(txtCPF.getText());
            pj.setDataAbertura(txtData.getText());
            pj.setNome(p.getNome());
            pj.setEmail(p.getEmail());
            pj.setIdEndereco(p.getEndereco());
            pj.setContato(p.getContato());

            boolean retorno = PessoaJuridicaDAO.cadastrarPesoaJuridica(pj);

            if (retorno) {
                JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!", "Cadastrar", JOptionPane.INFORMATION_MESSAGE);
                listarPessoaJ();
            } else {
                JOptionPane.showMessageDialog(null, "erro", "Cadastrar", JOptionPane.ERROR_MESSAGE);
                listarPessoaJ();
            }
        } else {

            PessoaFisica pf = new PessoaFisica();

            pf.setRg(txtRG.getText());
            pf.setCpf(txtCPF.getText());
            pf.setDataNascimento(txtData.getText());

            pf.setNome(p.getNome());
            pf.setEmail(p.getEmail());
            pf.setIdEndereco(p.getEndereco());
            pf.setContato(p.getContato());

            boolean retorno = PessoaFisicaDAO.cadastrarPesoaFisica(pf);

            if (retorno) {
                JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!", "Cadastrar", JOptionPane.INFORMATION_MESSAGE);
                listarPessoaF();
            } else {
                JOptionPane.showMessageDialog(null, "Erro", "Cadastrar", JOptionPane.ERROR_MESSAGE);
                listarPessoaF();
            }
        }
    }

    public void pesquisarUsuarios() {

        if (cmbTipo.getSelectedItem() == "PJ") {

            String sql = "Select * from vw_pj where nome like ?";

            try {
                pst = con.prepareStatement(sql);
                pst.setString(1, txtPesquisar.getText() + "%");
                rs = pst.executeQuery();
                tblUsuarios.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (SQLException error) {
                JOptionPane.showMessageDialog(null, error);
            }

        } else {

            String sql = "Select * from vw_pf where nome like ?";

            try {
                pst = con.prepareStatement(sql);
                pst.setString(1, txtPesquisar.getText() + "%");
                rs = pst.executeQuery();
                tblUsuarios.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (SQLException error) {
                JOptionPane.showMessageDialog(null, error);
            }
        }
    }

    public void mostrarItens() {
        int seleciona = tblUsuarios.getSelectedRow();
        txtCodigo.setText(tblUsuarios.getModel().getValueAt(seleciona, 0).toString());
        txtNome.setText(tblUsuarios.getModel().getValueAt(seleciona, 1).toString());
        txtEmail.setText(tblUsuarios.getModel().getValueAt(seleciona, 2).toString());
        txtRG.setText(tblUsuarios.getModel().getValueAt(seleciona, 3).toString());
        txtCPF.setText(tblUsuarios.getModel().getValueAt(seleciona, 4).toString());
        txtData.setText(tblUsuarios.getModel().getValueAt(seleciona, 5).toString());
        txtDDD.setText(tblUsuarios.getModel().getValueAt(seleciona, 6).toString());
        txtFone.setText(tblUsuarios.getModel().getValueAt(seleciona, 7).toString());
        txtEndereco.setText(tblUsuarios.getModel().getValueAt(seleciona, 8).toString());
        txtCEP.setText(tblUsuarios.getModel().getValueAt(seleciona, 9).toString());
        txtEstado.setText(tblUsuarios.getModel().getValueAt(seleciona, 11).toString());
        txtCidade.setText(tblUsuarios.getModel().getValueAt(seleciona, 10).toString());
    }

    public void editarUsuarios() {

        Pessoa p = new Pessoa();

        p.setNome(txtNome.getText());
        p.setEmail(txtEmail.getText());

        if (cmbTipo.getSelectedItem() == "PJ") {

            PessoaJuridica pj = PessoaJuridicaDAO.buscarPessoaJurudica(Integer.parseInt(txtCodigo.getText()));

            pj.setInscricaoEstadual(txtRG.getText());
            pj.setCnpj(txtCPF.getText());
            pj.setDataAbertura(txtData.getText());
            pj.setIdPessoaJuridica(Integer.parseInt(txtCodigo.getText()));

            pj.setNome(p.getNome());
            pj.setEmail(p.getEmail());
            pj.getEndereco().setLogradouro(txtEndereco.getText());
            pj.getEndereco().setCep(Integer.parseInt(txtCEP.getText()));
            pj.getEndereco().setIdCidade(new Cidade(Integer.parseInt(cmbCidade.getSelectedItem().toString().replaceAll("[^0-9]", ""))));
            pj.getContato().setDdd(this.txtDDD.getText());
            pj.getContato().setFone(this.txtFone.getText());

            boolean retorno = PessoaJuridicaDAO.atualizarPessoa(pj);

            if (retorno) {
                JOptionPane.showMessageDialog(null, "Atualizado com sucesso!", "Atualizar", JOptionPane.INFORMATION_MESSAGE);
                listarPessoaJ();
            } else {
                JOptionPane.showMessageDialog(null, "erro", "Atualizar", JOptionPane.ERROR_MESSAGE);
                listarPessoaJ();
            }
        } else {

            PessoaFisica pf = PessoaFisicaDAO.buscarPessoaFisica(Integer.parseInt(txtCodigo.getText()));

            pf.setRg(txtRG.getText());
            pf.setCpf(txtCPF.getText());
            pf.setDataNascimento(txtData.getText());

            pf.setNome(p.getNome());
            pf.setEmail(p.getEmail());

            pf.getEndereco().setLogradouro(txtEndereco.getText());
            pf.getEndereco().setCep(Integer.parseInt(txtCEP.getText()));
            pf.getEndereco().setIdCidade(new Cidade(Integer.parseInt(cmbCidade.getSelectedItem().toString().replaceAll("[^0-9]", ""))));
            pf.getContato().setDdd(this.txtDDD.getText());
            pf.getContato().setFone(this.txtFone.getText());

            boolean retorno = PessoaFisicaDAO.atualizarPessoa(pf);

            if (retorno) {
                JOptionPane.showMessageDialog(null, "Atualizado com sucesso!", "Atualizar", JOptionPane.INFORMATION_MESSAGE);
                listarPessoaF();
            } else {
                JOptionPane.showMessageDialog(null, "erro", "Atualizar", JOptionPane.ERROR_MESSAGE);
                listarPessoaF();
            }
        }
    }

    public void deletarUsuarios() {
       

        if (cmbTipo.getSelectedItem() == "PJ") {

          PessoaJuridica pj = PessoaJuridicaDAO.buscarPessoaJurudica(Integer.parseInt(txtCodigo.getText()));


            boolean retorno = PessoaJuridicaDAO.apagarPessoa(pj);

            if (retorno) {
                JOptionPane.showMessageDialog(null, "Excluido com sucesso!", "Excluir", JOptionPane.INFORMATION_MESSAGE);
                listarPessoaJ();
            } else {
                JOptionPane.showMessageDialog(null, "erro", "Excluir", JOptionPane.ERROR_MESSAGE);
                listarPessoaJ();
            }
        } else {

           PessoaFisica pf = PessoaFisicaDAO.buscarPessoaFisica(Integer.parseInt(txtCodigo.getText()));

            boolean retorno = PessoaFisicaDAO.apagarPessoa(pf);

            if (retorno) {
                JOptionPane.showMessageDialog(null, "Excluido com sucesso!", "Excluir", JOptionPane.INFORMATION_MESSAGE);
                listarPessoaF();
            } else {
                JOptionPane.showMessageDialog(null, "erro", "Excluir", JOptionPane.ERROR_MESSAGE);
                listarPessoaF();
            }
        }
    }

    public void limparCampos() {
        txtCodigo.setText("");
        txtNome.setText("");
        txtEndereco.setText("");
        txtCEP.setText("");
        txtFone.setText("");
        txtDDD.setText("");
        txtFone.setText("");
        txtEmail.setText("");
        txtRG.setText("");
        txtCPF.setText("");
        txtData.setText("");
        txtCidade.setText("");
        txtEstado.setText("");
        cmbCidade.removeAllItems();
        cmbCidade.addItem("Escolha");
        cmbEstado.removeAllItems();
        cmbEstado.addItem("Escolha");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtEndereco = new javax.swing.JTextField();
        btnCadastrar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        txtPesquisar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtRG = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCPF = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtData = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtDDD = new javax.swing.JTextField();
        cmbTipo = new javax.swing.JComboBox<>();
        txtFone = new javax.swing.JTextField();
        cmbEstado = new javax.swing.JComboBox<>();
        cmbCidade = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtCEP = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtEstado = new javax.swing.JTextField();
        txtCidade = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastro de Clientes");

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Título 5", "Título 6", "Título 7", "Título 8", "Título 9", "Título 10", "Título 11"
            }
        ));
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

        jLabel1.setText("Código:");

        txtCodigo.setEnabled(false);

        jLabel2.setText("Nome:");

        jLabel3.setText("DDD:");

        jLabel4.setText("Endereço:");

        btnCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/user.png"))); // NOI18N
        btnCadastrar.setText("Cadastrar");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/edit-1.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/garbage-1.png"))); // NOI18N
        btnDeletar.setText("Deletar");
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/rotate.png"))); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/search-1.png"))); // NOI18N
        jLabel5.setText("Buscar");

        jLabel6.setText("Cidade:");

        jLabel7.setText("Estado:");

        jLabel8.setText("Email:");

        jLabel9.setText("RG/IE:");

        jLabel10.setText("CPF/CNPJ:");

        jLabel11.setText("Data*:");

        jLabel12.setText("Fone:");

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PF", "PJ" }));
        cmbTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoActionPerformed(evt);
            }
        });

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Escolha" }));
        cmbEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEstadoActionPerformed(evt);
            }
        });

        cmbCidade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Escolha" }));

        jLabel13.setText("CEP:");

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel14.setText("(* Abertura ou Nascimento)");

        txtEstado.setEditable(false);

        txtCidade.setEditable(false);

        jLabel15.setText("**");

        jLabel16.setText("**");

        jLabel17.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel17.setText("(** UF e Cidade Atual)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel3)
                                                .addGap(6, 6, 6)
                                                .addComponent(txtDDD, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtEndereco))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel12))
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtCEP)
                                            .addComponent(txtFone, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel16)
                                        .addGap(6, 6, 6)
                                        .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbCidade, 0, 182, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel11))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(22, 22, 22)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtRG, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCPF))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCadastrar, btnDeletar, btnEditar, btnLimpar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtDDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtCEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(txtRG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel17))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCadastrar, btnDeletar, btnEditar, btnLimpar});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        cadastrarClientes();
        limparCampos();
        listarEstados();
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        pesquisarUsuarios();
        listarEstados();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        mostrarItens();
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limparCampos();
        listarEstados();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        editarUsuarios();
        limparCampos();
        listarEstados();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        deletarUsuarios();
        listarEstados();
    }//GEN-LAST:event_btnDeletarActionPerformed

    private void cmbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoActionPerformed
        if (cmbTipo.getSelectedItem() == "PF") {
            listarPessoaF();
        } else {
            listarPessoaJ();
        }
    }//GEN-LAST:event_cmbTipoActionPerformed

    private void cmbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEstadoActionPerformed
        String dados[] = String.valueOf(cmbEstado.getSelectedItem()).split("-");
        if (!dados[0].equalsIgnoreCase("Escolha") && !dados[0].equalsIgnoreCase("null")) {
            cmbCidade.removeAllItems();
            cmbCidade.addItem("Escolha");
            listarCidade(dados[0]);
        } else {
            cmbCidade.removeAllItems();
            cmbCidade.addItem("Escolha");
        }
    }//GEN-LAST:event_cmbEstadoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JComboBox<String> cmbCidade;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtCEP;
    private javax.swing.JTextField txtCPF;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDDD;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtFone;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtRG;
    // End of variables declaration//GEN-END:variables

    public void listarEstados() {

        ArrayList<Estado> estados = EstadoDAO.getListaEstados();

        estados.stream().forEach((e) -> {
            //Adiciona os itens na ComboBox
            cmbEstado.addItem(String.valueOf(e.getIdEstado() + "-" + e.getUf()));

        });

    }

    public void listarCidade(String codEstado) {

        ArrayList<Cidade> cidades = CidadeDAO.ListaCidades(Integer.parseInt(cmbEstado.getSelectedItem().toString().split("-")[0]));

        cidades.stream().forEach((c) -> {
            //Adiciona os itens na ComboBox
            cmbCidade.addItem(String.valueOf(c.getIdCidade() + "-" + c.getCidade()));

        });

    }
}
