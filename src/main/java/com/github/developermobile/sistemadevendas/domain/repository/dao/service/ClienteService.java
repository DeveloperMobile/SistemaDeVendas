package com.github.developermobile.sistemadevendas.domain.repository.dao.service;

import com.github.developermobile.sistemadevendas.domain.entities.Cliente;
import com.github.developermobile.sistemadevendas.domain.repository.dao.ClienteDAO;
import com.github.developermobile.sistemadevendas.domain.exceptions.DAOExceptions;
import java.util.List;

/**
 *
 * @author tiago
 */
public class ClienteService {
    private static final ClienteDAO dao = ClienteDAO.getInstance();
    private static ClienteService instance;
    
    private ClienteService() { }
    
    public static ClienteService getInstance() {
        if (instance == null) {
            instance = new ClienteService();
        }
        return instance;
    }
    
    public void insert(Cliente cliente) {
        if (!dao.insert(cliente)) {
            throw new DAOExceptions("Erro ao cadastrar cliente!");
        }
        dao.insert(cliente);
    }
    
    public void update(Cliente cliente) {
        if (!dao.update(cliente)) {
            throw new DAOExceptions("Erro ao atualizar cliente!");
        }
        dao.update(cliente);
    }
    
    public void delete(Cliente cliente) {
        if (!dao.delete(cliente)) {
            throw new DAOExceptions("Erro ao deletar cliente!");
        }
    }
    
    public List<Cliente> findAll(String query, Class c) {
        if (dao.findAll(query, c).isEmpty()) {
            throw new DAOExceptions("Nenhum registro encontrado!");
        }
        return dao.findAll(query, c);
    }
    
    public List<Cliente> findByName(String name, String query, Class c) {
        if (dao.findByNme(name, query, c).isEmpty()) {
            throw new DAOExceptions("Cliente " + name + " não encontrado!");
        }
        return dao.findByNme(name, query, c);
    }
}
