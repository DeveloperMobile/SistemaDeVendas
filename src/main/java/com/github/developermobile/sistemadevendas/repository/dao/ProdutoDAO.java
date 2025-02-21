
package com.github.developermobile.sistemadevendas.repository.dao;

import com.github.developermobile.sistemadevendas.domain.entities.Produto;

/**
 *
 * @author tiago
 */
public class ProdutoDAO extends BaseDAO<Produto> {
    private static ProdutoDAO instance;

    private ProdutoDAO() { }
    
    public static ProdutoDAO getInstance() {
        if (instance == null) {
            instance = new ProdutoDAO();
        } 
        return instance;
    }
}
