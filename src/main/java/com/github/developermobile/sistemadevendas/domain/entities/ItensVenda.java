
package com.github.developermobile.sistemadevendas.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author tiago
 */
@Entity
@Table(name = "ITENS_VENDA")
@NamedQueries({
    @NamedQuery(name = "ItensVenda.findById", query = "SELECT iv FROM ItensVenda iv INNER JOIN Venda v ON iv.id.venda.id = v.id WHERE v.id = :idVenda")
})
public class ItensVenda {
    public static final String FIND_BY_ID = "ItensVenda.findById";
    
    @EmbeddedId
    private ItensVendaPK id = new ItensVendaPK();
    
    @Column(name = "QTDE")
    private Integer qtde;
    
    @Column(name = "VALOR")
    private Double valor;

    public ItensVenda() { }
    
    public ItensVenda(Produto produto, Venda venda, Integer qtde, Double valor) {
        id.setProduto(produto);
        id.setVenda(venda);
        this.qtde = qtde;
        this.valor = valor;
    }

    public ItensVendaPK getId() {
        return id;
    }

    public void setProduto(Produto produto) {
        id.setProduto(produto);
    }

    public void setVenda(Venda venda) {
        id.setVenda(venda);
    }
    
    public Integer getQtde() {
        return qtde;
    }

    public void setQtde(Integer qtde) {
        this.qtde = qtde;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    public Double total() {
        return valor * qtde;
    }
}
