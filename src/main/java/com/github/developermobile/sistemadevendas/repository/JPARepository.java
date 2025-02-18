
package com.github.developermobile.sistemadevendas.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;





/**
 *
 * @author tiago
 */
public class JPARepository {
    private static final String PERSISTENCE_UNIT = "SisVendaHB";
    
    private static final ThreadLocal<EntityManager> them = new ThreadLocal<>();
    
    private static EntityManagerFactory emf;
    
    private static JPARepository instance;

    private JPARepository() { }
    
    public static JPARepository getInstance() {
        if (instance == null) {
            instance = new JPARepository();
        }
        return instance;
    }
    
    public EntityManager getEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            System.out.println("Teste");
        }
        
        EntityManager em = them.get();
        
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
            them.set(em);
        }
        
        return em;
    }
    
    private void closeEntityManager() {
        EntityManager em = them.get();
        
        if (em != null) {
            EntityTransaction transaction = em.getTransaction();
            if (transaction.isActive()) {
                transaction.commit();;
            }
            em.close();
            them.set(null);
        }
    }
    
    public void closeEntityManagerFactory() {
        closeEntityManager();
        emf.close();
    }
}
