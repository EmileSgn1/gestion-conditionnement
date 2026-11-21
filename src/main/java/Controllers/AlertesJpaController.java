/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.RollbackFailureException;
import Entities.Alertes;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Entities.Articles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class AlertesJpaController implements Serializable {

    public AlertesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alertes alertes) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Articles articleId = alertes.getArticleId();
            if (articleId != null) {
                articleId = em.getReference(articleId.getClass(), articleId.getId());
                alertes.setArticleId(articleId);
            }
            em.persist(alertes);
            if (articleId != null) {
                articleId.getAlertesList().add(alertes);
                articleId = em.merge(articleId);
            }
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

    public void edit(Alertes alertes) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Alertes persistentAlertes = em.find(Alertes.class, alertes.getId());
            Articles articleIdOld = persistentAlertes.getArticleId();
            Articles articleIdNew = alertes.getArticleId();
            if (articleIdNew != null) {
                articleIdNew = em.getReference(articleIdNew.getClass(), articleIdNew.getId());
                alertes.setArticleId(articleIdNew);
            }
            alertes = em.merge(alertes);
            if (articleIdOld != null && !articleIdOld.equals(articleIdNew)) {
                articleIdOld.getAlertesList().remove(alertes);
                articleIdOld = em.merge(articleIdOld);
            }
            if (articleIdNew != null && !articleIdNew.equals(articleIdOld)) {
                articleIdNew.getAlertesList().add(alertes);
                articleIdNew = em.merge(articleIdNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = alertes.getId();
                if (findAlertes(id) == null) {
                    throw new NonexistentEntityException("The alertes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Alertes alertes;
            try {
                alertes = em.getReference(Alertes.class, id);
                alertes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alertes with id " + id + " no longer exists.", enfe);
            }
            Articles articleId = alertes.getArticleId();
            if (articleId != null) {
                articleId.getAlertesList().remove(alertes);
                articleId = em.merge(articleId);
            }
            em.remove(alertes);
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

    public List<Alertes> findAlertesEntities() {
        return findAlertesEntities(true, -1, -1);
    }

    public List<Alertes> findAlertesEntities(int maxResults, int firstResult) {
        return findAlertesEntities(false, maxResults, firstResult);
    }

    private List<Alertes> findAlertesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alertes.class));
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

    public Alertes findAlertes(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alertes.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlertesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alertes> rt = cq.from(Alertes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
