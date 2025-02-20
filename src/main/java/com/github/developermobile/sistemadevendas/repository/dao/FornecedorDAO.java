
package com.github.developermobile.sistemadevendas.repository.dao;

import com.github.developermobile.sistemadevendas.domain.entities.Fornecedor;

/**
 *
 * @author tiago
 */
public class FornecedorDAO extends BaseDAO<Fornecedor> {
    private static FornecedorDAO instance;

    private FornecedorDAO() { }
    
    public static FornecedorDAO getInstance() {
        if (instance == null) {
            instance = new FornecedorDAO();
        }
        return instance;
    }
}
