
package com.github.developermobile.sistemadevendas.domain.entities;

import com.github.developermobile.sistemadevendas.domain.exceptions.DomainExceptions;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author tiago
 */
@Entity
@Table(name = "PRODUTO")
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p"),
    @NamedQuery(name = "Produto.findByName", query = "SELECT p FROM Produto p WHERE p.nome LIKE :nome")
})
public class Produto implements Serializable {
   public static final String FIND_ALL = "Produto.findAll";
   public static final String FIND_BY_NAME = "Produto.findByName";
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Basic(optional = false)
   @Column(name = "ID")
   private Integer id;
   
   @Column(name = "NOME")
   private String nome;
   
   @Column(name = "QTDE_ESTOQUE")
   private Integer qtdeEstoque;
   
   @Column(name = "VALOR")
   private Double valor;

   @JoinColumn(name = "ID_FORNECEDOR", referencedColumnName = "ID")
   @ManyToOne(optional = false)
   private Fornecedor fornecedor;
   
   public Produto() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
         if ("".equals(nome)) {
            throw new DomainExceptions("Informe o nome!");
        }
        this.nome = nome;
    }

    public Integer getQtdeEstoque() {
        return qtdeEstoque;
    }

    public void setQtdeEstoque(Integer qtdeEstoque) {
         if (qtdeEstoque.equals(0)) {
            throw new DomainExceptions("Informe a quantidade no estoque!");
        }
        this.qtdeEstoque = qtdeEstoque;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
         if (valor.equals(0)) {
            throw new DomainExceptions("Informe o valor do produto!");
        }
        this.valor = valor;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
         if (fornecedor == null) {
            throw new DomainExceptions("Informe o fornecedor!");
        }
        this.fornecedor = fornecedor;
    }
   
   
}
