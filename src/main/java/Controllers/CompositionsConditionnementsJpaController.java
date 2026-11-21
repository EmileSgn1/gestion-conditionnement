/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.RollbackFailureException;
import Entities.CompositionsConditionnements;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.UserTransaction;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class CompositionsConditionnementsJpaController implements Serializable {

    public CompositionsConditionnementsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CompositionsConditionnements compositionsConditionnements) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(compositionsConditionnements);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CompositionsConditionnements compositionsConditionnements) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            compositionsConditionnements = em.merge(compositionsConditionnements);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compositionsConditionnements.getId();
                if (findCompositionsConditionnements(id) == null) {
                    throw new NonexistentEntityException("The compositionsConditionnements with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CompositionsConditionnements compositionsConditionnements;
            try {
                compositionsConditionnements = em.getReference(CompositionsConditionnements.class, id);
                compositionsConditionnements.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compositionsConditionnements with id " + id + " no longer exists.", enfe);
            }
            em.remove(compositionsConditionnements);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CompositionsConditionnements> findCompositionsConditionnementsEntities() {
        return findCompositionsConditionnementsEntities(true, -1, -1);
    }

    public List<CompositionsConditionnements> findCompositionsConditionnementsEntities(int maxResults, int firstResult) {
        return findCompositionsConditionnementsEntities(false, maxResults, firstResult);
    }

    private List<CompositionsConditionnements> findCompositionsConditionnementsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CompositionsConditionnements.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CompositionsConditionnements findCompositionsConditionnements(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CompositionsConditionnements.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompositionsConditionnementsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CompositionsConditionnements> rt = cq.from(CompositionsConditionnements.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
