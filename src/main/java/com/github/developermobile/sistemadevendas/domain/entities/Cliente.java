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
@Table(name = "CLIENTE")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByName", query = "SELECT c FROM Cliente c WHERE c.nome LIKE :nome")})
public class Cliente extends Pessoa implements Serializable {
    public static final String FIND_ALL = "Cliente.findAll";
    public static final String FIND_BY_NAME = "Cliente.findByName";
}
