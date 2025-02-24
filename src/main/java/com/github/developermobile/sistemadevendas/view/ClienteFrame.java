package com.github.developermobile.sistemadevendas.view;

import com.github.developermobile.sistemadevendas.domain.entities.Cliente;
import com.github.developermobile.sistemadevendas.domain.enums.Operations;
import com.github.developermobile.sistemadevendas.domain.exceptions.DAOExceptions;
import com.github.developermobile.sistemadevendas.domain.exceptions.DomainExceptions;
import com.github.developermobile.sistemadevendas.domain.service.ClienteService;
import com.github.developermobile.sistemadevendas.utils.FormatterUtils;
import com.github.developermobile.sistemadevendas.utils.JOPUtil;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tiago
 */
public class ClienteFrame extends javax.swing.JInternalFrame {

    private DefaultTableModel dtm;
    private ListSelectionModel listModel;
    private List<Cliente> clientes = new ArrayList<>();
    private Operations mode;
    private VendaFrame vendaFrame;

    public ClienteFrame() {
        initComponents();
        defineModelo();
        btnSelecionarCliente.setVisible(false);
    }

    public ClienteFrame(VendaFrame vendaFrame) {
        initComponents();
        defineModelo();
        btnSelecionarCliente.setVisible(true);
        this.vendaFrame = vendaFrame;
    }
    
    private void defineModelo() {
        dtm = (DefaultTableModel) tbCliente.getModel();
        listModel = tbCliente.getSelectionModel();
        listModel.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                mostraDetalhe();
            }
        });
        FormatterUtils.maskFormatter("##.###.###", ftfCep);
        FormatterUtils.maskFormatter("(##)####-####", ftfFone);
    }

    private void mostraDetalhe() {
        if (tbCliente.getSelectedRow() != -1) {
            int indice = tbCliente.getSelectedRow();
            tfNome.setText(clientes.get(indice).getNome());
            tfEndereco.setText(clientes.get(indice).getEndereco());
            tfBairro.setText(clientes.get(indice).getBairro());
            tfCidade.setText(clientes.get(indice).getCidade());
            cbUf.setSelectedItem(clientes.get(indice).getUf());
            ftfCep.setText(clientes.get(indice).getCep());
            ftfFone.setText(clientes.get(indice).getTelefone());
            tfEmail.setText(clientes.get(indice).getEmail());
        } else {
            limparCampos();
        }
    }

    private void atualizaTela() {
        if (tfFilter.getText().trim().equals("")) {
            clientes = ClienteService.getInstance().findAll();
        } else {
            clientes = ClienteService.getInstance().findByName(tfFilter.getText().trim());
        }

        int linha = dtm.getRowCount();

        for (int i = 0; i < linha; i++) {
            dtm.removeRow(0);
        }

        for (int i = 0; i < clientes.size(); i++) {
            dtm.insertRow(i, new Object[]{
                clientes.get(i).getId(),
                clientes.get(i).getNome(),
                clientes.get(i).getTelefone(),
                clientes.get(i).getEmail()
            });
        }
    }

    private void habilitaCampos() {
        tfNome.setEnabled(true);
        tfEndereco.setEnabled(true);
        tfBairro.setEnabled(true);
        tfCidade.setEnabled(true);
        cbUf.setEnabled(true);
        ftfCep.setEnabled(true);
        ftfFone.setEnabled(true);
        tfEmail.setEnabled(true);
    }

    private void desabilitaCampos() {
        tfNome.setEnabled(false);
        tfEndereco.setEnabled(false);
        tfBairro.setEnabled(false);
        tfCidade.setEnabled(false);
        cbUf.setEnabled(false);
        ftfCep.setEnabled(false);
        ftfFone.setEnabled(false);
        tfEmail.setEnabled(false);
    }

    private void limparCampos() {
        tfNome.setText("");
        tfEndereco.setText("");
        tfBairro.setText("");
        tfCidade.setText("");
        cbUf.setSelectedItem("");
        ftfCep.setText("");
        ftfFone.setText("");
        tfEmail.setText("");
    }

    private void habilitaBotoes() {
        btnNovo.setEnabled(false);
        btnSalvar.setEnabled(true);
        btnAlterar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    private void desabilitaBotoes() {
        btnNovo.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnAlterar.setEnabled(true);
        btnExcluir.setEnabled(true);
        btnCancelar.setEnabled(false);
    }

    private void insert() {
        try {
            Cliente cliente = new Cliente();
            cliente.setNome(tfNome.getText().trim());
            cliente.setEndereco(tfEndereco.getText().trim());
            cliente.setBairro(tfBairro.getText().trim());
            cliente.setCidade(tfCidade.getText().trim());
            cliente.setUf(cbUf.getSelectedItem().toString());
            cliente.setCep((String) ftfCep.getValue());
            cliente.setTelefone((String) ftfFone.getValue());
            cliente.setEmail(tfEmail.getText().trim());
            ClienteService.getInstance().insert(cliente);

            JOPUtil.message("Cliente cadastrado com sucesso!",
                    "Confirmação",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (DomainExceptions e) {
            JOPUtil.message(e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOPUtil.message("Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void update() {
        try {
            Cliente cliente = new Cliente();
            cliente.setId(clientes.get(tbCliente.getSelectedRow()).getId());
            cliente.setNome(tfNome.getText().trim());
            cliente.setEndereco(tfEndereco.getText().trim());
            cliente.setBairro(tfBairro.getText().trim());
            cliente.setCidade(tfCidade.getText().trim());
            cliente.setUf(cbUf.getSelectedItem().toString());
            cliente.setCep((String) ftfCep.getValue());
            cliente.setTelefone((String) ftfFone.getValue());
            cliente.setEmail(tfEmail.getText().trim());
            ClienteService.getInstance().update(cliente);

            JOPUtil.message("Cliente alterado com sucesso!",
                    "Confirmação",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (DomainExceptions e) {
            JOPUtil.message(e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOPUtil.message("Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     private void delete() {
        try {
            ClienteService.getInstance().delete(clientes.get(tbCliente.getSelectedRow()));
            JOPUtil.message("Cliente excluído com sucesso!", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
            atualizaTela();
            limparCampos();
        } catch (DAOExceptions e) {
            JOPUtil.message(e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvar() {
        if (mode == Operations.INSERT) {
            insert();
        } else {
            update();
        }
        
        atualizaTela();
        limparCampos();
    }
    
    private void alterar() {
        if (tbCliente.getSelectedRow() != -1) {
            habilitaCampos();
            habilitaBotoes();
            mode = Operations.EDIT;
        } else {
            JOPUtil.message("Selecione um cliente na lista!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void deletar() {
        if (tbCliente.getSelectedRow() != -1) {
           int resposta = JOPUtil.confirmMessage( "Confirmar a exclusão do cliente?", 
                   "Confirmação",
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.INFORMATION_MESSAGE);
           if (resposta == JOptionPane.YES_NO_OPTION) {
               delete();
           }
        } else {
            JOPUtil.message("Selecione um cliente na lista!", 
                    "Avusi", 
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void selecionaCliente() {
        if (tbCliente.getSelectedRow() != -1) {
            vendaFrame.setCliente(clientes.get(tbCliente.getSelectedRow()));
            this.dispose();
            vendaFrame.toFront();
        } else {
            JOPUtil.message("Selecione um cliente na lista", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lbFilter = new javax.swing.JLabel();
        tfFilter = new javax.swing.JTextField();
        btnFilter = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCliente = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lbNome = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        lbEndereco = new javax.swing.JLabel();
        tfEndereco = new javax.swing.JTextField();
        lbBairro = new javax.swing.JLabel();
        tfBairro = new javax.swing.JTextField();
        lbCidade = new javax.swing.JLabel();
        tfCidade = new javax.swing.JTextField();
        lbUf = new javax.swing.JLabel();
        cbUf = new javax.swing.JComboBox<>();
        lbCep = new javax.swing.JLabel();
        ftfCep = new javax.swing.JFormattedTextField();
        lbFone = new javax.swing.JLabel();
        ftfFone = new javax.swing.JFormattedTextField();
        lbEmail = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnSelecionarCliente = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastro de Clientes");
        setMinimumSize(new java.awt.Dimension(600, 500));
        setPreferredSize(new java.awt.Dimension(600, 500));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(0, 119, 0));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lbTitle.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(255, 255, 255));
        lbTitle.setText("Cadastro de Clientes");
        jPanel1.add(lbTitle);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        lbFilter.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbFilter.setText("Filtro por nome:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lbFilter, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(tfFilter, gridBagConstraints);

        btnFilter.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        btnFilter.setText("Pesquisar");
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnFilter, gridBagConstraints);

        tbCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOME", "TELEFONE", "EMAIL"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbCliente);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        lbNome.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbNome.setText("*Nome:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lbNome, gridBagConstraints);

        tfNome.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(tfNome, gridBagConstraints);

        lbEndereco.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbEndereco.setText("Endereço:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lbEndereco, gridBagConstraints);

        tfEndereco.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(tfEndereco, gridBagConstraints);

        lbBairro.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbBairro.setText("Bairro:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lbBairro, gridBagConstraints);

        tfBairro.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(tfBairro, gridBagConstraints);

        lbCidade.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbCidade.setText("Cidade:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lbCidade, gridBagConstraints);

        tfCidade.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(tfCidade, gridBagConstraints);

        lbUf.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbUf.setText("UF:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lbUf, gridBagConstraints);

        cbUf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
        cbUf.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbUf, gridBagConstraints);

        lbCep.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbCep.setText("Cep:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lbCep, gridBagConstraints);

        ftfCep.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(ftfCep, gridBagConstraints);

        lbFone.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbFone.setText("*Telefone:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lbFone, gridBagConstraints);

        ftfFone.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(ftfFone, gridBagConstraints);

        lbEmail.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbEmail.setText("Email:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lbEmail, gridBagConstraints);

        tfEmail.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(tfEmail, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        btnSelecionarCliente.setText("Seleciona Cliente");
        btnSelecionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarClienteActionPerformed(evt);
            }
        });
        jPanel4.add(btnSelecionarCliente);

        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        jPanel4.add(btnNovo);

        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        jPanel4.add(btnAlterar);

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        jPanel4.add(btnExcluir);

        btnSalvar.setText("Salvar");
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(btnSalvar);

        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCancelar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        atualizaTela();
    }//GEN-LAST:event_btnFilterActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        habilitaCampos();
        habilitaBotoes();
        mode = Operations.INSERT;
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        desabilitaCampos();
        desabilitaBotoes();
        limparCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        salvar();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        deletar();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSelecionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarClienteActionPerformed
        selecionaCliente();
    }//GEN-LAST:event_btnSelecionarClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSelecionarCliente;
    private javax.swing.JComboBox<String> cbUf;
    private javax.swing.JFormattedTextField ftfCep;
    private javax.swing.JFormattedTextField ftfFone;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbBairro;
    private javax.swing.JLabel lbCep;
    private javax.swing.JLabel lbCidade;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbEndereco;
    private javax.swing.JLabel lbFilter;
    private javax.swing.JLabel lbFone;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbUf;
    private javax.swing.JTable tbCliente;
    private javax.swing.JTextField tfBairro;
    private javax.swing.JTextField tfCidade;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfEndereco;
    private javax.swing.JTextField tfFilter;
    private javax.swing.JTextField tfNome;
    // End of variables declaration//GEN-END:variables
}
