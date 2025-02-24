
package com.github.developermobile.sistemadevendas.domain.entities;

import com.github.developermobile.sistemadevendas.domain.exceptions.DomainExceptions;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tiago
 */
@Entity
@Table(name = "VENDA")
@NamedQueries({ 
    @NamedQuery(name = "Venda.findByData", query = "SELECT v FROM Venda v WHERE v.dataVenda between :dataInicio AND :dataFim")  
})
public class Venda {
    public static String FIND_BY_DATA_VENDA = "Venda.findByData";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cliente cliente;
    
    @Column(name = "DATA_VENDA")
    private LocalDate dataVenda;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id.venda", fetch = FetchType.EAGER)
    private List<ItensVenda> itensVendas;

    public Venda() {this.itensVendas = new ArrayList<>();}

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public List<ItensVenda> getItensVendas() {
        return itensVendas;
    }

   public void addItensVenda(ItensVenda it) {
       itensVendas.add(it);
   }
   
   public void removeItensVenda(ItensVenda it) {
       itensVendas.remove(it);
   }
    
}
