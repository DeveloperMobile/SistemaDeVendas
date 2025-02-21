
package com.github.developermobile.sistemadevendas.domain.service;

import com.github.developermobile.sistemadevendas.domain.entities.Fornecedor;
import com.github.developermobile.sistemadevendas.domain.exceptions.DAOExceptions;
import com.github.developermobile.sistemadevendas.repository.dao.FornecedorDAO;
import java.util.List;

/**
 *
 * @author tiago
 */
public class FornecedorService {
    private static final FornecedorDAO dao = FornecedorDAO.getInstance();
    private static FornecedorService instance;
    
    private FornecedorService() { }
    
    public static FornecedorService getInstance() {
        if (instance == null) {
            instance = new FornecedorService();
        }
        return instance;
    }
    
    public void insert(Fornecedor fornecedor) {
        if (!dao.insert(fornecedor)) {
            throw new DAOExceptions("Erro ao cadastrar fornecedor!");
        }
    }
    
    public void update(Fornecedor fornecedor) {
        if (!dao.update(fornecedor)) {
            throw new DAOExceptions("Erro ao atualizar fornecedor!");
        }
    }
    
    public void delete(Fornecedor fornecedor) {
        if (!dao.delete(fornecedor)) {
            throw new DAOExceptions("Erro ao deletar fornecedor!");
        }
    }
    
    public List<Fornecedor> findAll() {
        List<Fornecedor> fornecedores = dao.findAll(Fornecedor.FIND_ALL, Fornecedor.class);
        if (fornecedores.isEmpty()) {
            throw new DAOExceptions("Nenhum registro encontrado!");
        }
        return fornecedores;
    }
    
    public List<Fornecedor> findByName(String name) {
        List<Fornecedor> fornecedores = dao.findByName(name, Fornecedor.FIND_BY_NAME, Fornecedor.class);
        if (fornecedores.isEmpty()) {
            throw new DAOExceptions("Fornecedor " + name + " não encontrado!");
        }
        return fornecedores;
    }
}
