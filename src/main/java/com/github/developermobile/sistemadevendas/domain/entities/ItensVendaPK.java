
package com.github.developermobile.sistemadevendas.domain.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author tiago
 */
@Embeddable
public class ItensVendaPK {
    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID")
    private Produto produto;
    
    @ManyToOne
    @JoinColumn(name = "ID_VENDA", referencedColumnName = "ID")
    private Venda venda;

    public ItensVendaPK() { }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
    
    
}
