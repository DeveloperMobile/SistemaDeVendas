package com.github.developermobile.sistemadevendas.domain.service;

import com.github.developermobile.sistemadevendas.domain.entities.Venda;
import com.github.developermobile.sistemadevendas.domain.exceptions.DAOExceptions;
import com.github.developermobile.sistemadevendas.repository.dao.VendaDAO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tiago
 */
public class VendaService {

    private static final VendaDAO dao = VendaDAO.getInstance();
    private static VendaService instance;

    private VendaService() {
    }

    public static VendaService getInstance() {
        if (instance == null) {
            instance = new VendaService();
        }
        return instance;
    }

    public void insert(Venda venda) {
        if (!dao.insert(venda)) {
            throw new DAOExceptions("Erro ao cadastrar venda!");
        }
    }

    public List<Venda> findByData(LocalDate inicio, LocalDate fim) {
        List<Venda> vendas = new ArrayList<>();
        
        if (inicio == null) {
            throw new DAOExceptions("Informe a data inicial!");
        } else if (fim == null) {
            throw new DAOExceptions("Informe a data inicial!");
        } else {
            vendas = dao.findByDate(inicio, fim, Venda.FIND_BY_DATA_VENDA, Venda.class);
            if (vendas.isEmpty()) {
                throw new DAOExceptions("Nenhum registro encontrado!");
            }
        }

        return vendas;
    }
}
