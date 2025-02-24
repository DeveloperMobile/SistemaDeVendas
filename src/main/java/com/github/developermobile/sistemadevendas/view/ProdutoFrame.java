
package com.github.developermobile.sistemadevendas.view;

import com.github.developermobile.sistemadevendas.domain.entities.Fornecedor;
import com.github.developermobile.sistemadevendas.domain.entities.Produto;
import com.github.developermobile.sistemadevendas.domain.enums.Operations;
import com.github.developermobile.sistemadevendas.domain.exceptions.DAOExceptions;
import com.github.developermobile.sistemadevendas.domain.exceptions.DomainExceptions;
import com.github.developermobile.sistemadevendas.domain.service.ProdutoService;
import com.github.developermobile.sistemadevendas.utils.FormatterUtils;
import com.github.developermobile.sistemadevendas.utils.JOPUtil;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tiago
 */
public class ProdutoFrame extends javax.swing.JInternalFrame {
    private DefaultTableModel dtm;
    private ListSelectionModel listModel;
    private List<Produto> produtos;
    private Operations mode;
    private Fornecedor fornecedor;
    private VendaFrame vendaFrame;
    
    public ProdutoFrame() {
        initComponents();
        defineModelo();
        btnSelecionaProduto.setVisible(false);
    }
    
    public ProdutoFrame(VendaFrame vendaFrame) {
        initComponents();
        defineModelo();
        btnSelecionaProduto.setVisible(true);
        this.vendaFrame = vendaFrame;
    }
    
    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        tfFornecedor.setText(fornecedor.getNome()); 
    }
    
    private void defineModelo() {
        dtm = (DefaultTableModel) tbProduto.getModel();
        listModel = tbProduto.getSelectionModel();
        listModel.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                mostraDetalhe();
            }
        });
        
        FormatterUtils.maskFormatterNubmer("#,###.00", ftfValor, Double.class);
        FormatterUtils.maskFormatterNubmer("#,###", ftfEstoque, Integer.class);
        tbProduto.getColumnModel().getColumn(1).setPreferredWidth(300);
    }
    
    private void mostraDetalhe() {
        if (tbProduto.getSelectedRow() != -1) {
            int indice = tbProduto.getSelectedRow();
            tfNome.setText(produtos.get(indice).getNome());
            tfFornecedor.setText(produtos.get(indice).getFornecedor().getNome());
            fornecedor = produtos.get(indice).getFornecedor();
            ftfEstoque.setValue(produtos.get(indice).getQtdeEstoque());
            ftfValor.setValue(produtos.get(indice).getValor());
        } else {
            limpaCampos();
        }
    }
    
    private void atualizaTela() {
        if (tfFilter.getText().trim().equals("")) {
            produtos = ProdutoService.getInstance().findAll();
        } else {
            produtos = ProdutoService.getInstance().findByName(tfFilter.getText().trim());
        }
        
        int linha  = dtm.getRowCount();
        
        for (int i = 0; i < linha; i++) {
            dtm.removeRow(0);
        } 
        
        for (int i = 0; i < produtos.size(); i++) {
           dtm.insertRow(i, new Object[]{
               produtos.get(i).getId(),
               produtos.get(i).getNome(),
               String.format("%d", produtos.get(i).getQtdeEstoque()),
               String.format("%.2f", produtos.get(i).getValor())
           });
        }
    }
    
    private void habilitaCampos() {
        tfNome.setEnabled(true);
        ftfEstoque.setEnabled(true);
        ftfValor.setEnabled(true);
    }
    
    private void desabilitaCampos() {
        tfNome.setEnabled(false);
        ftfEstoque.setEnabled(false);
        ftfValor.setEnabled(false);
    }
    
    private void habilitaBotoes() {
        btnFornecedor.setEnabled(true);
        btnNovo.setEnabled(false);
        btnSalvar.setEnabled(true);
        btnAlterar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnCancelar.setEnabled(true); 
    }
    
    private void desabilitaBotoes() {
        btnFornecedor.setEnabled(false);
        btnNovo.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnAlterar.setEnabled(true);
        btnExcluir.setEnabled(true);
        btnCancelar.setEnabled(false); 
    }
    
    private void limpaCampos() {
        tfNome.setText("");
        tfFornecedor.setText("");
        fornecedor = null;
        ftfEstoque.setText("");
        ftfValor.setText("");
    }
    
    private void insert() {
        try {
            Produto produto = new Produto();
            produto.setNome(tfNome.getText().trim());
            produto.setFornecedor(fornecedor);
            produto.setQtdeEstoque((Integer) ftfEstoque.getValue());
            produto.setValor((Double) ftfValor.getValue());
            ProdutoService.getInstance().insert(produto);
            
            JOPUtil.message("Produto cadastrado com sucesso!",
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
            Produto produto = new Produto();
            produto.setId(produtos.get(tbProduto.getSelectedRow()).getId());
            produto.setNome(tfNome.getText().trim());
            produto.setFornecedor(fornecedor);
            produto.setQtdeEstoque((Integer) ftfEstoque.getValue());
            produto.setValor((Double) ftfValor.getValue());
            
            ProdutoService.getInstance().update(produto);
            
            JOPUtil.message("Produto cadastrado com sucesso!",
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
            ProdutoService.getInstance().delete(produtos.get(tbProduto.getSelectedRow()));
            JOPUtil.message("Produto excluído com sucesso!", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
            atualizaTela();
            limpaCampos();
        } catch (DAOExceptions e) {
            JOPUtil.message(e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void salvar() {
        if (mode == Operations.INSERT) {
            insert();
        } else {
            update();
        }
        
        atualizaTela();
        limpaCampos();
    }
    
    private void alterar() {
        if (tbProduto.getSelectedRow() != -1) {
            habilitaCampos();
            habilitaBotoes();
            mode = Operations.EDIT;
        } else {
            JOPUtil.message("Selecione um produto na lista", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void deletar() {
        if (tbProduto.getSelectedRow() != -1) {
            int resposta = JOPUtil.confirmMessage(
                    "Confirmar a esclusão do produto?", 
                    "Confirmação", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.INFORMATION_MESSAGE);
            
            if (resposta == JOptionPane.YES_NO_OPTION) {
                delete();
            }
            
        } else {
            JOPUtil.message("Selecione um produto na lista!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void selecionaFornecedor() {
        FornecedorFrame frame = new FornecedorFrame(this);
        frame.setVisible(true);
        this.getDesktopPane().add(frame);
        frame.toFront();
    }
    
    private void selecionaProduto() {
        if (tbProduto.getSelectedRow() != -1) {
            vendaFrame.setProduto(produtos.get(tbProduto.getSelectedRow()));
            this.dispose();
            vendaFrame.toFront();
        } else {
            JOPUtil.message("Selecione um produto na lista!", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
        }
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
        lbFilter = new javax.swing.JLabel();
        tfFilter = new javax.swing.JTextField();
        btnFilter = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProduto = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lbNome = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        lbFornecedor = new javax.swing.JLabel();
        tfFornecedor = new javax.swing.JTextField();
        btnFornecedor = new javax.swing.JButton();
        lbEstoque = new javax.swing.JLabel();
        ftfEstoque = new javax.swing.JFormattedTextField();
        lbValor = new javax.swing.JLabel();
        ftfValor = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        btnSelecionaProduto = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setMinimumSize(new java.awt.Dimension(600, 500));
        setPreferredSize(new java.awt.Dimension(600, 500));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(0, 119, 0));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lbTitle.setFont(new java.awt.Font("Serif", 0, 36)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(255, 255, 255));
        lbTitle.setText("Cadastro de Produtos");
        jPanel1.add(lbTitle);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
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

        tbProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOME", "ESTOQUE", "VALOR"
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
        jScrollPane1.setViewportView(tbProduto);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        lbNome.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbNome.setText("Nome:");
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

        lbFornecedor.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbFornecedor.setText("Fornecedor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lbFornecedor, gridBagConstraints);

        tfFornecedor.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(tfFornecedor, gridBagConstraints);

        btnFornecedor.setText("...");
        btnFornecedor.setEnabled(false);
        btnFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFornecedorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(btnFornecedor, gridBagConstraints);

        lbEstoque.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbEstoque.setText("Estoque:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lbEstoque, gridBagConstraints);

        ftfEstoque.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(ftfEstoque, gridBagConstraints);

        lbValor.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbValor.setText("Valor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lbValor, gridBagConstraints);

        ftfValor.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(ftfValor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.TRAILING));

        btnSelecionaProduto.setText("Seleciona Produto");
        btnSelecionaProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionaProdutoActionPerformed(evt);
            }
        });
        jPanel4.add(btnSelecionaProduto);

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
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
       atualizaTela();
    }//GEN-LAST:event_btnFilterActionPerformed

    private void btnFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFornecedorActionPerformed
        selecionaFornecedor();
    }//GEN-LAST:event_btnFornecedorActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        habilitaCampos();
        habilitaBotoes();
        mode = Operations.INSERT;
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
       alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        deletar();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        salvar();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        desabilitaCampos();
        desabilitaBotoes();
        limpaCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSelecionaProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionaProdutoActionPerformed
       selecionaProduto();
    }//GEN-LAST:event_btnSelecionaProdutoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnFornecedor;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSelecionaProduto;
    private javax.swing.JFormattedTextField ftfEstoque;
    private javax.swing.JFormattedTextField ftfValor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbEstoque;
    private javax.swing.JLabel lbFilter;
    private javax.swing.JLabel lbFornecedor;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbValor;
    private javax.swing.JTable tbProduto;
    private javax.swing.JTextField tfFilter;
    private javax.swing.JTextField tfFornecedor;
    private javax.swing.JTextField tfNome;
    // End of variables declaration//GEN-END:variables
}
