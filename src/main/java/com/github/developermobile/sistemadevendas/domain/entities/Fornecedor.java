package com.github.developermobile.sistemadevendas.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;


/**
 *
 * @author tiago
 */
@Entity
@Table(name = "FORNECEDOR")
@NamedQueries({
    @NamedQuery(name = "Fornecedor.findAll", query = "SELECT f FROM Fornecedor f"),
    @NamedQuery(name = "Fornecedor.findByName", query = "SELECT f FROM Fornecedor f WHERE f.nome LIKE :nome")})
public class Fornecedor extends Pessoa implements Serializable {
    public static final String FIND_ALL = "Fornecedor.findAll";
    public static final String FIND_BY_NAME = "Fornecedor.findByName";
}
