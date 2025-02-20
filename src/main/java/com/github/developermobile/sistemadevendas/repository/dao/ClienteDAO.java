
package com.github.developermobile.sistemadevendas.repository.dao;

import com.github.developermobile.sistemadevendas.domain.entities.Cliente;

/**
 *
 * @author tiago
 */
public class ClienteDAO extends BaseDAO<Cliente> {
    private static ClienteDAO instance;

    private ClienteDAO() { }
    
    public static ClienteDAO getInstance(){
        if (instance == null) {
            instance = new ClienteDAO();
        }
        return instance;
    }
}
