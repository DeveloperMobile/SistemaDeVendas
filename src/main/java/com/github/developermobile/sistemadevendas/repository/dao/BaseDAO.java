package com.github.developermobile.sistemadevendas.repository.dao;

import com.github.developermobile.sistemadevendas.repository.JPARepository;
import com.github.developermobile.sistemadevendas.domain.exceptions.DAOExceptions;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tiago
 * @param <T>
 */
public class BaseDAO<T> {

    private final JPARepository repository = JPARepository.getInstance();

    public boolean insert(T t) {
        EntityManager em = repository.getEntityManager();
        try {
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.persist(t);
            et.commit();
            return true;
        } catch (DAOExceptions e) {
            exception(em, e);
            return false;
        }
    }
    
     public boolean update(T t) {
         EntityManager em = repository.getEntityManager();
        try {
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.merge(t);
            et.commit();
            return true;
        } catch (DAOExceptions e) {
            exception(em, e);
            return false;
        }
    }
     
     public boolean delete(T t) {
         EntityManager em = repository.getEntityManager();
        try {
            EntityTransaction et = em.getTransaction();
            et.begin();
            t = em.merge(t);
            em.remove(t);
            et.commit();
            return true;
        } catch (DAOExceptions e) {
            exception(em, e);
            return false;
        }
    }
     
    public List<T> findAll(String query, Class<T> t) {
        EntityManager em = repository.getEntityManager();
        List<T> list = new ArrayList<>();
        
        try {
            Query q = em.createNamedQuery(query, t);
            list = q.getResultList();
            return list;
        } catch (DAOExceptions e) {
            exception(em, e);
            return new ArrayList<>();
        }
    } 
    
    public List<T> findByNme(String name, String query, Class<T> t) {
        EntityManager em = repository.getEntityManager();
        List<T> list = new ArrayList<>();
        
        try {
            Query q = em.createNamedQuery(query, t);
            q.setParameter("nome", "%" + name + "%");
            list = q.getResultList();
            return list;
        } catch (DAOExceptions e) {
            exception(em, e);
            return new ArrayList<>();
        }
    } 
    
    private void exception(EntityManager em, Exception e) {
        System.out.println("Error: " + e.getMessage());
            if (em.isOpen()) {
                em.getTransaction().rollback();
            }
    }
}
