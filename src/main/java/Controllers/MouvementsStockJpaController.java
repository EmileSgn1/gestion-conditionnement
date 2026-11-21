/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Entities.Articles;
import Entities.MouvementsStock;
import Entities.OperationsConditionnement;
import Entities.Utilisateurs;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class MouvementsStockJpaController implements Serializable {

    public MouvementsStockJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MouvementsStock mouvementsStock) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Articles articleId = mouvementsStock.getArticleId();
            if (articleId != null) {
                articleId = em.getReference(articleId.getClass(), articleId.getId());
                mouvementsStock.setArticleId(articleId);
            }
            OperationsConditionnement operationId = mouvementsStock.getOperationId();
            if (operationId != null) {
                operationId = em.getReference(operationId.getClass(), operationId.getId());
                mouvementsStock.setOperationId(operationId);
            }
            Utilisateurs utilisateurId = mouvementsStock.getUtilisateurId();
            if (utilisateurId != null) {
                utilisateurId = em.getReference(utilisateurId.getClass(), utilisateurId.getId());
                mouvementsStock.setUtilisateurId(utilisateurId);
            }
            em.persist(mouvementsStock);
            if (articleId != null) {
                articleId.getMouvementsStockList().add(mouvementsStock);
                articleId = em.merge(articleId);
            }
            if (operationId != null) {
                operationId.getMouvementsStockList().add(mouvementsStock);
                operationId = em.merge(operationId);
            }
            if (utilisateurId != null) {
                utilisateurId.getMouvementsStockList().add(mouvementsStock);
                utilisateurId = em.merge(utilisateurId);
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

    public void edit(MouvementsStock mouvementsStock) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            MouvementsStock persistentMouvementsStock = em.find(MouvementsStock.class, mouvementsStock.getId());
            Articles articleIdOld = persistentMouvementsStock.getArticleId();
            Articles articleIdNew = mouvementsStock.getArticleId();
            OperationsConditionnement operationIdOld = persistentMouvementsStock.getOperationId();
            OperationsConditionnement operationIdNew = mouvementsStock.getOperationId();
            Utilisateurs utilisateurIdOld = persistentMouvementsStock.getUtilisateurId();
            Utilisateurs utilisateurIdNew = mouvementsStock.getUtilisateurId();
            if (articleIdNew != null) {
                articleIdNew = em.getReference(articleIdNew.getClass(), articleIdNew.getId());
                mouvementsStock.setArticleId(articleIdNew);
            }
            if (operationIdNew != null) {
                operationIdNew = em.getReference(operationIdNew.getClass(), operationIdNew.getId());
                mouvementsStock.setOperationId(operationIdNew);
            }
            if (utilisateurIdNew != null) {
                utilisateurIdNew = em.getReference(utilisateurIdNew.getClass(), utilisateurIdNew.getId());
                mouvementsStock.setUtilisateurId(utilisateurIdNew);
            }
            mouvementsStock = em.merge(mouvementsStock);
            if (articleIdOld != null && !articleIdOld.equals(articleIdNew)) {
                articleIdOld.getMouvementsStockList().remove(mouvementsStock);
                articleIdOld = em.merge(articleIdOld);
            }
            if (articleIdNew != null && !articleIdNew.equals(articleIdOld)) {
                articleIdNew.getMouvementsStockList().add(mouvementsStock);
                articleIdNew = em.merge(articleIdNew);
            }
            if (operationIdOld != null && !operationIdOld.equals(operationIdNew)) {
                operationIdOld.getMouvementsStockList().remove(mouvementsStock);
                operationIdOld = em.merge(operationIdOld);
            }
            if (operationIdNew != null && !operationIdNew.equals(operationIdOld)) {
                operationIdNew.getMouvementsStockList().add(mouvementsStock);
                operationIdNew = em.merge(operationIdNew);
            }
            if (utilisateurIdOld != null && !utilisateurIdOld.equals(utilisateurIdNew)) {
                utilisateurIdOld.getMouvementsStockList().remove(mouvementsStock);
                utilisateurIdOld = em.merge(utilisateurIdOld);
            }
            if (utilisateurIdNew != null && !utilisateurIdNew.equals(utilisateurIdOld)) {
                utilisateurIdNew.getMouvementsStockList().add(mouvementsStock);
                utilisateurIdNew = em.merge(utilisateurIdNew);
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
                Integer id = mouvementsStock.getId();
                if (findMouvementsStock(id) == null) {
                    throw new NonexistentEntityException("The mouvementsStock with id " + id + " no longer exists.");
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
            MouvementsStock mouvementsStock;
            try {
                mouvementsStock = em.getReference(MouvementsStock.class, id);
                mouvementsStock.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mouvementsStock with id " + id + " no longer exists.", enfe);
            }
            Articles articleId = mouvementsStock.getArticleId();
            if (articleId != null) {
                articleId.getMouvementsStockList().remove(mouvementsStock);
                articleId = em.merge(articleId);
            }
            OperationsConditionnement operationId = mouvementsStock.getOperationId();
            if (operationId != null) {
                operationId.getMouvementsStockList().remove(mouvementsStock);
                operationId = em.merge(operationId);
            }
            Utilisateurs utilisateurId = mouvementsStock.getUtilisateurId();
            if (utilisateurId != null) {
                utilisateurId.getMouvementsStockList().remove(mouvementsStock);
                utilisateurId = em.merge(utilisateurId);
            }
            em.remove(mouvementsStock);
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

    public List<MouvementsStock> findMouvementsStockEntities() {
        return findMouvementsStockEntities(true, -1, -1);
    }

    public List<MouvementsStock> findMouvementsStockEntities(int maxResults, int firstResult) {
        return findMouvementsStockEntities(false, maxResults, firstResult);
    }

    private List<MouvementsStock> findMouvementsStockEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MouvementsStock.class));
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

    public MouvementsStock findMouvementsStock(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MouvementsStock.class, id);
        } finally {
            em.close();
        }
    }

    public int getMouvementsStockCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MouvementsStock> rt = cq.from(MouvementsStock.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
