package com.github.developermobile.sistemadevendas.view;

import com.github.developermobile.sistemadevendas.domain.entities.Cliente;
import com.github.developermobile.sistemadevendas.domain.entities.ItensVenda;
import com.github.developermobile.sistemadevendas.domain.entities.Produto;
import com.github.developermobile.sistemadevendas.domain.entities.Venda;
import com.github.developermobile.sistemadevendas.domain.exceptions.DAOExceptions;
import com.github.developermobile.sistemadevendas.domain.exceptions.DomainExceptions;
import com.github.developermobile.sistemadevendas.domain.service.VendaService;
import com.github.developermobile.sistemadevendas.utils.FormatterUtils;
import com.github.developermobile.sistemadevendas.utils.JOPUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tiago
 */
public class VendaFrame extends javax.swing.JInternalFrame {

    private DefaultTableModel dtm;
    private Venda venda;
    private Cliente cliente;
    private Produto produto;

    public VendaFrame() {
        initComponents();
        defineModelo();
        venda = new Venda();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new DomainExceptions("Informe o cliente!");
        }
        this.cliente = cliente;
        tfCliente.setText(cliente.getNome());
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        if (cliente == null) {
            throw new DomainExceptions("Informe o produto!");
        }
        this.produto = produto;
        tfProduto.setText(produto.getNome());
    }

    private void defineModelo() {
        dtm = (DefaultTableModel) tbItensVenda.getModel();

        FormatterUtils.maskFormatterNubmer("#,###.00", ftfTotal, Double.class);
        FormatterUtils.maskFormatterNubmer("#,###", ftfQtde, Integer.class);
        tbItensVenda.getColumnModel().getColumn(0).setPreferredWidth(400);
    }

    private void atualizaTabela() {
        int linha = tbItensVenda.getRowCount();

        for (int i = 0; i < linha; i++) {
            dtm.removeRow(0);
        }

        double valorTotal = 0.0;

        for (int i = 0; i < venda.getItensVendas().size(); i++) {
            dtm.insertRow(i, new Object[]{
                venda.getItensVendas().get(i).getId().getProduto().getNome(),
                String.format("%d", venda.getItensVendas().get(i).getQtde()),
                String.format("%.2f", venda.getItensVendas().get(i).getId().getProduto().getValor()),
                String.format("%.2f", venda.getItensVendas().get(i).total())
            });

            valorTotal += venda.getItensVendas().get(i).total();
        }
        ftfTotal.setValue( valorTotal);
    }

    private void selecionaCliente() {
        ClienteFrame clienteFrame = new ClienteFrame(this);
        clienteFrame.setVisible(true);
        this.getDesktopPane().add(clienteFrame);
        clienteFrame.toFront();
    }
    
    private void selecionaProduto() {
        ProdutoFrame produtoFrame = new ProdutoFrame(this);
        produtoFrame.setVisible(true);
        this.getDesktopPane().add(produtoFrame);
        produtoFrame.toFront();
    }
    
    private void limparCampos() {
        tfCliente.setText("");
        cliente = null;
        
        tfProduto.setText("");
        produto = null;
        
        ftfQtde.setText("");
        ftfTotal.setText("");
        
        venda.getItensVendas().clear();
    }
    
    private void incluir() {
        try {
            if (ftfQtde.getValue() == null) {
                JOPUtil.message("Informe a quantidade!", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                venda.addItensVenda(new ItensVenda(
                        produto, 
                        venda, 
                        (Integer) ftfQtde.getValue(), 
                        produto.getValor()));
                System.out.println("Ivs: " + venda.getItensVendas().size());
                atualizaTabela();
            }
        } catch (DomainExceptions e) {
            JOPUtil.message(e.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void excluir() {
        if (tbItensVenda.getSelectedRow() != -1) {
            venda.removeItensVenda(venda.getItensVendas().get(tbItensVenda.getSelectedRow()));
            atualizaTabela();
        } else {
            JOPUtil.message("Selecione um item na lista!", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void registrar() {
        try {
            if (venda.getItensVendas().isEmpty()) {
                JOPUtil.message("Insirea pelo menos um item na venda!", 
                        "Aviso", 
                        JOptionPane.WARNING_MESSAGE);
            } else {
                venda.setCliente(cliente);
                venda.setDataVenda(LocalDate.now());
                VendaService.getInstance().insert(venda);
                JOPUtil.message("Venda registrada com sucesso!", 
                        "Confirmação", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (DomainExceptions e) {
            JOPUtil.message(e.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        limparCampos();
        atualizaTabela();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lbCliente = new javax.swing.JLabel();
        tfCliente = new javax.swing.JTextField();
        btnSelecionaCliente = new javax.swing.JButton();
        lbProduto = new javax.swing.JLabel();
        tfProduto = new javax.swing.JTextField();
        btnSelecionaProduto = new javax.swing.JButton();
        lbQtde = new javax.swing.JLabel();
        ftfQtde = new javax.swing.JFormattedTextField();
        btnIncluir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbItensVenda = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lbTotal = new javax.swing.JLabel();
        ftfTotal = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        btnExcluirItem = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Registro de Venda");
        setMinimumSize(new java.awt.Dimension(600, 500));
        setPreferredSize(new java.awt.Dimension(600, 500));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(0, 119, 0));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lbTitle.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(255, 255, 255));
        lbTitle.setText("Registro de Vendas");
        jPanel1.add(lbTitle);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        lbCliente.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbCliente.setText("Cliente:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lbCliente, gridBagConstraints);

        tfCliente.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(tfCliente, gridBagConstraints);

        btnSelecionaCliente.setText("...");
        btnSelecionaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionaClienteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnSelecionaCliente, gridBagConstraints);

        lbProduto.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbProduto.setText("Produto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lbProduto, gridBagConstraints);

        tfProduto.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(tfProduto, gridBagConstraints);

        btnSelecionaProduto.setText("...");
        btnSelecionaProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionaProdutoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnSelecionaProduto, gridBagConstraints);

        lbQtde.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbQtde.setText("Qtde:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lbQtde, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(ftfQtde, gridBagConstraints);

        btnIncluir.setText("Incluir");
        btnIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnIncluir, gridBagConstraints);

        tbItensVenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produto", "Qtde", "Vlr. Unit.", "Vlr. Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane1.setViewportView(tbItensVenda);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        lbTotal.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbTotal.setText("Total:");
        jPanel3.add(lbTotal);

        ftfTotal.setEnabled(false);
        ftfTotal.setMinimumSize(new java.awt.Dimension(70, 27));
        ftfTotal.setPreferredSize(new java.awt.Dimension(70, 27));
        jPanel3.add(ftfTotal);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnExcluirItem.setText("Excluir Item");
        btnExcluirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirItemActionPerformed(evt);
            }
        });
        jPanel4.add(btnExcluirItem);

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(btnSalvar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelecionaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaClienteActionPerformed
       selecionaCliente();
    }//GEN-LAST:event_btnSelecionaClienteActionPerformed

    private void btnSelecionaProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaProdutoActionPerformed
        selecionaProduto();
    }//GEN-LAST:event_btnSelecionaProdutoActionPerformed

    private void btnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirActionPerformed
        incluir();
    }//GEN-LAST:event_btnIncluirActionPerformed

    private void btnExcluirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirItemActionPerformed
        excluir();
    }//GEN-LAST:event_btnExcluirItemActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        registrar();
    }//GEN-LAST:event_btnSalvarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcluirItem;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSelecionaCliente;
    private javax.swing.JButton btnSelecionaProduto;
    private javax.swing.JFormattedTextField ftfQtde;
    private javax.swing.JFormattedTextField ftfTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbCliente;
    private javax.swing.JLabel lbProduto;
    private javax.swing.JLabel lbQtde;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JTable tbItensVenda;
    private javax.swing.JTextField tfCliente;
    private javax.swing.JTextField tfProduto;
    // End of variables declaration//GEN-END:variables
}
