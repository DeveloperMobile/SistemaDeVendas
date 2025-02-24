
package com.github.developermobile.sistemadevendas.repository.dao;

import com.github.developermobile.sistemadevendas.domain.entities.Venda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tiago
 */
public class VendaDAO extends BaseDAO<Venda> {
    private static VendaDAO instance;
    
    private VendaDAO() { }
    
    public static VendaDAO getInstance() {
        if (instance == null) {
            instance = new VendaDAO();
        }
        return instance;
    }
    
    public List<Venda> findByDate(LocalDate inicio, LocalDate fim, String query, Class<?> c) {
        EntityManager em = (EntityManager) repository.getEntityManager();
        List<Venda> list = new ArrayList<>();
        
        try {
            Query q = em.createNamedQuery(query, c);
            q.setParameter("dataInicio", inicio);
            q.setParameter("dataFim", fim);
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            exception(em, e);
            return new ArrayList<>();
        }
    }
}
