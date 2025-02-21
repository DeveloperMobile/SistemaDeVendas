package com.github.developermobile.sistemadevendas.domain.service;

import com.github.developermobile.sistemadevendas.domain.entities.Cliente;
import com.github.developermobile.sistemadevendas.repository.dao.ClienteDAO;
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
    }
    
    public void update(Cliente cliente) {
        if (!dao.update(cliente)) {
            throw new DAOExceptions("Erro ao atualizar cliente!");
        }
    }
    
    public void delete(Cliente cliente) {
        if (!dao.delete(cliente)) {
            throw new DAOExceptions("Erro ao deletar cliente!");
        }
    }
    
    public List<Cliente> findAll() {
        List<Cliente> clientes = dao.findAll(Cliente.FIND_ALL, Cliente.class);
        if (clientes.isEmpty()) {
            throw new DAOExceptions("Nenhum registro encontrado!");
        }
        return clientes;
    }
    
    public List<Cliente> findByName(String name) {
        List<Cliente> clientes = dao.findByName(name, Cliente.FIND_BY_NAME, Cliente.class);
        if (clientes.isEmpty()) {
            throw new DAOExceptions("Cliente " + name + " não encontrado!");
        }
        return clientes;
    }
}
