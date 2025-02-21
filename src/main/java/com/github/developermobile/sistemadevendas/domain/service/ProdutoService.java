
package com.github.developermobile.sistemadevendas.domain.service;

import com.github.developermobile.sistemadevendas.domain.entities.Produto;
import com.github.developermobile.sistemadevendas.domain.exceptions.DAOExceptions;
import com.github.developermobile.sistemadevendas.repository.dao.ProdutoDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tiago
 */
public class ProdutoService {
    private static final ProdutoDAO dao = ProdutoDAO.getInstance();
    private static ProdutoService instance;

    private ProdutoService() { }
    
    public static ProdutoService getInstance() {
        if (instance == null) {
            instance = new ProdutoService();
        }
        return instance;
    }
    
    public void insert(Produto produto) {
        if (!dao.insert(produto)) {
            throw new DAOExceptions("Erro ao cadastrar produto!");
        }
    }
    
    public void update(Produto produto) {
        if (!dao.update(produto)) {
            throw new DAOExceptions("Erro ao atualizar produto!");
        }
    }
    
    public void delete(Produto produto) {
        if (!dao.delete(produto)) {
            throw new DAOExceptions("Erro ao deletar produto!");
        }
    }
    
    public List<Produto> findAll() {
        List<Produto> produtos = dao.findAll(Produto.FIND_ALL, Produto.class);
        if (produtos.isEmpty()) {
            throw new DAOExceptions("Nenhum registro encontrado!");
        }
        return produtos;
    }
    
     public List<Produto> findByName(String name) {
        List<Produto> produtos = dao.findByName(name, Produto.FIND_BY_NAME, Produto.class);
        if (produtos.isEmpty()) {
            throw new DAOExceptions("Produto " + name + " não encontrado!");
        }
        return produtos;
    }
}
